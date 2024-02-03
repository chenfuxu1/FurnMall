package com.cfx.furns.entity;

import java.util.ArrayList;
import java.util.List;

import static com.cfx.furns.utils.Constants.PAGE_SIZE;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/2/3 17:15
 *
 * 分页模型，实体类
 * T 表示泛型，因为将来分页的数据是不确定的
 **/
public class Page<T> {
    // 当前显示的是第几页
    private Integer mCurrentPageNo;
    // 表示每页显示几条数据
    private Integer mPageSize = PAGE_SIZE;
    // 表示共有多少条记录
    private Integer mTotalDataCount;
    // 表示总页数，通过总数据数 mTotalDataCount 与 mPageSize 计算得到
    private Integer mTotalPageCount;
    // 表示当前页要显示的数据集合
    private List<T> mItemList = new ArrayList<>();
    // 作分页导航的 url
    private String mPageUrl;

    public Page() {
    }

    public Integer getCurrentPageNo() {
        return mCurrentPageNo;
    }

    public void setCurrentPageNo(Integer currentPageNo) {
        mCurrentPageNo = currentPageNo;
    }

    public Integer getPageSize() {
        return mPageSize;
    }

    public void setPageSize(Integer pageSize) {
        mPageSize = pageSize;
    }

    public Integer getTotalDataCount() {
        return mTotalDataCount;
    }

    public void setTotalDataCount(Integer totalDataCount) {
        mTotalDataCount = totalDataCount;
    }

    public Integer getTotalPageCount() {
        return mTotalPageCount;
    }

    public void setTotalPageCount(Integer totalPageCount) {
        mTotalPageCount = totalPageCount;
    }

    public List<T> getItemList() {
        return mItemList;
    }

    public void setItemList(List<T> itemList) {
        mItemList.clear();
        mItemList.addAll(itemList);
    }

    public String getPageUrl() {
        return mPageUrl;
    }

    public void setPageUrl(String pageUrl) {
        mPageUrl = pageUrl;
    }
}
