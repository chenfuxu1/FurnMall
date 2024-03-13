package com.cfx.furns.web.filter;

import com.cfx.furns.entity.Member;
import com.cfx.furns.utils.Logit;
import com.cfx.furns.utils.WebUtils;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.cfx.furns.utils.Constants.MEMBER;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/3/4 23:43
 *
 * 权限的过滤器，校验用户有没有登录
 **/
public class AuthorityFilter implements Filter {
    private static final String TAG = "AuthorityFilter";
    private final List<String> mExcludeUrls = new ArrayList<>(); // 保存需要排除放行的 url

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Logit.d(TAG, "init...");
        String excludedUrls = filterConfig.getInitParameter("excludedUrls");
        String[] excludeds = excludedUrls.split(",");
        mExcludeUrls.addAll(Arrays.asList(excludeds));
        Logit.d(TAG, "mExcludeUrls: " + mExcludeUrls);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Logit.d(TAG, "doFilter...");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        Logit.d(TAG, "url: " + httpServletRequest.getServletPath());
        // 表明是需要拦截的 url
        if (!mExcludeUrls.contains(httpServletRequest.getServletPath())) {
            // 1.获取 session 中是否有 member 用户对象
            Object memberObj = httpServletRequest.getSession().getAttribute(MEMBER);
            if (!(memberObj instanceof Member)) {
                if (WebUtils.isAjaxRequest(httpServletRequest)) {
                    /**
                     * 该请求为 ajax 请求，浏览器不会进行转发、重定向
                     * 可以将要转发的 url 以 json 的格式返回
                     * 在前端检测到有 url，那么进行转发
                     */
                    HashMap<String, String> resultMap = new HashMap();

                    resultMap.put("url", "views/member/login.jsp");
                    Gson gson = new Gson();
                    String result = gson.toJson(resultMap);
                    servletResponse.getWriter().write(result);
                    servletResponse.getWriter().flush();
                } else {
                    /**
                     * 用户没有登录，直接跳转到用户登录界面
                     * 这里使用请求转发，不能使用重定向，因为使用重定向还会走过滤器，形成死循环
                     * 但使用请求转发不会在走过滤器了，直接转发到用户登录界面
                     */
                    httpServletRequest.getRequestDispatcher("/views/member/login.jsp")
                            .forward(servletRequest, servletResponse);
                    Logit.d(TAG, "user is not login");
                }

                return;
            }
        }
        // 2.表明不需要拦截，或者拦截的 url 用户存在，接着往下执行
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Logit.d(TAG, "destroy...");
    }
}
