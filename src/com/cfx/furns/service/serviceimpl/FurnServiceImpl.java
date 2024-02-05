package com.cfx.furns.service.serviceimpl;

import com.cfx.furns.dao.IFurnDao;
import com.cfx.furns.dao.daoimpl.FurnDaoImpl;
import com.cfx.furns.entity.Furn;
import com.cfx.furns.entity.Page;
import com.cfx.furns.service.IFurnService;
import com.cfx.furns.utils.Logit;

import java.util.List;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/27 12:13
 *
 * 家居 service 接口实现类
 **/
public class FurnServiceImpl implements IFurnService {
    private static final String TAG = "FurnServiceImpl";
    private IFurnDao mFurnDao = new FurnDaoImpl();

    @Override
    public List<Furn> getAllFurns() {
        return mFurnDao.getAllFurns();
    }

    @Override
    public boolean addFurn(Furn furn) {
        if (furn == null) {
            Logit.d(TAG, "添加的 furn 为空");
            return false;
        }
        return mFurnDao.addFurn(furn);
    }

    @Override
    public boolean deleteFurnById(int furnId) {
        if (furnId < 0) {
            Logit.d(TAG, "输入 id 有误");
            return false;
        }
        return mFurnDao.deleteFurnById(furnId);
    }

    @Override
    public Furn queryFurnById(int furnId) {
        if (furnId < 0) {
            Logit.d(TAG, "输入 id 有误");
            return null;
        }
        return mFurnDao.queryFurnById(furnId);
    }

    @Override
    public boolean updateFurn(Furn furn) {
        if (furn == null) {
            Logit.d(TAG, "输入家居为空");
            return false;
        }
        if (furn.getId() == null) {
            Logit.d(TAG, "输入家居 id 为空");
            return false;
        }
        return mFurnDao.updateFurn(furn);
    }

    @Override
    public Page<Furn> page(int pageNo, int pageSize) {
        if (pageNo < 0) {
            Logit.d(TAG, "pageNo is less than zero");
            return null;
        }
        if (pageSize <= 0) {
            Logit.d(TAG, "pageSize is error");
            return null;
        }
        Page<Furn> page = new Page<>();
        // 1.设置当前显示页数
        page.setCurrentPageNo(pageNo);
        // 2.设置每页展示多少条数据
        page.setPageSize(pageSize);
        int totalDataSize = mFurnDao.queryTotalDataSize();
        // 3.设置总数据数
        page.setTotalDataCount(totalDataSize);
        double totalPageCount = totalDataSize / (double) pageSize;
        totalPageCount = Math.ceil(totalPageCount);
        // 4.设置总页数
        page.setTotalPageCount((int) totalPageCount);
        // 如果当前页数大于总页数，设置当前页数为总页数
        if (pageNo > totalPageCount) {
            pageNo = (int) totalPageCount;
            page.setCurrentPageNo(pageNo);
        }
        int beginNum = (pageNo - 1) * pageSize; // 计算出当前页开始的数据编号
        List<Furn> pageFurns = mFurnDao.getPageFurns(beginNum, pageSize);
        // 5.设置当前页展示的数据集合
        page.setItemList(pageFurns);
        // 6.设置分页导航的 url todo
        return page;
    }
}
