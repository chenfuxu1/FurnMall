package com.cfx.furns.web.servlet;

import com.cfx.furns.entity.Admin;
import com.cfx.furns.entity.Member;
import com.cfx.furns.service.IAdminService;
import com.cfx.furns.service.serviceimpl.AdminServiceImpl;
import com.cfx.furns.utils.Logit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.cfx.furns.utils.Constants.MEMBER;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/26 23:18
 **/
public class AdminServlet extends BaseServlet {
    private static final String TAG = "AdminServlet";
    private static final String DISPATCHER_ADMIN_SUCCESS = "/views/manage/manage_menu.jsp";
    private static final String DISPATCHER_ADMIN_FAIL = "/views/manage/manage_login.jsp";
    private IAdminService mAdminService = new AdminServiceImpl();

    /**
     * 管理员登录方法
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void adminLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logit.d(TAG, "admin...");
        String userName = req.getParameter("user_name");
        String userPassword = req.getParameter("user_password");
        Logit.d(TAG, "userName: " + userName + " userPassword: " + userPassword);
        if (userName == null || "".equals(userName)) {
            Logit.d(TAG, "输入用户名为空");
            return;
        }
        if (userPassword == null || "".equals(userPassword)) {
            Logit.d(TAG, "输入密码为空");
            return;
        }
        Admin admin = new Admin();
        admin.setUserName(userName);
        admin.setPassword(userPassword);
        boolean succeed = mAdminService.isLoginSucceed(admin);
        if (!succeed) {
            Logit.d(TAG, "登录失败");
            req.getRequestDispatcher(DISPATCHER_ADMIN_FAIL).forward(req, resp);
            return;
        }
        Member member = new Member(0, userName, userPassword, null);
        HttpSession session = req.getSession();
        session.setAttribute(MEMBER, member);
        req.getRequestDispatcher(DISPATCHER_ADMIN_SUCCESS).forward(req, resp);
        Logit.d(TAG, "登录成功");
    }
}
