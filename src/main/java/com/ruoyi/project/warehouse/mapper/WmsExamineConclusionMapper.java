package com.ruoyi.project.warehouse.mapper;

import com.ruoyi.project.warehouse.domain.WmsExamineConclusion;

import java.util.List;

/**
 * examineConclusionMapper接口
 *
 * @author Rem
 * @date 2023-04-21
 */
public interface WmsExamineConclusionMapper {
    /**
     * 查询examineConclusion
     *
     * @param id examineConclusion主键
     * @return examineConclusion
     */
    public WmsExamineConclusion selectWmsExamineConclusionById(String id);

    /**
     * 查询examineConclusion列表
     *
     * @param wmsExamineConclusion examineConclusion
     * @return examineConclusion集合
     */
    public List<WmsExamineConclusion> selectWmsExamineConclusionList(WmsExamineConclusion wmsExamineConclusion);

    /**
     * 新增examineConclusion
     *
     * @param wmsExamineConclusion examineConclusion
     * @return 结果
     */
    public int insertWmsExamineConclusion(WmsExamineConclusion wmsExamineConclusion);

    /**
     * 修改examineConclusion
     *
     * @param wmsExamineConclusion examineConclusion
     * @return 结果
     */
    public int updateWmsExamineConclusion(WmsExamineConclusion wmsExamineConclusion);

    /**
     * 删除examineConclusion
     *
     * @param id examineConclusion主键
     * @return 结果
     */
    public int deleteWmsExamineConclusionById(String id);

    /**
     * 批量删除examineConclusion
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsExamineConclusionByIds(String[] ids);

    WmsExamineConclusion selectByExamineId(String id);
}
