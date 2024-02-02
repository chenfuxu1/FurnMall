package com.cfx.furns.dao.daoimpl;

import com.cfx.furns.dao.BasicDao;
import com.cfx.furns.dao.IFurnDao;
import com.cfx.furns.entity.Furn;
import com.cfx.furns.utils.Logit;

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
    private static final String TAG = "FurnDaoImpl";

    @Override
    public List<Furn> getAllFurns() {
        String sql = "select id, `name`, maker, price, sales, stock, img_url imgUrl from " + TABLE_FURN + ";";
        List<Furn> furnList = queryMulti(sql, Furn.class);
        return furnList;
    }

    // 添加家居到数据库
    @Override
    public boolean addFurn(Furn furn) {
        if (furn == null) {
            Logit.d(TAG, "添加的 furn 为空");
            return false;
        }
        String sql = "insert into " + TABLE_FURN + " values(null, ?, ?, ?, ?, ?, ?);";
        int update =
                update(sql, furn.getName(), furn.getMaker(), furn.getPrice(), furn.getSales(), furn.getStock(), furn.getImgUrl());
        return update > 0;
    }

    // 根据 id 删除某条家居信息
    @Override
    public boolean deleteFurnById(int furnId) {
        if (furnId < 0) {
            Logit.d(TAG, "输入 id 有误");
            return false;
        }
        String sql = "delete from " + TABLE_FURN + " where id = ?";
        int update = update(sql, furnId);
        return update > 0;
    }

    @Override
    public Furn queryFurnById(int furnId) {
        if (furnId < 0) {
            Logit.d(TAG, "输入 id 有误");
            return null;
        }
        String sql = "select id, `name`, maker, price, sales, stock, img_url imgUrl from " + TABLE_FURN + " where id = ?";
        return querySingle(sql, Furn.class, furnId);
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
        String sql = "update " + TABLE_FURN + " set name = ?, maker = ?, price = ?, sales = ?, stock = ?, img_url = ? where id = ?";
        int update = update(sql, furn.getName(), furn.getMaker(), furn.getPrice(), furn.getSales(), furn.getStock(), furn.getImgUrl(), furn.getId());
        return update > 0;
    }
}
