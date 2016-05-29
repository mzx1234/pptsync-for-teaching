package com.mzx.pptparseserver.controller;


import com.mzx.pptparseserver.service.GetPPTPageService;
import com.mzx.pptparseserver.service.impl.GetPPTPageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by zison on 2016/4/21.
 */
@Controller
public class PPTPageController {

    @Autowired
    private GetPPTPageService getPPTPageService;

    private Logger logger = LoggerFactory.getLogger(getClass());


    @RequestMapping(value = "/pptpage")
    public void getPPTPage(String fileName, int index, HttpServletResponse response) {
        logger.info("getPPTPage method running");
        byte[] data = getPPTPageService.getPPTPage(fileName, index );

        response.setContentType("image/png");

        OutputStream stream = null;
        try {
            stream = response.getOutputStream();
            stream.write(data);
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
