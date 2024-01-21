package com.cfx.furns.test;

import com.cfx.furns.dao.IMemberDao;
import com.cfx.furns.dao.daoimpl.MemberDaoImpl;
import com.cfx.furns.entity.Member;
import com.cfx.furns.utils.Logit;
import org.junit.Test;

import java.util.List;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/20 13:22
 **/
public class TestMemberDao {
    private static final String TAG = "TestMemberDao";

    @Test
    public void getAllMembers() {
        IMemberDao memberDao = new MemberDaoImpl();
        List<Member> allMembers = memberDao.getAllMembers();
        for (Member member : allMembers) {
            Logit.d(TAG, member.toString());
        }

    }

    @Test
    public void saveMember() {
        IMemberDao memberDao = new MemberDaoImpl();
        Member member = new Member();
        member.setUserName("cfx");
        member.setPassword("532443");
        member.setEmail("wwww@fsdf.com");
        int result = memberDao.saveMember(member);
        Logit.d(TAG, "result: " + result);
    }

    @Test
    public void queryMemberByName() {
        IMemberDao memberDao = new MemberDaoImpl();
        Member member = memberDao.queryMemberByName("cfx");
        Logit.d(TAG, "member: " + member);
    }
}
