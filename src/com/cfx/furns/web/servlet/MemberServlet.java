package com.cfx.furns.web.servlet;

import com.cfx.furns.entity.Member;
import com.cfx.furns.service.ILoginService;
import com.cfx.furns.service.IMemberService;
import com.cfx.furns.service.serviceimpl.LoginServiceImpl;
import com.cfx.furns.service.serviceimpl.MemberServiceImpl;
import com.cfx.furns.utils.Logit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.cfx.furns.utils.Constants.MEMBER;
import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/23 23:34
 * <p>
 * 合并用户登录 / 注册功能的 servlet
 **/
public class MemberServlet extends BaseServlet {
    private static final String TAG = "MemberServlet";
    private static final String ACTION_LOGIN = "login"; // 登录的表单
    private static final String ACTION_REGISTER = "register"; // 注册的表单
    private static final String DISPATCHER_LOGIN_SUCCESS = "/views/member/login_success.jsp";
    private static final String DISPATCHER_LOGIN_FAILED = "/views/member/login.jsp";
    private static final String DISPATCHER_INDEX = "/index.jsp";
    private static final String DISPATCHER_REGISTER_SUCCESS = "/views/member/register_success.html";
    private static final String DISPATCHER_REGISTER_FAIL = "/views/member/register_fail.html";
    private IMemberService mMemberService = new MemberServiceImpl();
    private ILoginService mLoginService = new LoginServiceImpl();
    private String mUserName = "";
    private String mPassword = "";

    /**
     * 处理登录的请求
     *
     * @param req
     * @param resp
     */
    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        mUserName = req.getParameter("login_user_name");
        mPassword = req.getParameter("login_password");
        Logit.d(TAG, "userName: " + mUserName + " password: " + mPassword);
        Member member = new Member(0, mUserName, mPassword, null);
        boolean isSucceed = mLoginService.isLoginSucceed(member);

        if (!isSucceed) {
            // 登录失败，将失败的错误信息回送到前端
            req.setAttribute("login_error_msg", "用户名或密码错误！");
            req.setAttribute("login_user_name", mUserName);
            // 登录失败，转发到登录界面
            Logit.d(TAG, "登录失败！");
            req.getRequestDispatcher(DISPATCHER_LOGIN_FAILED).forward(req, resp);
            return;
        }
        // 登录成功，转发到成功界面
        Logit.d(TAG, "登录成功！");

        HttpSession session = req.getSession();
        session.setAttribute(MEMBER, member);
        req.getRequestDispatcher(DISPATCHER_LOGIN_SUCCESS).forward(req, resp);
    }

    /**
     * 处理退出登录逻辑
     *
     * 只需将 session 销毁即可
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logit.d(TAG, "logout...");
        // 1.销毁 session
        HttpSession session = req.getSession();
        session.invalidate();
        // 2.转发页面到 index.jsp
        req.getRequestDispatcher(DISPATCHER_INDEX).forward(req, resp);
    }


    /**
     * 处理注册的请求
     *
     * @param req
     * @param resp
     */
    private void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        mUserName = req.getParameter("username");
        mPassword = req.getParameter("password");
        String email = req.getParameter("email");
        // 1.获取用户提交的验证码
        String code = req.getParameter("code");
        // 2.从 session 中获取缓存的验证码进行比对
        HttpSession session = req.getSession();
        String token = (String) session.getAttribute(KAPTCHA_SESSION_KEY);
        // 3.立即删除 session 缓存的验证码，防止该验证码被重复使用
        session.removeAttribute(KAPTCHA_SESSION_KEY);
        Logit.d(TAG, "userName: " + mUserName + " password: " + mPassword + " email: " + email + " code: " + code);
        if (token == null || !token.equalsIgnoreCase(code)) {
            // 验证失败，直接俄返回验证码不正确
            Logit.d(TAG, "验证码错误");
            req.setAttribute("register_error_msg", "验证码错误！");
            req.setAttribute("register_email", email);
            req.setAttribute("register_user_name", mUserName);
            req.setAttribute("active", "register");
            req.getRequestDispatcher(DISPATCHER_LOGIN_FAILED).forward(req, resp);
            return;
        }
        boolean existUserName = mMemberService.isExistUserName(mUserName);
        if (existUserName) {
            // 用户名存在，返回注册失败界面
            Logit.d(TAG, "用户名已注册过");
            req.setAttribute("register_error_msg", "用户名已注册过！");
            req.setAttribute("register_email", email);
            req.setAttribute("register_user_name", mUserName);
            req.setAttribute("active", "register");
            req.getRequestDispatcher(DISPATCHER_LOGIN_FAILED).forward(req, resp);
            return;
        }
        // 用户名不存在，开始注册用户
        Member member = new Member(0, mUserName, mPassword, email);
        boolean result = mMemberService.registerMember(member);
        if (!result) {
            // 注册失败，返回失败界面
            req.getRequestDispatcher(DISPATCHER_LOGIN_FAILED).forward(req, resp);
            return;
        }
        // 注册成功，返回成功界面
        req.getRequestDispatcher(DISPATCHER_REGISTER_SUCCESS).forward(req, resp);
    }
}
