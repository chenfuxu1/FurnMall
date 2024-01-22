package com.cfx.furns.dao.daoimpl;

import com.cfx.furns.dao.BasicDao;
import com.cfx.furns.dao.ILoginDao;
import com.cfx.furns.entity.Member;
import com.cfx.furns.utils.Logit;

import static com.cfx.furns.utils.Constants.TABLE_MEMBER;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/21 22:05
 *
 * 用户登录 dao 接口实现类
 **/
public class LoginDaoImpl extends BasicDao<Member> implements ILoginDao {
    private static final String TAG = "LoginDaoImpl";

    @Override
    public Member getMemberByNameAndPsd(Member member) {
        if (member == null) {
            Logit.d(TAG, "member is empty");
            return null;
        }
        String userName = member.getUserName();
        String password = member.getPassword();
        if (userName == null || "".equals(userName)) {
            Logit.d(TAG, "userName is empty");
            return null;
        }
        if (password == null || "".equals(password)) {
            Logit.d(TAG, "password is empty");
            return null;
        }
        String sql = "select * from " + TABLE_MEMBER + " where userName = ? and password = MD5(?)";
        return querySingle(sql, Member.class, userName, password);
    }
}
