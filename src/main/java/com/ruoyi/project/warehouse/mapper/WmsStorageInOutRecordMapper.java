package com.ruoyi.project.warehouse.mapper;

import com.ruoyi.project.warehouse.domain.StorageInOutRecordVO;
import com.ruoyi.project.warehouse.domain.WmsStorageInOutRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 出入库记录Mapper接口
 *
 * @author Rem
 * @date 2023-04-08
 */
public interface WmsStorageInOutRecordMapper {
    /**
     * 查询出入库记录
     *
     * @param id 出入库记录主键
     * @return 出入库记录
     */
    public WmsStorageInOutRecord selectWmsStorageInOutRecordById(String id);

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
     * 删除出入库记录
     *
     * @param id 出入库记录主键
     * @return 结果
     */
    public int deleteWmsStorageInOutRecordById(String id);

    /**
     * 批量删除出入库记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsStorageInOutRecordByIds(String[] ids);

    WmsStorageInOutRecord selectWmsStorageInOutRecordByItemCode(String itemCode);

    WmsStorageInOutRecord selectByBatchNo(WmsStorageInOutRecord wmsStorageInOutRecord);

    int getBillCode(String noticeId, String warehouseCode, Integer billType, String startDate, String endDate);

    List<StorageInOutRecordVO> selectStorageRecord(@Param("itemCode") String itemCode, @Param("batchNo") String batchNo, @Param("noticeId") String noticeId);
}
