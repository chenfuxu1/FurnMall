package com.cfx.furns.test;

import com.cfx.furns.entity.Member;
import com.cfx.furns.service.ILoginService;
import com.cfx.furns.service.serviceimpl.LoginServiceImpl;
import org.junit.Test;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/21 22:19
 **/
public class TestLoginService {
    @Test
    public void isLoginSucceed() {
        ILoginService loginService = new LoginServiceImpl();
        boolean result = loginService.isLoginSucceed(new Member(0, "zhangsan", "123456", null));
        System.out.println(result);
    }
}
