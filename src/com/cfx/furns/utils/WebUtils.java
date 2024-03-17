package com.cfx.furns.utils;


import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/3/13 20:30
 **/
public class WebUtils {
    /**
     * 判断请求是否为 ajax 请求
     * @param httpServletRequest
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest httpServletRequest) {
        if (httpServletRequest == null) {
            return false;
        }
        String requestedWith = httpServletRequest.getHeader("X-Requested-With");
        if (requestedWith == null || "".equals(requestedWith)) {
            return false;
        }
        return "XMLHttpRequest".equals(requestedWith);
    }

    public static String getYearMonthDay() {
        LocalDateTime localDateTime = LocalDateTime.now();
        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();
        int day = localDateTime.getDayOfMonth();
        String yearMonthDay = year + "-" + month + "-" + day + "/";
        return yearMonthDay;
    }
}
