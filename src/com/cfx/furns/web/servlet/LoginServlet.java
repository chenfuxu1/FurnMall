package com.cfx.furns.web.servlet;

import com.cfx.furns.entity.Member;
import com.cfx.furns.service.ILoginService;
import com.cfx.furns.service.serviceimpl.LoginServiceImpl;
import com.cfx.furns.utils.Logit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/21 22:21
 *
 * 完成登录功能的 servlet
 **/
public class LoginServlet extends HttpServlet {
    private static final String TAG = "LoginServlet";
    private static final String DISPATCHER_LOGIN_SUCCESS = "/views/member/login_success.html";
    private static final String DISPATCHER_LOGIN_FAILED = "/views/member/login.html";
    private ILoginService mLoginService = new LoginServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logit.d(TAG, "doGet...");
        String userName = req.getParameter("login_user_name");
        String password = req.getParameter("login_password");
        Logit.d(TAG, "userName: " + userName + " password: " + password);
        Member member = new Member(0, userName, password, null);
        boolean isSucceed = mLoginService.isLoginSucceed(member);
        if (!isSucceed) {
            // 登录失败，转发到登录界面
            Logit.d(TAG, "登录失败！");
            req.getRequestDispatcher(DISPATCHER_LOGIN_FAILED).forward(req, resp);
            return;
        }
        // 登录成功，转发到成功界面
        Logit.d(TAG, "登录成功！");
        req.getRequestDispatcher(DISPATCHER_LOGIN_SUCCESS).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logit.d(TAG, "doPost...");
        doGet(req, resp);
    }
}
