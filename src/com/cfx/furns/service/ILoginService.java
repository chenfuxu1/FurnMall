package com.cfx.furns.service;

import com.cfx.furns.entity.Member;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/21 22:14
 **/
public interface ILoginService {
    // 判断用户是否登录成功
    public boolean isLoginSucceed(Member member);
}
