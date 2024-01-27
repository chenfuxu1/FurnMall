package com.cfx.furns.test;

import com.cfx.furns.entity.Furn;
import com.cfx.furns.service.IFurnService;
import com.cfx.furns.service.serviceimpl.FurnServiceImpl;
import com.cfx.furns.utils.Logit;
import org.junit.Test;

import java.util.List;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/27 12:09
 **/
public class TestFurnService {
    private static final String TAG = "TestFurnService";
    private IFurnService mFurnService = new FurnServiceImpl();

    @Test
    public void getAllFurns() {
        List<Furn> allFurns = mFurnService.getAllFurns();
        for (Furn furn : allFurns) {
            Logit.d(TAG, furn.toString());
        }
    }
}
