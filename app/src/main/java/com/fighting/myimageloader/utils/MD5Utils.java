package com.fighting.myimageloader.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 描述：
 * Created by MaJD on 2016/8/4.
 */

public class MD5Utils {

    public static void main(String args){
        String str = toMD5("123");
        System.out.println(str);
    }

    public static String toMD5(String key) {
        try {
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            byte[] bytes = mDigest.digest(key.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

}
