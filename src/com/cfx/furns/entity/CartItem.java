package com.cfx.furns.entity;

import java.math.BigDecimal;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/2/24 20:59
 *
 * 表示购物车中具体的家居实体类
 **/
public class CartItem {
    private Integer mId; // 家居 id
    private String mName; // 家居名
    private BigDecimal mPrice; // 家居单价
    private Integer mCount; // 家居数量
    private BigDecimal mTotalPrice; // 总价格

    public CartItem() {
    }

    public CartItem(Integer id, String name, BigDecimal price, Integer count, BigDecimal totalPrice) {
        mId = id;
        mName = name;
        mPrice = price.setScale(2, BigDecimal.ROUND_HALF_UP);
        mCount = count;
        mTotalPrice = totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public BigDecimal getPrice() {
        return mPrice;
    }

    public void setPrice(BigDecimal price) {
        mPrice = price;
    }

    public Integer getCount() {
        return mCount;
    }

    public void setCount(Integer count) {
        mCount = count;
    }

    public BigDecimal getTotalPrice() {
        return mTotalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        mTotalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mPrice=" + mPrice +
                ", mCount=" + mCount +
                ", mTotalPrice=" + mTotalPrice +
                '}';
    }
}
