package com.mzx.pptparseserver.service.impl;

import com.mzx.pptparseserver.service.SavePPTImgService;
import com.mzx.pptparseserver.utility.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Created by zison on 2016/4/21.
 */
@Service
public class SavePPTImgServiceImpl implements SavePPTImgService {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 保存解析之后的ppt图片
     * @param fileName
     * @param imgMap
     */
    public void savePPTImg(String fileName, Map<byte[],byte[]> imgMap) {
        redisUtil.hMsetObjects(fileName, imgMap);
    }
}
