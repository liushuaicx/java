package com.kaishengit.crm.files;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**七牛云存储
 * @author 刘帅
 */
@Component
public class QiNiuFileStore implements FileStore {

    @Value("${qiniu.ak}")
    private String accessKey;
    @Value("${qiniu.sk}")
    private String secretKey;
    @Value("${qiniu.bucketName}")
    private String bucketName;
    @Value("${qiniu.domain}")
    private String domain;
    /**
     * 保存文件
     * @param inputStream 文件输入流
     * @param fileName    文件名
     * @return 文件存放路径
     * @throws IOException
     */
    @Override
    public String saveFile(InputStream inputStream, String fileName) throws IOException {

        Configuration configuration = new Configuration(Zone.zone1());
        UploadManager uploadManager = new UploadManager(configuration);

        Auth auth = Auth.create(accessKey,secretKey);
        String upToken = auth.uploadToken(bucketName);

        byte[] bytes = IOUtils.toByteArray(inputStream);
        Response response = uploadManager.put(bytes,null,upToken);

        DefaultPutRet defaultPutRet = new Gson().fromJson(response.bodyString(),DefaultPutRet.class);

        return defaultPutRet.key;
    }

    /**
     * 下载文件
     * @param fileName 文件名或者文件路径
     * @return 文件的字节数组
     * @throws IOException
     */
    @Override
    public byte[] getFile(String fileName) throws IOException {

        String finalName = String.format("%s/%s",domain,fileName);
        //url转byte
        System.out.println(fileName);
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(finalName).openConnection();
        InputStream inputStream = httpURLConnection.getInputStream();
        return IOUtils.toByteArray(inputStream);
    }
}
