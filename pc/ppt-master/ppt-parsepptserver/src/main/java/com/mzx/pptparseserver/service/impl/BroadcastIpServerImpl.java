package com.mzx.pptparseserver.service.impl;

import com.mzx.pptparseserver.service.BroadcastIpServer;
import com.mzx.pptparseserver.utility.NetWorkOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by zison on 2016/5/4.
 */
@Service
public class BroadcastIpServerImpl implements BroadcastIpServer {

    private static Logger logger = LoggerFactory.getLogger(NetWorkOption.class);

    @Override
    public void boadcastIp(final String ip) {
        final String broadcastIp = NetWorkOption.getBroadcastIP(ip);

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                        DatagramSocket dgSocket = new DatagramSocket(8989);
                        byte[] by = ip.getBytes();
                        DatagramPacket packet = new DatagramPacket(by,
                                by.length, InetAddress
                                .getByName(broadcastIp),
                                8989);
                        dgSocket.send(packet);

                        dgSocket.close();
                        logger.info("broadcastIp success");
                    }catch (Exception e) {
                        logger.info("broadcastIp false");
                    }

                }
            }
        }.start();


    }
}
