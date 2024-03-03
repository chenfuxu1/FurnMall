package com.cfx.furns.service;

import com.cfx.furns.entity.Cart;
import com.cfx.furns.entity.Order;
import com.cfx.furns.entity.OrderItem;

import java.util.List;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/2/29 23:22
 **/
public interface IOrderService {
    // 根据用户名，查询订单
    public List<Order> queryOrderByName(String name);

    // 根据订单号返回某个订单
    public Order queryOrderByID(String orderId);

    // 根据订单号返回订单的具体家居项集合
    public List<OrderItem> queryOrderItemsByID(String orderId);

    /**
     * 生成订单, 返回生成的订单号
     * 根据购物车对象来生成订单
     * 因为订单是和会员关联的，所以还需要传入用户名
     */
    public String saveOrder(Cart cart, String memberName);
}
