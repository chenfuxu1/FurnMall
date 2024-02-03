package com.cfx.furns.test;

import com.cfx.furns.dao.IFurnDao;
import com.cfx.furns.dao.daoimpl.FurnDaoImpl;
import com.cfx.furns.entity.Furn;
import com.cfx.furns.utils.Logit;
import org.junit.Test;

import java.util.List;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/27 12:09
 **/
public class TestFurnDao {
    private static final String TAG = "TestFurnDao";

    @Test
    public void getAllFurns() {
        IFurnDao furnDao = new FurnDaoImpl();
        List<Furn> allFurns = furnDao.getAllFurns();
        for (Furn furn : allFurns) {
            Logit.d(TAG, furn.toString());
        }
    }

    @Test
    public void getTotalCount() {
        IFurnDao furnDao = new FurnDaoImpl();
        int i = furnDao.queryTotalDataSize();
        System.out.println(i);
    }

    @Test
    public void getPageFurns() {
        IFurnDao furnDao = new FurnDaoImpl();
        List<Furn> pageFurns = furnDao.getPageFurns(3, 3);
        Logit.d(TAG, "pageFurns: " + pageFurns);
    }
}
