package com.cfx.furns.entity;

import com.cfx.furns.utils.Logit;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/2/24 21:04
 *
 * 表示购物车模型
 * 里面包含多个 CartItem
 **/
public class Cart {
    private static final String TAG = "Cart";
    // 存放多个 CartItem
    private HashMap<Integer, CartItem> mCartItems = new HashMap<>();
    // 购物车商品总个数
    private Integer mTotalCount = 0;
    // 购物车商品总价格
    private BigDecimal mTotalPrice;

    public Cart() {
    }

    /**
     * 添加 CartItem 到购物车中
     * @param cartItem
     */
    public void addCartItem(CartItem cartItem) {
        if (cartItem == null) {
            return;
        }
        CartItem item = mCartItems.get(cartItem.getId());
        if (item == null) {
            // 表明当前购物车中没有该 cartItem，直接添加即可
            mCartItems.put(cartItem.getId(), cartItem);
        } else {
            // 表明当前的购物车已经有过 cartItem，那么将其数量加 1，总价变化一下
            // item.setCount(item.getCount() + 1);
            // item.setTotalPrice(item.getTotalPrice().add(cartItem.getPrice()));
            // item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));

            item.updateCountAndTotalPrice(item.getCount() + 1);
        }
    }

    public Integer getTotalCount() {
        mTotalCount = 0;
        for (Map.Entry<Integer, CartItem> itemEntry : mCartItems.entrySet()) {
            CartItem value = itemEntry.getValue();
            mTotalCount += value.getCount();
        }
        return mTotalCount;
    }

    public BigDecimal getTotalPrice() {
        mTotalPrice = new BigDecimal(0);
        for (Map.Entry<Integer, CartItem> itemEntry : mCartItems.entrySet()) {
            CartItem value = itemEntry.getValue();
            mTotalPrice = mTotalPrice.add(value.getTotalPrice());
        }
        return mTotalPrice;
    }

    public HashMap<Integer, CartItem> getCartItems() {
        return mCartItems;
    }

    public void deleteCartItem(Integer furnId) {
        if (furnId == null || furnId <= 0) {
            Logit.d(TAG, "furnId is less than zero");
            return;
        }
        mCartItems.remove(furnId);
    }

    public void clearCart() {
        mCartItems.clear();
    }

    @Override
    public String toString() {
        return "Cart{" +
                "mCartItems=" + mCartItems +
                '}';
    }
}
