package com.ruoyi.project.system.mapper;

import com.ruoyi.project.system.domain.Logistics;

import java.util.List;

/**
 * 承运商Mapper接口
 *
 * @author Rem
 * @date 2023-04-11
 */
public interface LogisticsMapper {
    /**
     * 查询承运商
     *
     * @param id 承运商主键
     * @return 承运商
     */
    public Logistics selectLogisticsById(String id);

    /**
     * 查询承运商列表
     *
     * @param logistics 承运商
     * @return 承运商集合
     */
    public List<Logistics> selectLogisticsList(Logistics logistics);

    /**
     * 新增承运商
     *
     * @param logistics 承运商
     * @return 结果
     */
    public int insertLogistics(Logistics logistics);

    /**
     * 修改承运商
     *
     * @param logistics 承运商
     * @return 结果
     */
    public int updateLogistics(Logistics logistics);

    /**
     * 删除承运商
     *
     * @param id 承运商主键
     * @return 结果
     */
    public int deleteLogisticsById(String id);

    /**
     * 批量删除承运商
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLogisticsByIds(String[] ids);
}
