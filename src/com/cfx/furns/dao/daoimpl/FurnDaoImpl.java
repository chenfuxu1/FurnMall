package com.cfx.furns.dao.daoimpl;

import com.cfx.furns.dao.BasicDao;
import com.cfx.furns.dao.IFurnDao;
import com.cfx.furns.entity.Furn;

import java.util.List;

import static com.cfx.furns.utils.Constants.TABLE_FURN;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/27 12:05
 * <p>
 * 家居 dao 接口实现类
 **/
public class FurnDaoImpl extends BasicDao<Furn> implements IFurnDao {
    @Override
    public List<Furn> getAllFurns() {
        String sql = "select id, `name`, maker, price, sales, stock, img_url imgUrl from " + TABLE_FURN + ";";
        List<Furn> furnList = queryMulti(sql, Furn.class);
        return furnList;
    }
}
