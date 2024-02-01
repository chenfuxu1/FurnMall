package com.cfx.furns.service.serviceimpl;

import com.cfx.furns.dao.IFurnDao;
import com.cfx.furns.dao.daoimpl.FurnDaoImpl;
import com.cfx.furns.entity.Furn;
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
}
