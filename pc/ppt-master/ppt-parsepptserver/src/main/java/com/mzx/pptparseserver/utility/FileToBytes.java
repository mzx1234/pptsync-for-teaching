package com.mzx.pptparseserver.utility;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

/**
 * Created by zison on 2016/4/30.
 */
public class FileToBytes {

    public static byte[] fileToBytes(String path) throws Exception{
        FileInputStream inputStream = null;
        inputStream = new FileInputStream(path);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] byffer = new byte[1024];
        int length = -1;
        while ((length = inputStream.read(byffer)) != -1) {
            byteArrayOutputStream.write(byffer, 0, length);
        }
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }
}
