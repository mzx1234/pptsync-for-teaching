package com.mzx.pptparseserver.service.impl;

import com.mzx.pptparseserver.service.GetPPTPageService;
import com.mzx.pptparseserver.utility.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by zison on 2016/4/21.
 */
@Service
public class GetPPTPageServiceImpl implements GetPPTPageService {


    @Autowired
    private RedisUtil redisUtil;

    /**
     * 通过文件名，页码，获取ppt页面
     * @param fileName
     * @param cur
     * @return
     */
    public byte[] getPPTPage(String fileName, int cur) {

        return redisUtil.hGetBytes(fileName, ""+(cur+1));
    }
}
