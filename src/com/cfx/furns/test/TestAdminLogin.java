package com.cfx.furns.test;

import com.cfx.furns.entity.Admin;
import com.cfx.furns.service.IAdminService;
import com.cfx.furns.service.serviceimpl.AdminServiceImpl;
import com.cfx.furns.utils.Logit;
import org.junit.Test;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/21 22:11
 **/
public class TestAdminLogin {
    private static final String TAG = "TestAdminLogin";
    private IAdminService mAdminService = new AdminServiceImpl();

    @Test
    public void isLoginSucceed() {
        Admin admin = new Admin();
        admin.setUserName("cfxAdmin");
        admin.setPassword("532443");
        boolean succeed = mAdminService.isLoginSucceed(admin);
        Logit.d(TAG, "succeed；" + succeed);
    }
}
