package com.cfx.furns.service.serviceimpl;

import com.cfx.furns.dao.IAdminDao;
import com.cfx.furns.dao.daoimpl.AdminDaoImpl;
import com.cfx.furns.entity.Admin;
import com.cfx.furns.service.IAdminService;
import com.cfx.furns.utils.Logit;
import com.cfx.furns.utils.MD5Utils;

import java.util.List;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/21 22:16
 **/
public class AdminServiceImpl implements IAdminService {
    private static final String TAG = "AdminServiceImpl";
    private IAdminDao mAdminDao = new AdminDaoImpl();

    /**
     * 判断用户是否登录成功
     * @return true: 登录成功 false：登录失败
     */
    @Override
    public boolean isLoginSucceed(Admin admin) {
        if (admin == null) {
            Logit.d(TAG, "输入参数有误！");
            return false;
        }
        if (admin.getUserName() == null || "".equals(admin.getUserName())) {
            Logit.d(TAG, "输入用户名为空！");
            return false;
        }
        if (admin.getPassword() == null || "".equals(admin.getPassword())) {
            Logit.d(TAG, "输入密码为空！");
            return false;
        }
        Admin result = mAdminDao.queryAdminByName(admin.getUserName());
        if (result == null) {
            Logit.d(TAG, "管理员登录失败");
            return false;
        }
        if (!admin.getUserName().equals(result.getUserName()) || !MD5Utils.stringToMD5(admin.getPassword()).equals(result.getPassword())) {
            Logit.d(TAG, "管理员登录失败, 用户名或密码错误");
            return false;
        }
        Logit.d(TAG, "管理员登录成功");
        return true;
    }

    @Override
    public List<Admin> getAllAdmins() {
        return mAdminDao.getAllAdmins();
    }
}
