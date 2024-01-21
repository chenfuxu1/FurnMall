package com.cfx.furns.test;

import com.cfx.furns.entity.Member;
import com.cfx.furns.service.IMemberService;
import com.cfx.furns.service.serviceimpl.MemberServiceImpl;
import com.cfx.furns.utils.Logit;
import com.cfx.furns.utils.MD5Utils;
import org.junit.Test;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/20 14:09
 **/
public class TestMemberService {
    private static final String TAG = "TestMemberService";
    private IMemberService mMemberService = new MemberServiceImpl();

    @Test
    public void testRegisterUser() {
        Member member = new Member();
        member.setUserName("tom");
        member.setPassword("12312312");
        member.setEmail("refwr@qq.com");
        boolean result = mMemberService.registerMember(member);
        Logit.d(TAG, "注册是否成功：" + result);
    }

    @Test
    public void testUserExist() {
        boolean result = mMemberService.isExistUserName("cfx");
        Logit.d(TAG, "用户名是否存在：" + result);
    }

    @Test
    public void aa() {
        System.out.println(MD5Utils.stringToMD5("123456"));
    }
}
