package com.ruoyi.project.system.service;

import com.ruoyi.project.system.domain.DoseUnit;

import java.util.List;

/**
 * 剂量单位Service接口
 *
 * @author Rem
 * @date 2023-04-27
 */
public interface IDoseUnitService {
    /**
     * 查询剂量单位
     *
     * @param id 剂量单位主键
     * @return 剂量单位
     */
    public DoseUnit selectTbDoseUnitById(String id);

    /**
     * 查询剂量单位列表
     *
     * @param doseUnit 剂量单位
     * @return 剂量单位集合
     */
    public List<DoseUnit> selectTbDoseUnitList(DoseUnit doseUnit);

}
