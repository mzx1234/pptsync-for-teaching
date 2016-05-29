package com.mzx.pptparseserver.service;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by zison on 2016/4/19.
 */
public interface ParseToImgService {

    Long parseToImg(String fileName, InputStream inputStream);

}
