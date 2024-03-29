package com.cfx.furns.dao;

import com.cfx.furns.entity.Furn;

import java.util.List;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/27 12:04
 *
 * 家居 dao 接口
 **/
public interface IFurnDao {
    // 获取所有的家居
    public List<Furn> getAllFurns();

    // 添加一条家居数据到数据库
    public boolean addFurn(Furn furn);

    // 根据 id 删除某条家居信息
    public boolean deleteFurnById(int furnId);

    // 根据 id 查询某条家居信息
    public Furn queryFurnById(int furnId);

    // 更新某条家居信息
    public boolean updateFurn(Furn furn);

    // 查询总数据个数
    public int queryTotalDataSize();

    // 获取一页展示的家居数据
    public List<Furn> getPageFurns(int beginNum, int pageSize);

    // 根据搜搜关键词获取一页展示的家居数据
    public List<Furn> getPageFurnsByKeyword(String keyword, int beginNum, int pageSize);

    // 搜索关键词的查询数据个数
    public int queryKeywordDataSize(String keyword);
}
