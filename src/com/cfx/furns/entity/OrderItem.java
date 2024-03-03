package com.cfx.furns.entity;

import java.math.BigDecimal;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/2/26 23:49
 *
 * 订单中具体某一项家居，对应 order_item 表
 **/
public class OrderItem {
    private Integer mId; // 家居 id
    private String mName; // 家居名
    private BigDecimal mPrice; // 该项家居价格
    private Integer mCount; // 该项家居的数量
    private BigDecimal mTotalPrice; // 该项家居总价格
    private String mOrderId; // 订单 id

    public OrderItem() {
    }

    public OrderItem(Integer id, String name, BigDecimal price, Integer count, String orderId) {
        mId = id;
        mName = name;
        mPrice = price;
        mCount = count;
        mOrderId = orderId;
    }

    public OrderItem(Integer id, String name, BigDecimal price, Integer count, BigDecimal totalPrice, String orderId) {
        mId = id;
        mName = name;
        mPrice = price;
        mCount = count;
        mTotalPrice = totalPrice;
        mOrderId = orderId;
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
        if (mPrice != null) {
            mPrice = mPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
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
        if (mPrice != null && mCount != null) {
            mTotalPrice = BigDecimal.valueOf(mCount).multiply(mPrice);
        } else {
            mTotalPrice = new BigDecimal(0);
        }
        mTotalPrice = mTotalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
        return mTotalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        mTotalPrice = totalPrice;
    }

    public String getOrderId() {
        return mOrderId;
    }

    public void setOrderId(String orderId) {
        mOrderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mPrice=" + mPrice +
                ", mCount=" + mCount +
                ", mTotalPrice=" + mTotalPrice +
                ", mOrderId='" + mOrderId + '\'' +
                '}';
    }
}
