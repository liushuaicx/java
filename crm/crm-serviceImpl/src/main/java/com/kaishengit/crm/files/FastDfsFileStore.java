package com.kaishengit.crm.files;

import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**FastDfs 上传下载
 * @author 刘帅
 */
@Component
public class FastDfsFileStore implements FileStore {

    /**
     * 保存文件
     * @param inputStream 文件输入流
     * @param fileName    文件名
     * @return 文件存放路径
     * @throws IOException
     */
    @Override
    public String saveFile(InputStream inputStream, String fileName) throws IOException{

        String extName = fileName.substring(fileName.lastIndexOf(".")+1);

        String[] result = null;
        try {

            StorageClient storageClient = getStorageClient();
            result = storageClient.upload_file(IOUtils.toByteArray(inputStream),extName,null);

        } catch (MyException e) {
            throw new RuntimeException("上传到fastDFS失败",e);
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(result[0]).append("#").append(result[1]);

        return stringBuilder.toString();
    }

    /**
     * 下载文件
     * @param fileName 文件名或者文件路径
     * @return 文件的字节数组
     * @throws IOException
     */
    @Override
    public byte[] getFile(String fileName) throws IOException {

        String[] result = fileName.split("#");
        String groupName = result[0];
        String filepath = result[1];

        byte[] bytes = new byte[0];
        try {
            StorageClient storageClient = getStorageClient();
            return storageClient.download_file(groupName,filepath);
        } catch (MyException e) {
            throw new RuntimeException("从fastDFS下载失败",e);
        }

    }


    /**
     * @return 获得storageClient
     * @throws IOException
     * @throws MyException
     */
    private StorageClient getStorageClient() throws IOException, MyException {
        Properties properties = new Properties();
        properties.setProperty(ClientGlobal.PROP_KEY_TRACKER_SERVERS,"192.168.208.30:22122");
        //初始化配置
        ClientGlobal.initByProperties(properties);
        //创建tracker
        TrackerClient client = new TrackerClient();
        TrackerServer trackerServer = client.getConnection();
        //创建StorageServer
        StorageClient storageClient = new StorageClient(trackerServer,null);
        return storageClient;
    }
}
