package com.cfx.furns.web.servlet;

import com.cfx.furns.utils.Logit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/24 23:49
 * <p>
 * 提供 BaseServlet 类模板
 * 根据动态绑定机制，通过反射来动态调用子类的方法
 **/
public abstract class BaseServlet extends HttpServlet {
    private static final String TAG = "BaseServlet";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logit.d(TAG, "doGet...");
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String action = req.getParameter("action");
        Logit.d(TAG, "action: " + action);

        try {
            /**
             * 使用 模板模式 + 反射 + 动态绑定机制，简化了 if else 分支判断
             * class com.cfx.furns.web.servlet.MemberServlet
             * this.getClass() 是动态变化的，根据请求进行动态绑定
             */
            Logit.d(TAG, "this.getClass(): " + this.getClass());
            /**
             * 通过反射调佣 MemberServlet 类里面的 action 方法
             * 其中 action 也是动态的，根据前端表单来决定的
             * 所以前端表单的 action 和 MemberServlet 类的 action 方法需要约定好，这样才能反射找到该方法
             * 一定要保证子类中包含有 action 对应的方法
             */
            Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            method.setAccessible(true);
            // 反射调用该方法
            method.invoke(this, req, resp);
        } catch (Exception e) {
            Logit.d(TAG, "Exception: " + e);
            resp.getWriter().write("<h1>请求参数有误！</h1>");
            resp.getWriter().flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logit.d(TAG, "doPost...");
        doGet(req, resp);
    }
}
