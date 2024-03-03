package com.cfx.furns.service.serviceimpl;

import com.cfx.furns.dao.IFurnDao;
import com.cfx.furns.dao.IOrderDao;
import com.cfx.furns.dao.IOrderItemDao;
import com.cfx.furns.dao.daoimpl.FurnDaoImpl;
import com.cfx.furns.dao.daoimpl.OrderDaoImpl;
import com.cfx.furns.dao.daoimpl.OrderItemDaoImpl;
import com.cfx.furns.entity.*;
import com.cfx.furns.service.IOrderService;
import com.cfx.furns.utils.Logit;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static com.cfx.furns.utils.Constants.STATUS_NO_SEND;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/2/29 23:27
 **/
public class OrderServiceImpl implements IOrderService {
    private static final String TAG = "OrderServiceImpl";
    private IOrderDao mOrderDao = new OrderDaoImpl();
    private IOrderItemDao mOrderItemDao = new OrderItemDaoImpl();
    private IFurnDao mFurnDao = new FurnDaoImpl();

    @Override
    public List<Order> queryOrderByName(String name) {
        if (name == null || "".equals(name)) {
            Logit.d(TAG, "name is null");
            return null;
        }
        return mOrderDao.queryOrderByName(name);
    }

    @Override
    public Order queryOrderByID(String orderId) {
        if (orderId == null || "".equals(orderId)) {
            Logit.d(TAG, "orderId is null");
            return null;
        }
        return mOrderDao.queryOrderByID(orderId);
    }

    @Override
    public List<OrderItem> queryOrderItemsByID(String orderId) {
        if (orderId == null || "".equals(orderId)) {
            Logit.d(TAG, "orderId is null");
            return null;
        }
        return mOrderItemDao.queryOrderItemById(orderId);
    }

    /**
     * 将 cart 购物车的数据以 order 和 orderItem 的形式保存到数据库
     * @param cart
     * @param memberName
     * @return
     */
    @Override
    public String saveOrder(Cart cart, String memberName) {
        if (cart == null) {
            Logit.d(TAG, "cart is null");
            return "";
        }
        if (memberName == null || "".equals(memberName)) {
            Logit.d(TAG, "memberName is null");
            return "";
        }
        /**
         * 生成订单会涉及多张表
         * 后面使用 ThreadLocal + 事务 + 过滤器来处理
         */
        // 1.通过 cart 对象，构建对应的 order 对象
        String orderId = String.valueOf(System.currentTimeMillis()); // 先生成订单号，需要保证唯一
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Order order = new Order(orderId, timestamp, cart.getTotalPrice(), STATUS_NO_SEND, memberName);
        // 2.保存 order 到数据库
        mOrderDao.saveOrder(order);

        // 3.遍历出 CartItem 对象，分别存到 order_item 表
        for (Map.Entry<Integer, CartItem> itemEntry : cart.getCartItems().entrySet()) {
            Integer furnId = itemEntry.getKey();
            CartItem cartItem = itemEntry.getValue();
            OrderItem orderItem = new OrderItem(furnId, cartItem.getName(), cartItem.getPrice(), cartItem.getCount(), orderId);
            mOrderItemDao.saveOrderItem(orderItem);
            // 4.更新 furn 家居表中的 sales 销量，以及 stock 库存量
            Furn furn = mFurnDao.queryFurnById(furnId);
            furn.setSales(furn.getSales() + cartItem.getCount());
            furn.setStock(furn.getStock() - cartItem.getCount());
            mFurnDao.updateFurn(furn);
        }
        // 5.清空购物车
        cart.clearCart();
        return orderId;
    }
}
