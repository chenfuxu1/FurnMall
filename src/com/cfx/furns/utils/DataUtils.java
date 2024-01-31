package com.cfx.furns.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/31 21:37
 **/
public class DataUtils {
    private static final String TAG = "DataUtils";

    // 将请求参数的 Map 封装到 JavaBean 中
    public static <T> T convertParamsToBean(T bean, Map params) {
        try {
            /**
             * 将 params 的数据封装到 T 对象中
             * 前提就是 params 中表单提交的参数需要和 JavaBean 中的成员属性名称能够对应上
             * 否则是没有办法封装的，例如提交的 price，在 Furn 反射 setPrice 方法
             */
            BeanUtils.populate(bean, params);
            Logit.d(TAG, "bean: " + bean);
        } catch (Exception e) {
            Logit.d(TAG, "Exception: " + e);
        }
        return bean;
    }
}
