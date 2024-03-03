package com.cfx.furns.dao;

import com.cfx.furns.entity.Order;

import java.util.List;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/2/26 23:57
 **/
public interface IOrderDao {
    // 根据用户名，查询订单
    public List<Order> queryOrderByName(String name);

    // 根据订单号返回某个订单
    public Order queryOrderByID(String orderId);

    // 生成订单
    public int saveOrder(Order order);
}
