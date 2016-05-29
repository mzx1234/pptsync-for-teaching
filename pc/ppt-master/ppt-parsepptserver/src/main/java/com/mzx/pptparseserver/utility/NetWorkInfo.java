package com.mzx.pptparseserver.utility;


import java.net.InetAddress;

public class NetWorkInfo {
     
	String subnet;  
    String ip;
    public NetWorkInfo(String ip, String subnet)
    {
    	this.ip=ip;
    	this.subnet=subnet;
    }
          
      
     /**
      * ���ݱ���IP���������룬���������㲥��ַ 
     * @return String
     */
    public String getBroadcastAddress() {  
         String[] ips = ip.split("\\.");   
         String[] subnets = subnet.split("\\.");  
         StringBuffer sb = new StringBuffer();  
         for(int i = 0; i < ips.length; i++) {  
             ips[i] = String.valueOf((~Integer.parseInt(subnets[i]))|(Integer.parseInt(ips[i])));  
             sb.append(turnToStr(Integer.parseInt(ips[i])));  
             if(i != (ips.length-1))  
                 sb.append(".");  
         }      
//         System.out.println(turnToIp(sb.toString()));
         return turnToIp(sb.toString());  
    }  
  
     
     /** 
      * �Ѵ���������ת��Ϊ������ 
      * @param num 
      * @return 
      */  
    private String turnToStr(int num) {  
        String str = "";  
        str = Integer.toBinaryString(num);            
        int len = 8 - str.length();  
        // �����������������8λ,��ǰ�油��.  
        for (int i = 0; i < len; i++) {  
            str = "0" + str;  
        }  
        //���numΪ������תΪ�����ƵĽ����32λ����1111 1111 1111 1111 1111 1111 1101 1110  
        //��ֻȡ����8λ.  
        if (len < 0)  
            str = str.substring(24, 32);  
        return str;  
    }      
      
    /** 
     * �Ѷ�������ʽ��ip��ת��Ϊʮ������ʽ��ip 
     * @param str 
     * @return 
     */  
    private String turnToIp(String str){  
        String[] ips = str.split("\\.");  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < ips.length; i++) {  
            sb.append(turnToInt(ips[i]));  
            sb.append(".");  
        }            
        sb.deleteCharAt(sb.length() - 1);  
        return sb.toString();  
    }  
  
    /** 
     * �Ѷ�����ת��Ϊʮ���� 
     * @param str 
     * @return 
     */  
    private int turnToInt(String str){  
        int total = 0;  
        int top = str.length();  
        for (int i = 0; i < str.length(); i++) {  
            String h = String.valueOf(str.charAt(i));  
            top--;  
            total += ((int) Math.pow(2, top)) * (Integer.parseInt(h));  
        }  
        return total;  
    }

    private static String getLocalHostName() {
        String hostName;
        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostName = addr.getHostName();
        } catch (Exception ex) {
            hostName = "";
        }
        return hostName;
    }

    public static  String[] getAllLocalHostIP() {
        String[] ret = null;
        try {
            String hostName = getLocalHostName();
            if (hostName.length() > 0) {
                InetAddress[] addrs = InetAddress.getAllByName(hostName);
                if (addrs.length > 0) {
                    ret = new String[addrs.length];
                    System.out.println(addrs.length);
                    for (int i = 0; i < addrs.length; i++) {
                        ret[i] = addrs[i].getHostAddress();

                    }
                }
            }

        } catch (Exception ex) {
            ret = null;
        }
        return ret;
    }
    
    
    
} 