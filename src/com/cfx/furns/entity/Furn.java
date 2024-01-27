package com.cfx.furns.entity;

import java.math.BigDecimal;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/27 11:55
 *
 * 家居实体类
 **/
public class Furn {
    // 数据库为 int 类型，这里定义为 Integer 类型，可以传 null，不会异常
    private Integer mId; // id
    private String mName; // 家居名
    private String mMaker; // 制造商
    private BigDecimal mPrice; // 价格
    private Integer mSales; // 销量
    private Integer mStock; // 库存量
    private String mImgUrl; // 家居图片 url

    public Furn() {
    }

    public Furn(Integer id, String name, String maker, BigDecimal price, Integer sales, Integer stock, String imgUrl) {
        mId = id;
        mName = name;
        mMaker = maker;
        mPrice = price;
        mSales = sales;
        mStock = stock;
        mImgUrl = imgUrl;
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

    public String getMaker() {
        return mMaker;
    }

    public void setMaker(String maker) {
        mMaker = maker;
    }

    public BigDecimal getPrice() {
        return mPrice;
    }

    public void setPrice(BigDecimal price) {
        mPrice = price;
    }

    public Integer getSales() {
        return mSales;
    }

    public void setSales(Integer sales) {
        mSales = sales;
    }

    public Integer getStock() {
        return mStock;
    }

    public void setStock(Integer stock) {
        mStock = stock;
    }

    public String getImgUrl() {
        return mImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        mImgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "Furn{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mMaker='" + mMaker + '\'' +
                ", mPrice=" + mPrice +
                ", mSales=" + mSales +
                ", mStock=" + mStock +
                ", mImgUrl='" + mImgUrl + '\'' +
                '}';
    }
}
