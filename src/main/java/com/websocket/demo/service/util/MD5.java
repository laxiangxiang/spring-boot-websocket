package com.websocket.demo.service.util;

import java.security.MessageDigest;

public class MD5 {

    public static String getMD5ofStr(String str){
        StringBuffer md5Str = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] output = md.digest(str.getBytes());
            for (int i = 0; i<output.length;i++){
                if (Integer.toHexString(0xFF & output[i]).length() == 1){
                    md5Str.append("0").append(Integer.toHexString(0xFF & output[i]));
                }else {
                    md5Str.append(Integer.toHexString(0xFF & output[i]));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("密码加密失败"+e.getMessage());
        }
        return md5Str.toString();
    }
}
