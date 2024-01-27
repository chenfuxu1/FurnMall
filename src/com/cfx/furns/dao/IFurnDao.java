package com.cfx.furns.dao;

import com.cfx.furns.entity.Furn;

import java.util.List;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/27 12:04
 *
 * 家居 dao 接口
 **/
public interface IFurnDao {
    // 获取所有的家居
    public List<Furn> getAllFurns();
}
