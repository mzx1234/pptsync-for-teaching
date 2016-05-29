package com.mzx.pptcommon.entity;

/**
 * Created by zison on 2016/5/4.
 */
public class NetAddress {

    String ip;
    String brocastIp;
    int brocastPort;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBrocastIp() {
        return brocastIp;
    }

    public void setBrocastIp(String brocastIp) {
        this.brocastIp = brocastIp;
    }

    public int getBrocastPort() {
        return brocastPort;
    }

    public void setBrocastPort(int brocastPort) {
        this.brocastPort = brocastPort;
    }
}
