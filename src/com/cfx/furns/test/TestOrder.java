package com.cfx.furns.test;

import com.cfx.furns.dao.IOrderDao;
import com.cfx.furns.dao.IOrderItemDao;
import com.cfx.furns.dao.daoimpl.OrderDaoImpl;
import com.cfx.furns.dao.daoimpl.OrderItemDaoImpl;
import com.cfx.furns.entity.Cart;
import com.cfx.furns.entity.CartItem;
import com.cfx.furns.entity.Order;
import com.cfx.furns.entity.OrderItem;
import com.cfx.furns.service.IOrderService;
import com.cfx.furns.service.serviceimpl.OrderServiceImpl;
import com.cfx.furns.utils.Logit;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/2/27 0:11
 **/
public class TestOrder {
    private IOrderItemDao mOrderItemDao = new OrderItemDaoImpl();
    private IOrderDao mOrderDao = new OrderDaoImpl();
    private IOrderService mOrderService = new OrderServiceImpl();
    private Cart mCart = new Cart();

    @Test
    public void testOrderItem() {
        List<OrderItem> orderItems = mOrderItemDao.queryOrderItemById("777");
        Logit.d("TAG", orderItems.toString());
    }

    @Test
    public void testSaveOrderItem() {
        OrderItem orderItem = new OrderItem(5, "小傻子", new BigDecimal(55.66), 11, "777");
        int i = mOrderItemDao.saveOrderItem(orderItem);
        System.out.println(i);
    }

    @Test
    public void testOrder() {
        List<Order> orders = mOrderDao.queryOrderByName("cfx123");
        Logit.d("TAG", orders.toString());
    }

    @Test
    public void testSaveOrder() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        Logit.d("TAG", "cfx ts " + ts.toLocaleString());
        Order order = new Order("1234", ts, new BigDecimal(3213), "未发货", "cfx123");
        mOrderDao.saveOrder(order);
    }


    @Test
    public void testOrderService() {
        List<Order> orders = mOrderService.queryOrderByName("cfx123");
        Logit.d("TAG", orders.toString());
    }

    @Test
    public void testSaveOrderService() {
        // 验证是否三张表变化正确 furn、order、order_item
        mCart.addCartItem(new CartItem(6, "北欧风格小桌子", new BigDecimal(180), 1));
        mCart.addCartItem(new CartItem(8, "典雅风格小台灯", new BigDecimal(180), 2));
        mOrderService.saveOrder(mCart, "222");
    }

}
