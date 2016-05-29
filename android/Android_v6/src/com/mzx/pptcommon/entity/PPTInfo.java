package com.mzx.pptcommon.entity;

import java.io.Serializable;

/**
 * Created by zison on 2016/4/30.
 */
public class PPTInfo implements Serializable{

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




}
