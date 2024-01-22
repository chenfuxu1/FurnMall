package com.cfx.furns.dao;

import com.cfx.furns.entity.Member;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/21 22:03
 *
 * 用户登录接口
 **/
public interface ILoginDao {
    // 根据输入的用户名和密码。查询数据库中是否存在用户
    public Member getMemberByNameAndPsd(Member member);
}
