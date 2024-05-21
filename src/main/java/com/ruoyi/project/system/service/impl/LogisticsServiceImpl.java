package com.ruoyi.project.system.service.impl;

import com.ruoyi.project.system.domain.Logistics;
import com.ruoyi.project.system.mapper.LogisticsMapper;
import com.ruoyi.project.system.service.ILogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 承运商Service业务层处理
 *
 * @author Rem
 * @date 2023-04-11
 */
@Service
public class LogisticsServiceImpl implements ILogisticsService {
    @Autowired
    private LogisticsMapper logisticsMapper;

    /**
     * 查询承运商
     *
     * @param id 承运商主键
     * @return 承运商
     */
    @Override
    public Logistics selectLogisticsById(String id) {
        return logisticsMapper.selectLogisticsById(id);
    }

    /**
     * 查询承运商列表
     *
     * @param logistics 承运商
     * @return 承运商
     */
    @Override
    public List<Logistics> selectLogisticsList(Logistics logistics) {
        return logisticsMapper.selectLogisticsList(logistics);
    }

    /**
     * 新增承运商
     *
     * @param logistics 承运商
     * @return 结果
     */
    @Override
    public int insertLogistics(Logistics logistics) {
        return logisticsMapper.insertLogistics(logistics);
    }

    /**
     * 修改承运商
     *
     * @param logistics 承运商
     * @return 结果
     */
    @Override
    public int updateLogistics(Logistics logistics) {
        return logisticsMapper.updateLogistics(logistics);
    }

    /**
     * 批量删除承运商
     *
     * @param ids 需要删除的承运商主键
     * @return 结果
     */
    @Override
    public int deleteLogisticsByIds(String[] ids) {
        return logisticsMapper.deleteLogisticsByIds(ids);
    }

    /**
     * 删除承运商信息
     *
     * @param id 承运商主键
     * @return 结果
     */
    @Override
    public int deleteLogisticsById(String id) {
        return logisticsMapper.deleteLogisticsById(id);
    }
}
