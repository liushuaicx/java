package com.kaishengit.crm.files;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件储存
 * @author 刘帅
 */
public interface FileStore {

    /**
     *保存文件
     * @param inputStream 文件输入流
     * @param fileName 文件名
     * @return 文件存放路径
     * @throws IOException
     */
    String saveFile(InputStream inputStream, String fileName) throws IOException;

    /**下载文件
     * @param fileName 文件名或者文件路径
     * @return 文件的字节数组
     * @throws IOException
     */
    byte[] getFile(String fileName) throws IOException;

}
