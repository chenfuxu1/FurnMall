package com.cfx.furns.test;

import com.cfx.furns.dao.IAdminDao;
import com.cfx.furns.dao.IMemberDao;
import com.cfx.furns.dao.daoimpl.AdminDaoImpl;
import com.cfx.furns.dao.daoimpl.MemberDaoImpl;
import com.cfx.furns.entity.Admin;
import com.cfx.furns.entity.Member;
import com.cfx.furns.utils.Logit;
import org.junit.Test;

import java.util.List;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/20 13:22
 **/
public class TestAdminDao {
    private static final String TAG = "TestAdminDao";

    @Test
    public void getAllAdmins() {
        IAdminDao adminDao = new AdminDaoImpl();
        List<Admin> allAdmins = adminDao.getAllAdmins();
        for (Admin allAdmin : allAdmins) {
            Logit.d(TAG, allAdmin.toString());
        }

    }

    @Test
    public void saveAdmin() {
        IAdminDao adminDao = new AdminDaoImpl();
        Admin admin = new Admin();
        admin.setUserName("cfxAdmin");
        admin.setPassword("532443");
        admin.setEmail("wwww@fsdf.com");
        int result = adminDao.saveAdmin(admin);
        Logit.d(TAG, "result: " + result);
    }

    @Test
    public void queryMemberByName() {
        IAdminDao adminDao = new AdminDaoImpl();
        Admin admin = adminDao.queryAdminByName("cfxAdmin");
        Logit.d(TAG, "member: " + admin);
    }
}
