package com.cfx.furns.entity;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/2/26 23:53
 *
 * 订单表 order, 对应用户生成的某一个订单
 **/
public class Order {
    private String mOrderId; // 订单 id
    private Timestamp mOrderTime; // 下订单的时间
    private BigDecimal mTotalPrice; // 订单总价格
    private String mStatus; // 状态
    private String mMemberName; // 用户名

    public Order() {
    }

    public Order(String orderId, Timestamp orderTime, BigDecimal totalPrice, String status, String memberName) {
        mOrderId = orderId;
        mOrderTime = orderTime;
        mTotalPrice = totalPrice;
        if (mTotalPrice != null) {
            mTotalPrice = mTotalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        mStatus = status;
        mMemberName = memberName;
    }

    public String getOrderId() {
        return mOrderId;
    }

    public void setOrderId(String orderId) {
        mOrderId = orderId;
    }

    public Timestamp getOrderTime() {
        return mOrderTime;
    }

    public String getOrderTimeStr() {
        if (mOrderTime != null) {
            return mOrderTime.toLocaleString();
        }
        return "";
    }

    public void setOrderTime(Timestamp orderTime) {
        mOrderTime = orderTime;
    }

    public BigDecimal getTotalPrice() {
        return mTotalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        mTotalPrice = totalPrice;
        if (mTotalPrice != null) {
            mTotalPrice = mTotalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getMemberName() {
        return mMemberName;
    }

    public void setMemberName(String memberName) {
        mMemberName = memberName;
    }

    @Override
    public String toString() {
        return "Order{" +
                "mOrderId='" + mOrderId + '\'' +
                ", mOrderTime=" + mOrderTime +
                ", mTotalPrice=" + mTotalPrice +
                ", mStatus='" + mStatus + '\'' +
                ", mMemberName='" + mMemberName + '\'' +
                '}';
    }
}
