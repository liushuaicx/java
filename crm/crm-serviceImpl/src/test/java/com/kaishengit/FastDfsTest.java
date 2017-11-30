package com.kaishengit;

import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FastDfsTest {

    @Test
    public void uploadFile() throws IOException, MyException {

        Properties properties = new Properties();
        properties.setProperty(ClientGlobal.PROP_KEY_TRACKER_SERVERS,"192.168.208.30:22122");
        //初始化配置
        ClientGlobal.initByProperties(properties);

        //跟踪客户端
        TrackerClient client = new TrackerClient();
        TrackerServer trackerServer = client.getConnection();

        //储存客户端
        StorageClient storageClient = new StorageClient(trackerServer,null);
        InputStream inputStream = new FileInputStream("e:/1.jpg");

        byte[] bytes = IOUtils.toByteArray(inputStream);
        String[] str = storageClient.upload_file(bytes,"jpg",null);

        for (String s : str) {
            System.out.println(s);
        }
        inputStream.close();


    }

//    @Test
//    public void upload() throws IOException, MyException {
//
//        Properties properties = new Properties();
//        properties.setProperty(ClientGlobal.PROP_KEY_TRACKER_SERVERS,"192.168.208.88:22122");
//
//        //初始配置
//        ClientGlobal.initByProperties(properties);
//
//        TrackerClient client = new TrackerClient();
//        TrackerServer trackerServer = client.getConnection();
//
//        //储存客户端
//        StorageClient storageClient = new StorageClient(trackerServer,null);
//        InputStream inputStream = new FileInputStream("e:/1.jpg");
//        byte[] bytes = IOUtils.toByteArray(inputStream);
//        String[] strings = storageClient.upload_file(bytes,"jpg",null);
//
//        for (String s: strings) {
//            System.out.println(s);
//        }
//
//        inputStream.close();




    //}



    @Test
    public void downloadFile() throws IOException, MyException {

        Properties properties = new Properties();
        properties.setProperty(ClientGlobal.PROP_KEY_TRACKER_SERVERS,"192.168.208.30:22122");
        //初始化配置
        ClientGlobal.initByProperties(properties);

        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();

        //存储服务器的客户端
        StorageClient storageClient = new StorageClient(trackerServer,null);

        byte[] bytes = storageClient.download_file("group1","M00/00/00/wKjQHloPqbSASiNzAALI525-HEU397.jpg");

        FileOutputStream outputStream = new FileOutputStream("e:/new11.jpg");
        outputStream.write(bytes,0,bytes.length);

        outputStream.flush();
        outputStream.close();
    }
}
