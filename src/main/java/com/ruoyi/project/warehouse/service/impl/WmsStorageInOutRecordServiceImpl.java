package com.ruoyi.project.warehouse.service.impl;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.warehouse.domain.PrintItemDetail;
import com.ruoyi.project.warehouse.domain.StorageInOutRecordVO;
import com.ruoyi.project.warehouse.domain.WmsStorageInOutDetail;
import com.ruoyi.project.warehouse.domain.WmsStorageInOutRecord;
import com.ruoyi.project.warehouse.mapper.WmsStorageInOutRecordMapper;
import com.ruoyi.project.warehouse.service.IWmsStorageInOutDetailService;
import com.ruoyi.project.warehouse.service.IWmsStorageInOutRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 出入库记录Service业务层处理
 *
 * @author Rem
 * @date 2023-04-08
 */
@Service
public class WmsStorageInOutRecordServiceImpl implements IWmsStorageInOutRecordService {
    @Autowired
    private WmsStorageInOutRecordMapper wmsStorageInOutRecordMapper;

    @Autowired
    private IWmsStorageInOutDetailService wmsStorageInOutDetailService;

    /**
     * 查询出入库记录
     *
     * @param id 出入库记录主键
     * @return 出入库记录
     */
    @Override
    public WmsStorageInOutRecord selectWmsStorageInOutRecordById(String id) {
        return wmsStorageInOutRecordMapper.selectWmsStorageInOutRecordById(id);
    }

    @Override
    public List<StorageInOutRecordVO> selectStorageRecord(String itemCode, String batchNo, String noticeId) {
        return wmsStorageInOutRecordMapper.selectStorageRecord(itemCode, batchNo, noticeId);
    }

    /**
     * 查询出入库记录列表
     *
     * @param wmsStorageInOutRecord 出入库记录
     * @return 出入库记录
     */
    @Override
    public List<WmsStorageInOutRecord> selectWmsStorageInOutRecordList(WmsStorageInOutRecord wmsStorageInOutRecord) {
        return wmsStorageInOutRecordMapper.selectWmsStorageInOutRecordList(wmsStorageInOutRecord);
    }

    /**
     * 新增出入库记录
     *
     * @param wmsStorageInOutRecord 出入库记录
     * @return 结果
     */
    @Override
    public int insertWmsStorageInOutRecord(WmsStorageInOutRecord wmsStorageInOutRecord) {
        wmsStorageInOutRecord.setCreateTime(DateUtils.getNowDate());
        return wmsStorageInOutRecordMapper.insertWmsStorageInOutRecord(wmsStorageInOutRecord);
    }

    /**
     * 修改出入库记录
     *
     * @param wmsStorageInOutRecord 出入库记录
     * @return 结果
     */
    @Override
    public int updateWmsStorageInOutRecord(WmsStorageInOutRecord wmsStorageInOutRecord) {
        wmsStorageInOutRecord.setUpdateTime(DateUtils.getNowDate());
        return wmsStorageInOutRecordMapper.updateWmsStorageInOutRecord(wmsStorageInOutRecord);
    }

    /**
     * 批量删除出入库记录
     *
     * @param ids 需要删除的出入库记录主键
     * @return 结果
     */
    @Override
    public int deleteWmsStorageInOutRecordByIds(String[] ids) {
        return wmsStorageInOutRecordMapper.deleteWmsStorageInOutRecordByIds(ids);
    }

    /**
     * 删除出入库记录信息
     *
     * @param id 出入库记录主键
     * @return 结果
     */
    @Override
    public int deleteWmsStorageInOutRecordById(String id) {
        return wmsStorageInOutRecordMapper.deleteWmsStorageInOutRecordById(id);
    }

    @Override
    public int getBillCode(String noticeId, String warehouseCode, Integer billType, Date occTime) {
        return wmsStorageInOutRecordMapper.getBillCode(noticeId, warehouseCode, billType,
                DateUtils.getMonthFirstDay(occTime) + " 00:00:00", DateUtils.getMonthLastDay(occTime) + " 23:59:59");
    }

    @Override
    public int updateRemark(List<PrintItemDetail> printItemDetails) {
        printItemDetails.forEach(printItemDetail -> {
            String[] ids = printItemDetail.getRecordDetailId().split(",");
            for (String id : ids) {
                WmsStorageInOutDetail updateStorageInOutDetail = new WmsStorageInOutDetail();
                updateStorageInOutDetail.setId(id);
                updateStorageInOutDetail.setRemark(printItemDetail.getRemark());
                wmsStorageInOutDetailService.updateWmsStorageInOutDetail(updateStorageInOutDetail);
            }
        });
        return Constants.SUCCESS_CODE;
    }

}
