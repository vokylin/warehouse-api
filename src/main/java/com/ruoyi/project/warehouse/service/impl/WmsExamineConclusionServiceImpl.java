package com.ruoyi.project.warehouse.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.project.warehouse.domain.WmsExamineConclusion;
import com.ruoyi.project.warehouse.mapper.WmsExamineConclusionMapper;
import com.ruoyi.project.warehouse.service.IWmsExamineConclusionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * examineConclusionService业务层处理
 *
 * @author Rem
 * @date 2023-04-21
 */
@Service
public class WmsExamineConclusionServiceImpl implements IWmsExamineConclusionService {
    @Autowired
    private WmsExamineConclusionMapper wmsExamineConclusionMapper;

    /**
     * 查询examineConclusion
     *
     * @param id examineConclusion主键
     * @return examineConclusion
     */
    @Override
    public WmsExamineConclusion selectWmsExamineConclusionById(String id) {
        return wmsExamineConclusionMapper.selectWmsExamineConclusionById(id);
    }

    /**
     * 查询examineConclusion列表
     *
     * @param wmsExamineConclusion examineConclusion
     * @return examineConclusion
     */
    @Override
    public List<WmsExamineConclusion> selectWmsExamineConclusionList(WmsExamineConclusion wmsExamineConclusion) {
        return wmsExamineConclusionMapper.selectWmsExamineConclusionList(wmsExamineConclusion);
    }

    /**
     * 新增examineConclusion
     *
     * @param wmsExamineConclusion examineConclusion
     * @return 结果
     */
    @Override
    public int insertWmsExamineConclusion(WmsExamineConclusion wmsExamineConclusion) {
        wmsExamineConclusion.setId(IdUtils.fastSimpleUUID());
        wmsExamineConclusion.setCreateBy(SecurityUtils.getUserId());
        wmsExamineConclusion.setCreateTime(DateUtils.getNowDate());
        return wmsExamineConclusionMapper.insertWmsExamineConclusion(wmsExamineConclusion);
    }

    /**
     * 修改examineConclusion
     *
     * @param wmsExamineConclusion examineConclusion
     * @return 结果
     */
    @Override
    public int updateWmsExamineConclusion(WmsExamineConclusion wmsExamineConclusion) {
        wmsExamineConclusion.setUpdateBy(SecurityUtils.getUserId());
        wmsExamineConclusion.setUpdateTime(DateUtils.getNowDate());
        return wmsExamineConclusionMapper.updateWmsExamineConclusion(wmsExamineConclusion);
    }

    /**
     * 批量删除examineConclusion
     *
     * @param ids 需要删除的examineConclusion主键
     * @return 结果
     */
    @Override
    public int deleteWmsExamineConclusionByIds(String[] ids) {
        return wmsExamineConclusionMapper.deleteWmsExamineConclusionByIds(ids);
    }

    /**
     * 删除examineConclusion信息
     *
     * @param id examineConclusion主键
     * @return 结果
     */
    @Override
    public int deleteWmsExamineConclusionById(String id) {
        return wmsExamineConclusionMapper.deleteWmsExamineConclusionById(id);
    }

    @Override
    public WmsExamineConclusion selectByExamineId(String id) {
        return wmsExamineConclusionMapper.selectByExamineId(id);
    }
}
