package com.mzx.pptcommon.entity;

import java.io.Serializable;

/**
 * Created by zison on 2016/4/30.
 */
public class PPTFileInfo implements Serializable {

    private String fileName;

    private byte[] fileBytes;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }
}
