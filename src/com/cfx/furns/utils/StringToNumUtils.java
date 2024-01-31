package com.cfx.furns.utils;

import java.math.BigDecimal;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/27 20:38
 **/
public class StringToNumUtils {
    private static final String TAG = "StringToNumUtils";

    /**
     * 将字符串转化为 int 值，如果失败，转换为默认值返回
     * @param strNum
     * @param defaultVal
     * @return
     */
    public static int parseInt(String strNum, int defaultVal) {
        if (strNum == null || "".equals(strNum)) {
            return defaultVal;
        }
        try {
            return Integer.parseInt(strNum);
        } catch (NumberFormatException e) {
            System.out.println(TAG + " NumberFormatException: " + e);
            return defaultVal;
        }
    }

    /**
     * 将字符串转化为 Integer 值，如果失败，转换为默认值 null
     * @param strNum
     * @return
     */
    public static Integer parseInteger(String strNum) {
        if (strNum == null || "".equals(strNum)) {
            return null;
        }
        try {
            return Integer.parseInt(strNum);
        } catch (NumberFormatException e) {
            System.out.println(TAG + " NumberFormatException: " + e);
            return null;
        }
    }

    /**
     * 将字符串转化为 BigDecimal 值，如果失败，转换为默认值 null
     * @param strNum
     * @return
     */
    public static BigDecimal parseBigDecimal(String strNum) {
        if (strNum == null || "".equals(strNum)) {
            return null;
        }
        try {
            return new BigDecimal(strNum);
        } catch (NumberFormatException e) {
            System.out.println(TAG + " NumberFormatException: " + e);
            return null;
        }
    }
}
