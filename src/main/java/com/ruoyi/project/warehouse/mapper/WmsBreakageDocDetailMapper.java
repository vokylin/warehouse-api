package com.ruoyi.project.warehouse.mapper;

import com.ruoyi.project.warehouse.domain.WmsBreakageDocDetail;

import java.util.List;

/**
 * 报损物料明细Mapper接口
 *
 * @author Rem
 * @date 2023-05-20
 */
public interface WmsBreakageDocDetailMapper {
    /**
     * 查询报损物料明细
     *
     * @param id 报损物料明细主键
     * @return 报损物料明细
     */
    public WmsBreakageDocDetail selectWmsBreakageDocDetailById(String id);

    /**
     * 查询报损物料明细列表
     *
     * @param wmsBreakageDocDetail 报损物料明细
     * @return 报损物料明细集合
     */
    public List<WmsBreakageDocDetail> selectWmsBreakageDocDetailList(WmsBreakageDocDetail wmsBreakageDocDetail);

    WmsBreakageDocDetail selectDetail(WmsBreakageDocDetail wmsBreakageDocDetail);

    /**
     * 新增报损物料明细
     *
     * @param wmsBreakageDocDetail 报损物料明细
     * @return 结果
     */
    public int insertWmsBreakageDocDetail(WmsBreakageDocDetail wmsBreakageDocDetail);

    /**
     * 修改报损物料明细
     *
     * @param wmsBreakageDocDetail 报损物料明细
     * @return 结果
     */
    public int updateWmsBreakageDocDetail(WmsBreakageDocDetail wmsBreakageDocDetail);

    /**
     * 删除报损物料明细
     *
     * @param id 报损物料明细主键
     * @return 结果
     */
    public int deleteWmsBreakageDocDetailById(String id);

    /**
     * 批量删除报损物料明细
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsBreakageDocDetailByIds(String[] ids);

    List<WmsBreakageDocDetail> selectByDocId(String docId);

    int generatePrintNumber(String detailId, String startDate, String endDate);
}
