package com.mzx.pptparseserver.controller;

import com.mzx.pptparseserver.service.SyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zison on 2016/5/3.
 */
@Controller
public class PPTPageSwichController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SyncService pptSyncService;

    @RequestMapping(value = "/switch")
    public void swichPPTPage(String fileName, int index) {
        logger.info("swichPPTPage method run");
        pptSyncService.pptSync(fileName,index);
    }
}
