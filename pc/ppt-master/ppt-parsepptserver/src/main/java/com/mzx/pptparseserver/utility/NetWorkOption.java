package com.mzx.pptparseserver.utility;

import com.mzx.pptcommon.entity.NetAddress;
import com.mzx.pptparseserver.Main.Main;
import com.mzx.pptparseserver.application.GlobalApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * �㲥ip������
 * Created by zison on 2016/1/17.
 */
public class NetWorkOption {

    private static Logger logger = LoggerFactory.getLogger(NetWorkOption.class);
    private static String sub= "255.255.255.0";

    public static void broadcastIP(String ip) {
        String subnetString = sub;

        NetWorkInfo netWorkInfo = new NetWorkInfo(ip,
                subnetString);
        String broadcastAddress = netWorkInfo.getBroadcastAddress();
        logger.info("�㲥IPΪ:" + broadcastAddress);

        NetAddress netAddress = new NetAddress();
        netAddress.setBrocastIp(broadcastAddress);
        netAddress.setIp(ip);

        //����IP�͹㲥IPȫ�ֱ���
        GlobalApplication.netAddress = netAddress;


    }


    public static String getBroadcastIP(String ip) {
        String subnetString = sub;
        NetWorkInfo netWorkInfo = new NetWorkInfo(ip,
                subnetString);
        return netWorkInfo.getBroadcastAddress();
    }
}
