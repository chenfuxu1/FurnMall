package com.cfx.furns.dao.daoimpl;

import com.cfx.furns.dao.BasicDao;
import com.cfx.furns.dao.IOrderItemDao;
import com.cfx.furns.entity.OrderItem;
import com.cfx.furns.utils.Logit;

import java.util.List;

import static com.cfx.furns.utils.Constants.TABLE_ORDER_ITEM;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/2/27 0:02
 **/
public class OrderItemDaoImpl extends BasicDao<OrderItem> implements IOrderItemDao {
    private static final String TAG = "OrderItemImpl";

    // 根据订单 id，查询具体的 OrderItem 项
    @Override
    public List<OrderItem> queryOrderItemById(String orderId) {
        if (orderId == null || "".equals(orderId)) {
            return null;
        }
        String sql = "select id, name, price, count, total_price totalPrice, order_id orderId from " + TABLE_ORDER_ITEM + " where order_id = ?";
        return queryMulti(sql, OrderItem.class, orderId);
    }

    // 保存 OrderItem 到数据库
    @Override
    public int saveOrderItem(OrderItem orderItem) {
        if (orderItem == null) {
            Logit.d(TAG, "orderItem is null");
            return 0;
        }
        String sql = "insert into " + TABLE_ORDER_ITEM + " values(?, ?, ?, ?, ?, ?);";
        return update(sql, orderItem.getId(), orderItem.getName(), orderItem.getPrice(), orderItem.getCount(), orderItem.getTotalPrice(), orderItem.getOrderId());
    }
}
