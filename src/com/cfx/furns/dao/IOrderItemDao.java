package com.cfx.furns.dao;

import com.cfx.furns.entity.OrderItem;

import java.util.List;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/2/26 23:57
 **/
public interface IOrderItemDao {
    // 根据订单 id，查询具体的 OrderItem 项
    public List<OrderItem> queryOrderItemById(String orderId);

    // 保存 OrderItem 到数据库
    public int saveOrderItem(OrderItem orderItem);
}
