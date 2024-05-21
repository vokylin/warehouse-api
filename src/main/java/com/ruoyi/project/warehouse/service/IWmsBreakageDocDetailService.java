package com.ruoyi.project.warehouse.service;

import com.ruoyi.project.warehouse.domain.BreakageDocPrintInfo;
import com.ruoyi.project.warehouse.domain.WmsBreakageDocDetail;
import com.ruoyi.project.warehouse.domain.WmsItemStorageDetail;

import java.util.List;

/**
 * 报损物料明细Service接口
 *
 * @author Rem
 * @date 2023-05-20
 */
public interface IWmsBreakageDocDetailService {
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
     * 批量删除报损物料明细
     *
     * @param ids 需要删除的报损物料明细主键集合
     * @return 结果
     */
    public int deleteWmsBreakageDocDetailByIds(String[] ids);

    /**
     * 删除报损物料明细信息
     *
     * @param id 报损物料明细主键
     * @return 结果
     */
    public int deleteWmsBreakageDocDetailById(String id);

    void allotItemStorage(WmsItemStorageDetail wmsItemStorageDetail) throws Exception;

    void cancelAllotItemStorage(WmsBreakageDocDetail wmsBreakageDocDetail) throws Exception;

    void cancelAllotItemStorageAndDelete(WmsBreakageDocDetail wmsBreakageDocDetail) throws Exception;

    int generatePrintNumber(BreakageDocPrintInfo item);
}
