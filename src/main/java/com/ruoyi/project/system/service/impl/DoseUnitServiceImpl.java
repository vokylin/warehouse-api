package com.ruoyi.project.system.service.impl;

import com.ruoyi.project.system.domain.DoseUnit;
import com.ruoyi.project.system.mapper.DoseUnitMapper;
import com.ruoyi.project.system.service.IDoseUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 剂量单位Service业务层处理
 *
 * @author Rem
 * @date 2023-04-27
 */
@Service
public class DoseUnitServiceImpl implements IDoseUnitService {
    @Autowired
    private DoseUnitMapper doseUnitMapper;

    /**
     * 查询剂量单位
     *
     * @param id 剂量单位主键
     * @return 剂量单位
     */
    @Override
    public DoseUnit selectTbDoseUnitById(String id) {
        return doseUnitMapper.selectTbDoseUnitById(id);
    }

    /**
     * 查询剂量单位列表
     *
     * @param doseUnit 剂量单位
     * @return 剂量单位
     */
    @Override
    public List<DoseUnit> selectTbDoseUnitList(DoseUnit doseUnit) {
        return doseUnitMapper.selectTbDoseUnitList(doseUnit);
    }
}
