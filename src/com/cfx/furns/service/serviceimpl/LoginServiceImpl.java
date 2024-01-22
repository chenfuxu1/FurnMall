package com.cfx.furns.service.serviceimpl;

import com.cfx.furns.dao.ILoginDao;
import com.cfx.furns.dao.daoimpl.LoginDaoImpl;
import com.cfx.furns.entity.Member;
import com.cfx.furns.service.ILoginService;
import com.cfx.furns.utils.Logit;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/21 22:16
 **/
public class LoginServiceImpl implements ILoginService {
    private static final String TAG = "LoginServiceImpl";
    private ILoginDao mLoginDao = new LoginDaoImpl();

    /**
     * 判断用户是否登录成功
     * @return true: 登录成功 false：登录失败
     */
    @Override
    public boolean isLoginSucceed(Member member) {
        Member result = mLoginDao.getMemberByNameAndPsd(member);
        if (result == null) {
            Logit.d(TAG, "登录失败");
            return false;
        }
        Logit.d(TAG, "登录成功");
        return true;
    }
}
