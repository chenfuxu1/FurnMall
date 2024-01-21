package com.cfx.furns.utils;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/17 23:35
 **/
public class Logit {
    public static void d(String tag, String msg) {
        System.out.println(tag + " " + msg);
    }

    public static void d(String tag, String msg, Throwable throwable) {
        System.out.println(tag + " " + msg + "\n" + throwable.getMessage());
    }
}
