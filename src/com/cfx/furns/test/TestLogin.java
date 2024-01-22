package com.cfx.furns.test;

import com.cfx.furns.dao.ILoginDao;
import com.cfx.furns.dao.daoimpl.LoginDaoImpl;
import com.cfx.furns.entity.Member;
import com.cfx.furns.utils.Logit;
import org.junit.Test;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/21 22:11
 **/
public class TestLogin {
    private static final String TAG = "TestLogin";
    ILoginDao mILoginDao = new LoginDaoImpl();

    @Test
    public void getMemberByNameAndPsd() {
        Member zhangsan = mILoginDao.getMemberByNameAndPsd(new Member(0, "zhangsan", "123456", null));
        Logit.d(TAG, zhangsan + "");
    }
}
