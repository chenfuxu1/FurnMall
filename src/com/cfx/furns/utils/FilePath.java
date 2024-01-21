package com.cfx.furns.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Project: Ajax
 * Create By: Chen.F.X
 * DateTime: 2024/1/7 19:53
 **/
public class FilePath {
    /**
     * 该方法用于获取 src 路径下文件的绝对路径(兼容中文)
     * @param fileName 文件名
     * @return
     */
    public static String getAbsolutePath(String fileName) {
        String path = "";
        try {
            /**
             * 1.先获取根路径
             * /D:/F/%e5%ae%89%e5%8d%93%e8%b5%84%e6%96%99/JAVA/JAVA%e5%90%8e%e7%ab%af/%e9%9f%a9%e9%a1%ba%e5%b9%b3_JAVA_%e8%b7%af%e7%ba%bf/2.JAVA_WEB/11.JSON_Ajax_ThreadLocal_%e4%b8%8a%e4%bc%a0%e4%b8%8b%e8%bd%bd/%e4%bb%a3%e7%a0%81/Ajax/out/production/Ajax/
             */
            String baseUrl = FilePath.class.getResource("/").getPath();
            /**
             * 2.将 / 都替换成 \
             * \D:\F\%e5%ae%89%e5%8d%93%e8%b5%84%e6%96%99\JAVA\JAVA%e5%90%8e%e7%ab%af\%e9%9f%a9%e9%a1%ba%e5%b9%b3_JAVA_%e8%b7%af%e7%ba%bf\2.JAVA_WEB\11.JSON_Ajax_ThreadLocal_%e4%b8%8a%e4%bc%a0%e4%b8%8b%e8%bd%bd\%e4%bb%a3%e7%a0%81\Ajax\out\production\Ajax\
             */
            baseUrl = baseUrl.replace("/", "\\");
            // System.out.println(baseUrl);
            /**
             * 3.第一个位置的 \ 不需要
             * D:\F\%e5%ae%89%e5%8d%93%e8%b5%84%e6%96%99\JAVA\JAVA%e5%90%8e%e7%ab%af\%e9%9f%a9%e9%a1%ba%e5%b9%b3_JAVA_%e8%b7%af%e7%ba%bf\2.JAVA_WEB\11.JSON_Ajax_ThreadLocal_%e4%b8%8a%e4%bc%a0%e4%b8%8b%e8%bd%bd\%e4%bb%a3%e7%a0%81\Ajax\out\production\Ajax\
             */
            baseUrl = baseUrl.substring(1);
            /**
             * 4.可能有中文，需要解码一下
             * D:\F\安卓资料\JAVA\JAVA后端\韩顺平_JAVA_路线\2.JAVA_WEB\11.JSON_Ajax_ThreadLocal_上传下载\代码\Ajax\out\production\Ajax\
             */
            String decodeUrl = URLDecoder.decode(baseUrl, "utf-8");
            // System.out.println(decodeUrl);

            /**
             * 6.找到文件的绝对路径
             * D:\F\安卓资料\JAVA\JAVA后端\韩顺平_JAVA_路线\2.JAVA_WEB\11.JSON_Ajax_ThreadLocal_上传下载\代码\Ajax\out\production\Ajax\druid.properties
             */
            path = decodeUrl + fileName;

        } catch (UnsupportedEncodingException e) {
            System.out.println(e);
        }
        return path;
    }
}
