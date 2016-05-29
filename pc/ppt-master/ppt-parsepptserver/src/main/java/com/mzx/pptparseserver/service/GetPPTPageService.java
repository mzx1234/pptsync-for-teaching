package com.mzx.pptparseserver.service;


/**
 * Created by zison on 2016/4/21.
 */
public interface GetPPTPageService {

    byte[] getPPTPage(String fileName, int cur);
}
