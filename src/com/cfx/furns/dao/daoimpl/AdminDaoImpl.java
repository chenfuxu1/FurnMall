package com.cfx.furns.dao.daoimpl;

import com.cfx.furns.dao.BasicDao;
import com.cfx.furns.dao.IAdminDao;
import com.cfx.furns.entity.Admin;
import com.cfx.furns.utils.MD5Utils;

import java.util.List;

import static com.cfx.furns.utils.Constants.TABLE_ADMIN;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/26 21:38
 *
 * 管理员 dao 实现类
 **/
public class AdminDaoImpl extends BasicDao<Admin> implements IAdminDao {
    private static final String TAG = "AdminDaoImpl";

    @Override
    public Admin queryAdminByName(String userName) {
        if (userName == null || "".equals(userName)) {
            return null;
        }
        String sql = "select * from " + TABLE_ADMIN + " where username = ?";
        return querySingle(sql, Admin.class, userName);
    }

    @Override
    public int saveAdmin(Admin admin) {
        if (admin == null) {
            return 0;
        }
        String sql = "insert into " + TABLE_ADMIN + " values(null, ?, ?, ?)";
        return update(sql, admin.getUserName(), MD5Utils.stringToMD5(admin.getPassword()), admin.getEmail());
    }

    @Override
    public List<Admin> getAllAdmins() {
        String sql = "select * from " + TABLE_ADMIN;
        return queryMulti(sql, Admin.class, null);
    }
}
