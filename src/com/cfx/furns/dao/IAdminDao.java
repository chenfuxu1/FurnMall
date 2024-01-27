package com.cfx.furns.dao;

import com.cfx.furns.entity.Admin;

import java.util.List;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/20 13:16
 *
 * 管理员 AdminDao 接口
 **/
public interface IAdminDao {
    // 根据用户名返回对应的 Admin
    public Admin queryAdminByName(String userName);

    // 保存 Admin 对象到数据库中
    public int saveAdmin(Admin admin);

    // 查询 Admin 表中所有的数据
    public List<Admin> getAllAdmins();
}
