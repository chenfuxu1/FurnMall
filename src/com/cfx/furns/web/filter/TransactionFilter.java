package com.cfx.furns.web.filter;

import com.cfx.furns.utils.JdbcUtilsByDruid;
import com.cfx.furns.utils.Logit;

import javax.servlet.*;
import java.io.IOException;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/3/9 0:09
 *
 * 完成事务提交控制的过滤器
 **/
public class TransactionFilter implements Filter {
    private static final String TAG = "TransactionFilter";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Logit.d(TAG, "init...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Logit.d(TAG, "doFilter...");
        try {
            filterChain.doFilter(servletRequest, servletResponse);
            // 在后置代码中进行提交事务
            JdbcUtilsByDruid.commit();
        } catch (Exception e) {
            JdbcUtilsByDruid.rollback(); // 回滚
            Logit.d(TAG, "e: " + e);
        }
    }

    @Override
    public void destroy() {
        Logit.d(TAG, "destroy...");
    }
}
