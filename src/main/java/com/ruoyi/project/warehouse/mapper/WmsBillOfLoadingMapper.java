package com.ruoyi.project.warehouse.mapper;


import com.ruoyi.project.warehouse.domain.WmsBillOfLoading;

import java.util.List;

/**
 * 发运单Mapper接口
 *
 * @author ruoyi
 * @date 2023-04-16
 */
public interface WmsBillOfLoadingMapper {
    /**
     * 查询发运单
     *
     * @param id 发运单主键
     * @return 发运单
     */
    public WmsBillOfLoading selectWmsBillOfLoadingById(String id);

    /**
     * 查询发运单列表
     *
     * @param wmsBillOfLoading 发运单
     * @return 发运单集合
     */
    public List<WmsBillOfLoading> selectWmsBillOfLoadingList(WmsBillOfLoading wmsBillOfLoading);

    /**
     * 新增发运单
     *
     * @param wmsBillOfLoading 发运单
     * @return 结果
     */
    public int insertWmsBillOfLoading(WmsBillOfLoading wmsBillOfLoading);

    /**
     * 修改发运单
     *
     * @param wmsBillOfLoading 发运单
     * @return 结果
     */
    public int updateWmsBillOfLoading(WmsBillOfLoading wmsBillOfLoading);

    /**
     * 删除发运单
     *
     * @param id 发运单主键
     * @return 结果
     */
    public int deleteWmsBillOfLoadingById(String id);

    /**
     * 批量删除发运单
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsBillOfLoadingByIds(String[] ids);

    /**
     * 查询当天的发运单数量
     *
     * @return int
     */
    int selectWmsBillOfLoadingCountByToday();

    WmsBillOfLoading selectByNoticeId(String noticeId);
}
