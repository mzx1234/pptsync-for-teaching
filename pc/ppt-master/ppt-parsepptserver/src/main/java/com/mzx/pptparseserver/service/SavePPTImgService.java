package com.mzx.pptparseserver.service;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Created by zison on 2016/4/21.
 */
public interface SavePPTImgService {

    void savePPTImg(String fileName, Map<byte[],byte[]> ImgMap);
}
