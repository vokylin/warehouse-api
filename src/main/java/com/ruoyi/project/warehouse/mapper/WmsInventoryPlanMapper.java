package com.ruoyi.project.warehouse.mapper;

import com.ruoyi.project.warehouse.domain.WmsInventoryPlan;

import java.util.List;

/**
 * 盘点计划Mapper接口
 *
 * @author Rem
 * @date 2023-05-21
 */
public interface WmsInventoryPlanMapper {
    /**
     * 查询盘点计划
     *
     * @param id 盘点计划主键
     * @return 盘点计划
     */
    public WmsInventoryPlan selectWmsInventoryPlanById(String id);

    /**
     * 查询盘点计划列表
     *
     * @param wmsInventoryPlan 盘点计划
     * @return 盘点计划集合
     */
    public List<WmsInventoryPlan> selectWmsInventoryPlanList(WmsInventoryPlan wmsInventoryPlan);

    /**
     * 新增盘点计划
     *
     * @param wmsInventoryPlan 盘点计划
     * @return 结果
     */
    public int insertWmsInventoryPlan(WmsInventoryPlan wmsInventoryPlan);

    /**
     * 修改盘点计划
     *
     * @param wmsInventoryPlan 盘点计划
     * @return 结果
     */
    public int updateWmsInventoryPlan(WmsInventoryPlan wmsInventoryPlan);

    /**
     * 删除盘点计划
     *
     * @param id 盘点计划主键
     * @return 结果
     */
    public int deleteWmsInventoryPlanById(String id);

    /**
     * 批量删除盘点计划
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsInventoryPlanByIds(String[] ids);

    /**
     * 查询当天盘点计划数量
     *
     * @return int
     */
    int selectCountByToday();
}
