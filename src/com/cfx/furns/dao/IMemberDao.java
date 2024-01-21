package com.cfx.furns.dao;

import com.cfx.furns.entity.Member;

import java.util.List;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/20 13:16
 *
 * 会员 MemberDao 接口
 **/
public interface IMemberDao {
    // 根据用户名返回对应的 Member
    public Member queryMemberByName(String userName);

    // 保存 member 对象到数据库中
    public int saveMember(Member member);

    // 查询 member 表中所有的数据
    public List<Member> getAllMembers();
}
