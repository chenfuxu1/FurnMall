package com.cfx.furns.service.serviceimpl;

import com.cfx.furns.dao.IMemberDao;
import com.cfx.furns.dao.daoimpl.MemberDaoImpl;
import com.cfx.furns.entity.Member;
import com.cfx.furns.service.IMemberService;
import com.cfx.furns.utils.Logit;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/20 13:57
 **/
public class MemberServiceImpl implements IMemberService {
    private static final String TAG = "MemberServiceImpl";
    private IMemberDao mIMemberDao = new MemberDaoImpl();

    /**
     * 注册一个用户
     * @param member
     * @return true 注册成功 false：注册失败
     */
    @Override
    public boolean registerMember(Member member) {
        if (member == null) {
            Logit.d(TAG, "member is empty");
            return false;
        }
        String userName = member.getUserName();
        if (userName == null || "".equals(userName)) {
            Logit.d(TAG, "userName is empty");
            return false;
        }
        boolean existUserName = isExistUserName(userName);
        if (existUserName) {
            Logit.d(TAG, "userName is exist");
            return false;
        }
        boolean result = mIMemberDao.saveMember(member) == 1;
        Logit.d(TAG, "register result: " + result);
        return result;
    }

    /**
     * 判断用户名是否存在
     * @param userName
     * @return true 表示存在 false 表示不存在
     */
    @Override
    public boolean isExistUserName(String userName) {
        // 使用 ctrl + alt + b 可以定位到实现类的方法
        Member member = mIMemberDao.queryMemberByName(userName);
        return member != null;
    }
}
