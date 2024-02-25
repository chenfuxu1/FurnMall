package com.cfx.furns.test;

import com.cfx.furns.entity.Cart;
import com.cfx.furns.entity.CartItem;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/2/24 21:15
 **/
public class CartTest {
    private Cart mCart = new Cart();

    @Test
    public void testAddItem() {
        mCart.addCartItem(new CartItem(1, "沙发", new BigDecimal(10.11), 100));
        mCart.addCartItem(new CartItem(2, "椅子", new BigDecimal(5.11), 3));
        System.out.println(mCart);
    }
}
