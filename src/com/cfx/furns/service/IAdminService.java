package com.cfx.furns.service;

import com.cfx.furns.entity.Admin;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/21 22:14
 **/
public interface IAdminService {
    // 判断管理员是否登录成功
    public boolean isLoginSucceed(Admin admin);
}
