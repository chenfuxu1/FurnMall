package com.cfx.furns.dao.daoimpl;

import com.cfx.furns.dao.BasicDao;
import com.cfx.furns.dao.IOrderDao;
import com.cfx.furns.entity.Order;
import com.cfx.furns.utils.Logit;

import java.util.List;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/2/27 23:23
 **/
public class OrderDaoImpl extends BasicDao<Order> implements IOrderDao {
    private static final String TAG = "OrderDaoImpl";

    @Override
    public List<Order> queryOrderByName(String name) {
        if (name == null || "".equals(name)) {
            Logit.d(TAG, "name is null");
        }
        String sql = "select order_id orderId, order_time orderTime, total_price totalPrice, `status`, member_name memberName from `order` where member_name = ? order by order_id desc;";
        return queryMulti(sql, Order.class, name);
    }

    @Override
    public Order queryOrderByID(String orderId) {
        if (orderId == null || "".equals(orderId)) {
            Logit.d(TAG, "orderId is null");
        }
        String sql = "select order_id orderId, order_time orderTime, total_price totalPrice, `status`, member_name memberName from `order` where order_id = ? order by order_id desc;";
        return querySingle(sql, Order.class, orderId);
    }

    @Override
    public int saveOrder(Order order) {
        if (order == null) {
            Logit.d(TAG, "order is null");
        }
        String sql = "insert into `order` values(?, ?, ?, ?, ?);";
        return update(sql, order.getOrderId(), order.getOrderTime().toLocaleString(), order.getTotalPrice(), order.getStatus(), order.getMemberName());
    }
}
