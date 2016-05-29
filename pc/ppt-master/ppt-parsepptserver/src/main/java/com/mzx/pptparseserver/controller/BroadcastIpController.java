package com.mzx.pptparseserver.controller;

import com.mzx.pptparseserver.service.BroadcastIpServer;
import com.mzx.pptparseserver.utility.NetWorkInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by zison on 2016/5/10.
 */
@Controller
@RequestMapping(value="/IP")
public class BroadcastIpController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BroadcastIpServer broadcastIpServer;

    @ResponseBody
    @RequestMapping(value="/getIP")
    public String [] getIP() {

        logger.info("getIP method running");

        String [] ips = NetWorkInfo.getAllLocalHostIP();
        return ips;

    }

    @RequestMapping(value="/broadcastIP")
    public void broadcastIP(String ip) {

        logger.info("broadcastIP method running");
        logger.info("tend to broadcast ip add is :"+ip);

        broadcastIpServer.boadcastIp(ip);

    }


}
