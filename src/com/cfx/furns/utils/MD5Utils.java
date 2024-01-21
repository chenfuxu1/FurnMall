package com.cfx.furns.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/21 19:56
 **/
public class MD5Utils {
    public static String stringToMD5(String content) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(content.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个 md5 算法");
        }
        String md5Code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5Code.length(); i++) {
            md5Code = "0" + md5Code;
        }
        return md5Code;
    }
}
