package com.cfx.furns.web.servlet;

import com.cfx.furns.entity.Cart;
import com.cfx.furns.entity.CartItem;
import com.cfx.furns.entity.Furn;
import com.cfx.furns.service.IFurnService;
import com.cfx.furns.service.serviceimpl.FurnServiceImpl;
import com.cfx.furns.utils.Logit;
import com.cfx.furns.utils.StringToNumUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.cfx.furns.utils.Constants.CART;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/2/24 21:30
 **/
public class CartServlet extends BaseServlet {
    private static final String TAG = "CartServlet";
    private IFurnService mFurnService = new FurnServiceImpl();

    /**
     * 添加家居信息到购物车
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void addCartItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logit.d(TAG, "addCartItem...");
        String id = req.getParameter("id");
        Logit.d(TAG, "id: " + id);
        int idInt = StringToNumUtils.parseInt(id, 0);
        Furn furn = mFurnService.queryFurnById(idInt);
        String referer = req.getHeader("Referer");
        if (furn == null) {
            // 家居不存在
            // TODO: 2024/2/25
            return;
        }
        if (furn.getStock() <= 0) {
            // 表明没有库存了
            resp.sendRedirect(referer); // 回到原界面，这里直接重定向到 referer 即可
        }
        // 家居存在
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute(CART);
        if (cart == null) {
            // 表明 session 中还没有 Cart 对象，那么创建购物车对象
            cart = new Cart();
            // 将购物车缓存到 session 中
            session.setAttribute(CART, cart);
        }
        // 表明 session 中有购物车对象
        CartItem cartItem = new CartItem(idInt, furn.getName(), furn.getPrice(), 1);
        // 将家居对象添加到购物车
        cart.addCartItem(cartItem);
        Logit.d(TAG, "cart: " + cart);
        resp.sendRedirect(referer); // 添加后，需要回到原界面，这里直接重定向到 referer 即可
    }

    /**
     * 修改购物车中商品的数量
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void updateCartCount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logit.d(TAG, "updateCartCount...");
        String id = req.getParameter("id");
        String count = req.getParameter("count");
        int idInt = StringToNumUtils.parseInt(id, 0);
        int countInt = StringToNumUtils.parseInt(count, 1);
        Logit.d(TAG, "id: " + id + " countInt: " + countInt);
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute(CART);
        if (cart != null) {
            CartItem cartItem = cart.getCartItems().get(idInt);
            if (cartItem == null) {
                resp.sendRedirect(req.getContextPath() + "index.jsp");
                return;
            }
            cartItem.updateCountAndTotalPrice(countInt);
        }
        String referer = req.getHeader("Referer");
        resp.sendRedirect(referer); // 修改后，需要回到原界面，这里直接重定向到 referer 即可
    }

    /**
     * 删除购物车中的某个家居
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void deleteCartItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logit.d(TAG, "deleteCartItem...");
        String id = req.getParameter("id");
        int idInt = StringToNumUtils.parseInt(id, 0);
        Logit.d(TAG, "id: " + id);
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute(CART);
        if (cart != null) {
            cart.deleteCartItem(idInt);
        }
        String referer = req.getHeader("Referer");
        resp.sendRedirect(referer); // 删除后，需要回到原界面，这里直接重定向到 referer 即可
    }

    /**
     * 清空购物车
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void clearCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logit.d(TAG, "clearCart...");
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute(CART);
        if (cart != null) {
            cart.clearCart();
        }
        session.removeAttribute(CART);
        String referer = req.getHeader("Referer");
        resp.sendRedirect(referer); // 删除后，需要回到原界面，这里直接重定向到 referer 即可
    }
}
