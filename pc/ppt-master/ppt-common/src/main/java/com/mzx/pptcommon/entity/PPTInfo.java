package com.mzx.pptcommon.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by zison on 2016/4/30.
 */
public class PPTInfo implements Serializable {

    public static final byte SEPARATOR_1 = (byte) ':';
    public static final byte SEPARATOR_2 = (byte) '&';
    public static final byte SEPARATOR_3 = (byte) '|';

    private String fileName;

    private int curPage;

    private int pageNum;

    private byte[] imgBytes;

    public PPTInfo(PPTInfo info) {
        this.fileName = info.fileName;
        this.curPage = info.curPage;
        this.pageNum = info.pageNum;
        this.imgBytes = info.imgBytes;
    }

    public PPTInfo(){}


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public byte[] getImgBytes() {
        return imgBytes;
    }

    public void setImgBytes(byte[] imgBytes) {
        this.imgBytes = imgBytes;
    }


    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageNum() {
        return this.pageNum;
    }


    @Override
    public String toString() {
        return "PPTInfo{" +
                "fileName='" + fileName + '\'' +
                ", curPage=" + curPage +
                ", pageNum=" + pageNum +
                ", imgBytes=" + Arrays.toString(imgBytes) +
                '}';
    }
}
