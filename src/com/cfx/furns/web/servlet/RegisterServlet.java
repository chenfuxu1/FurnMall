package com.cfx.furns.web.servlet;

import com.cfx.furns.entity.Member;
import com.cfx.furns.service.IMemberService;
import com.cfx.furns.service.serviceimpl.MemberServiceImpl;
import com.cfx.furns.utils.Logit;
import com.google.code.kaptcha.servlet.KaptchaServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/20 14:15
 *
 * 注册用户的 servlet
 **/
public class RegisterServlet extends HttpServlet {
    private static final String TAG = "RegisterServlet";
    private static final String DISPATCHER_REGISTER_SUCCESS = "/views/member/register_success.html";
    private static final String DISPATCHER_REGISTER_FAIL = "/views/member/register_fail.html";
    private IMemberService mMemberService = new MemberServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logit.d(TAG, "doGet...");
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        Logit.d(TAG, "userName: " + userName + " password: " + password + " email: " + email);
        boolean existUserName = mMemberService.isExistUserName(userName);
        if (existUserName) {
            // 用户名存在，返回注册失败界面
            req.getRequestDispatcher(DISPATCHER_REGISTER_FAIL).forward(req, resp);
        }
        // 用户名不存在，开始注册用户
        Member member = new Member(0, userName, password, email);
        boolean result = mMemberService.registerMember(member);
        if (!result) {
            // 注册失败，返回失败界面
            req.getRequestDispatcher(DISPATCHER_REGISTER_FAIL).forward(req, resp);
        }
        // 注册成功，返回成功界面
        req.getRequestDispatcher(DISPATCHER_REGISTER_SUCCESS).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logit.d(TAG, "doPost...");
        doGet(req, resp);
    }
}
