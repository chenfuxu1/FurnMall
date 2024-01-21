package com.cfx.furns.service;

import com.cfx.furns.entity.Member;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/20 13:55
 **/
public interface IMemberService {
    // 注册用户
    public boolean registerMember(Member member);

    // 判断用户名数据表中是否存在
    public boolean isExistUserName(String userName);
}
