package com.cfx.furns.service;

import com.cfx.furns.entity.Furn;
import com.cfx.furns.entity.Page;

import java.util.List;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/27 12:12
 *
 * 家居 service 接口
 **/
public interface IFurnService {
    // 获取所有的家居
    public List<Furn> getAllFurns();

    // 添加一条家居
    public boolean addFurn(Furn furn);

    // 根据 id 删除某条家居信息
    public boolean deleteFurnById(int furnId);

    // 根据 id 查询某条家居信息
    public Furn queryFurnById(int furnId);

    // 更新某条家居信息
    public boolean updateFurn(Furn furn);

    /**
     * 根据传入的 pageNo 和 pageSize 返回对应的 page 对象
     * @param pageNo 表示当前是第几页
     * @param pageSize
     * @return
     */
    public Page<Furn> page(int pageNo, int pageSize);

    /**
     * 根据传入的 pageNo 和 pageSize 以及搜索关键词返回对应的 page 对象
     * @param keyword 搜索关键词
     * @param pageNo 表示当前是第几页
     * @param pageSize 每页展示的数量
     * @return 返回 page 对象
     */
    public Page<Furn> pageByKeyword(String keyword, int pageNo, int pageSize);
}
