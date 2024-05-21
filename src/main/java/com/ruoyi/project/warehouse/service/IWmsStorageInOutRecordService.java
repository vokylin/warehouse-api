package com.ruoyi.project.warehouse.service;

import com.ruoyi.project.warehouse.domain.PrintItemDetail;
import com.ruoyi.project.warehouse.domain.StorageInOutRecordVO;
import com.ruoyi.project.warehouse.domain.WmsStorageInOutRecord;

import java.util.Date;
import java.util.List;

/**
 * 出入库记录Service接口
 *
 * @author Rem
 * @date 2023-04-08
 */
public interface IWmsStorageInOutRecordService {
    /**
     * 查询出入库记录
     *
     * @param id 出入库记录主键
     * @return 出入库记录
     */
    public WmsStorageInOutRecord selectWmsStorageInOutRecordById(String id);

    List<StorageInOutRecordVO> selectStorageRecord(String itemCode, String batchNo, String noticeId);

    /**
     * 查询出入库记录列表
     *
     * @param wmsStorageInOutRecord 出入库记录
     * @return 出入库记录集合
     */
    public List<WmsStorageInOutRecord> selectWmsStorageInOutRecordList(WmsStorageInOutRecord wmsStorageInOutRecord);

    /**
     * 新增出入库记录
     *
     * @param wmsStorageInOutRecord 出入库记录
     * @return 结果
     */
    public int insertWmsStorageInOutRecord(WmsStorageInOutRecord wmsStorageInOutRecord);

    /**
     * 修改出入库记录
     *
     * @param wmsStorageInOutRecord 出入库记录
     * @return 结果
     */
    public int updateWmsStorageInOutRecord(WmsStorageInOutRecord wmsStorageInOutRecord);

    /**
     * 批量删除出入库记录
     *
     * @param ids 需要删除的出入库记录主键集合
     * @return 结果
     */
    public int deleteWmsStorageInOutRecordByIds(String[] ids);

    /**
     * 删除出入库记录信息
     *
     * @param id 出入库记录主键
     * @return 结果
     */
    public int deleteWmsStorageInOutRecordById(String id);

    int getBillCode(String noticeId, String warehouseCode, Integer billType, Date occTime);

    int updateRemark(List<PrintItemDetail> printItemDetails);
}
