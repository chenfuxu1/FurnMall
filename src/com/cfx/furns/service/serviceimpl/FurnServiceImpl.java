package com.cfx.furns.service.serviceimpl;

import com.cfx.furns.dao.IFurnDao;
import com.cfx.furns.dao.daoimpl.FurnDaoImpl;
import com.cfx.furns.entity.Furn;
import com.cfx.furns.service.IFurnService;

import java.util.List;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/27 12:13
 *
 * 家居 service 接口实现类
 **/
public class FurnServiceImpl implements IFurnService {
    private IFurnDao mFurnDao = new FurnDaoImpl();

    @Override
    public List<Furn> getAllFurns() {
        return mFurnDao.getAllFurns();
    }
}
