package com.cfx.furns.service;

import com.cfx.furns.entity.Furn;

import java.util.List;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/27 12:12
 *
 * 家居 service 接口
 **/
public interface IFurnService {
    // 获取所有的家居
    public List<Furn> getAllFurns();
}
