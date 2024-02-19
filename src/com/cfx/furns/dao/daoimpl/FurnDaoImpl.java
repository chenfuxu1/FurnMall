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

    // 查询数据库中总数据个数
    @Override
    public int queryTotalDataSize() {
        String sql = "select count(*) from " + TABLE_FURN;
        // return (Integer) queryScalar(sql); 直接这样转换会报异常，因为查出来的是 Long 型，不能直接转化
        return ((Number) queryScalar(sql)).intValue();
    }

    /**
     * 获取一页展示个家居数据集合
     *
     * @param beginNum 开始从哪条数据开始取数，数据库第一条从 0 开始计数
     * @param pageSize 共取多少条数据
     * @return
     */
    @Override
    public List<Furn> getPageFurns(int beginNum, int pageSize) {
        if (beginNum < 0) {
            Logit.d(TAG, "beginNum is less than zero");
            return null;
        }
        if (pageSize <= 0) {
            Logit.d(TAG, "pageSize is error");
            return null;
        }
        String sql = "select id, `name`, maker, price, sales, stock, img_url imgUrl from " + TABLE_FURN + " limit ?, ?;";
        return queryMulti(sql, Furn.class, beginNum, pageSize);
    }

    /**
     * 根据搜索关键词获取一页展示个家居数据集合
     *
     * @param keyword  搜索关键词
     * @param beginNum 开始从哪条数据开始取数，数据库第一条从 0 开始计数
     * @param pageSize 共取多少条数据
     * @return
     */
    @Override
    public List<Furn> getPageFurnsByKeyword(String keyword, int beginNum, int pageSize) {
        if (keyword == null || "".equals(keyword)) {
            return getPageFurns(beginNum, pageSize);
        }
        if (beginNum < 0) {
            Logit.d(TAG, "beginNum is less than zero");
            return null;
        }
        if (pageSize <= 0) {
            Logit.d(TAG, "pageSize is error");
            return null;
        }
        String sql = "SELECT id, `name`, maker, price, sales, stock, img_url imgUrl FROM furn WHERE `name` LIKE ? limit ?, ?;";
        keyword = "%" + keyword + "%";
        return queryMulti(sql, Furn.class, keyword, beginNum, pageSize);
    }

    // 搜索关键词的查询数据个数
    @Override
    public int queryKeywordDataSize(String keyword) {
        String sql = "select count(*) from " + TABLE_FURN + " where name like ?";
        keyword = "%" + keyword + "%";
        // return (Integer) queryScalar(sql); 直接这样转换会报异常，因为查出来的是 Long 型，不能直接转化
        return ((Number) queryScalar(sql, keyword)).intValue();
    }
}
