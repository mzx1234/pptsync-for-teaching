package com.mzx.pptparseserver.controller;

import com.mzx.pptcommon.entity.PPTInfo;
import com.mzx.pptparseserver.application.GlobalApplication;
import com.mzx.pptparseserver.service.GetPPTPageService;
import com.mzx.pptparseserver.service.SyncService;
import com.mzx.pptparseserver.service.ParseToImgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by zison on 2016/4/20.
 */
@Controller
public class PPTFileController {

    @Autowired
    private ParseToImgService parseToImgService;
    @Autowired
    private SyncService pptSyncService;
    @Autowired
    private GetPPTPageService getPPTPageService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value="/upload")
    public String parseToImg(@RequestParam MultipartFile file, Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        logger.info("controller is running");


        Long pageNum = new Long(0);
        try {
            pageNum = parseToImgService.parseToImg(file.getOriginalFilename(),file.getInputStream());
            model.addAttribute("pageNum", pageNum.intValue());
            model.addAttribute("fileName",file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //初始化全局变量
        GlobalApplication.CurPPTInfo = new PPTInfo();
        GlobalApplication.CurPPTInfo.setCurPage(0);
        GlobalApplication.CurPPTInfo.setFileName(file.getOriginalFilename());
        GlobalApplication.CurPPTInfo.setPageNum(pageNum.intValue());
        GlobalApplication.CurPPTInfo.setImgBytes(getPPTPageService.getPPTPage(file.getOriginalFilename(), 0));

        pptSyncService.pptSync(file.getOriginalFilename(), 0);

        return "pptview";
    }
}
