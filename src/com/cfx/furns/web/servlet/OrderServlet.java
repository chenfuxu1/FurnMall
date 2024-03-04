package com.cfx.furns.web.servlet;

import com.cfx.furns.entity.*;
import com.cfx.furns.service.IOrderService;
import com.cfx.furns.service.serviceimpl.OrderServiceImpl;
import com.cfx.furns.utils.Constants;
import com.cfx.furns.utils.Logit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.cfx.furns.utils.Constants.*;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/2/29 23:42
 *
 * 用来生成订单的 Servlet
 **/
public class OrderServlet extends BaseServlet {
    private static final String TAG = "OrderServlet";
    private static final String DISPATCHER_LOGIN = "/views/member/login.jsp";
    private static final String DISPATCHER_CART = "/views/cart/cart.jsp";
    private static final String DISPATCHER_ORDER = "/views/order/order.jsp";
    private static final String DISPATCHER_ORDER_CHECKOUT = "/views/order/checkout.jsp";
    private static final String DISPATCHER_ORDER_ITEMS = "/views/order/order_detail.jsp";
    private IOrderService mOrderService = new OrderServiceImpl();

    /**
     * 该方法用来创建订单
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logit.d(TAG, "createOrder...");
        // 1.获取 session 中的购物车对象
        Object cartObj = req.getSession().getAttribute(Constants.CART);
        if (!(cartObj instanceof Cart)) {
            // 没有购物车信息，直接返回项目初始页面
            Logit.d(TAG, "cartObj is null");
            resp.sendRedirect(req.getContextPath());
            return;
        }
        Cart cart = (Cart) cartObj;
        HashMap<Integer, CartItem> cartItems = cart.getCartItems();
        // 2.判断购物车是否为空
        if (cartItems.size() <= 0) {
            // 没有购物车信息为空，直接返回项目初始页面
            Logit.d(TAG, "cartItems is empty");
            resp.sendRedirect(req.getContextPath());
            return;
        }
        Logit.d(TAG, "cartItems.size(): " + cartItems.size());
        // 3.判断 session 中有没有登录的用户对象
        Object memberObj = req.getSession().getAttribute(Constants.MEMBER);
        if (!(memberObj instanceof Member)) {
            // 用户没有进行登录
            Logit.d(TAG, "memberObj is null");
            // 跳转到登录界面，让用户登录
            resp.sendRedirect(req.getContextPath() + DISPATCHER_LOGIN);
            return;
        }
        // 4.用户已经登录，生成订单
        Member member = (Member) memberObj;
        // 5.生成订单
        String orderId = mOrderService.saveOrder(cart, member.getUserName());
        req.getSession().setAttribute(ORDER_ID, orderId);
        // 重定向到 checkout.jsp
        resp.sendRedirect(req.getContextPath() + DISPATCHER_ORDER_CHECKOUT);
    }

    /**
     * 展示该用户的订单
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void showOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logit.d(TAG, "showOrder...");
        Object memberObj = req.getSession().getAttribute(Constants.MEMBER);
        if (!(memberObj instanceof Member)) {
            // 用户没有进行登录
            Logit.d(TAG, "memberObj is null");
            // 跳转到登录界面，让用户登录
            resp.sendRedirect(req.getContextPath() + DISPATCHER_LOGIN);
            return;
        }
        Member member = (Member) memberObj;
        List<Order> orders = mOrderService.queryOrderByName(member.getUserName());
        req.setAttribute(ORDERS, orders);
        // 转发到订单页面
        req.getRequestDispatcher(DISPATCHER_ORDER).forward(req, resp);
    }

    /**
     * 展示用户订单详情
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void showOrderDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logit.d(TAG, "showOrderDetails...");
        String orderId = req.getParameter(ORDER_ID);
        Logit.d(TAG, "orderId: " + orderId);
        List<OrderItem> orderItems = mOrderService.queryOrderItemsByID(orderId);
        Order order = new Order();
        order.setOrderItemList(orderItems);
        order.setOrderId(orderId);
        req.setAttribute(ORDER_ITEMS, order);
        // 转发到订单具体项页面
        req.getRequestDispatcher(DISPATCHER_ORDER_ITEMS).forward(req, resp);
    }
}
