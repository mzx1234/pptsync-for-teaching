package com.mzx.pptparseserver.service.impl;

import com.mzx.pptcommon.entity.PPTInfo;
import com.mzx.pptparseserver.annotation.Cacheable;
import com.mzx.pptparseserver.application.GlobalApplication;
import com.mzx.pptparseserver.service.ParseToImgService;
import com.mzx.pptparseserver.service.SavePPTImgService;
import com.mzx.pptparseserver.utility.POIParse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by zison on 2016/4/19.
 */
@Service
public class ParseToImgServiceImpl implements ParseToImgService{

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SavePPTImgService savePPTImgService;

    /**
     * 将文件解析成图片
     * @param fileName
     * @param inputStream
     */
    @Cacheable(key = "")
    public Long parseToImg(String fileName, InputStream inputStream) {
        logger.info("parseToImgService is running");
        POIParse poiParse = new POIParse();
        Map<byte[],byte[]> imgMap = poiParse.parsePPTToImg(fileName, inputStream);

//        将pptImg存储到Redis缓存
        savePPTImgService.savePPTImg(fileName,imgMap);

        return new Long(imgMap.size());

    }
}
