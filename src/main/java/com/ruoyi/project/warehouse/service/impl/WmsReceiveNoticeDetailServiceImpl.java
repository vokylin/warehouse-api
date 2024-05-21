package com.ruoyi.project.warehouse.service.impl;

import com.ruoyi.common.enums.ItemType;
import com.ruoyi.common.enums.ReceiptStatus;
import com.ruoyi.common.enums.ReceiveItemDetailWorkStatus;
import com.ruoyi.common.enums.ReciveItemStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.component.StorageManager;
import com.ruoyi.project.warehouse.domain.WmsReceiveItemDetail;
import com.ruoyi.project.warehouse.domain.WmsReceiveNoticeDetail;
import com.ruoyi.project.warehouse.mapper.WmsReceiveItemDetailMapper;
import com.ruoyi.project.warehouse.mapper.WmsReceiveNoticeDetailMapper;
import com.ruoyi.project.warehouse.service.IWmsReceiveNoticeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 入库通知明细Service业务层处理
 *
 * @author Rem
 * @date 2023-03-31
 */
@Service
public class WmsReceiveNoticeDetailServiceImpl implements IWmsReceiveNoticeDetailService {
    @Autowired
    private WmsReceiveNoticeDetailMapper wmsReceiveNoticeDetailMapper;

    @Autowired
    private WmsReceiveItemDetailMapper wmsReceiveItemDetailMapper;

    @Autowired
    private StorageManager storageManager;

    /**
     * 查询入库通知明细
     *
     * @param id 入库通知明细主键
     * @return 入库通知明细
     */
    @Override
    public WmsReceiveNoticeDetail selectWmsReceiveNoticeDetailById(String id) {
        return wmsReceiveNoticeDetailMapper.selectWmsReceiveNoticeDetailById(id);
    }

    /**
     * 查询入库通知明细列表
     *
     * @param wmsReceiveNoticeDetail 入库通知明细
     * @return 入库通知明细
     */
    @Override
    public List<WmsReceiveNoticeDetail> selectWmsReceiveNoticeDetailList(WmsReceiveNoticeDetail wmsReceiveNoticeDetail) {
        return wmsReceiveNoticeDetailMapper.selectWmsReceiveNoticeDetailList(wmsReceiveNoticeDetail);
    }

    /**
     * 新增入库通知明细
     *
     * @param wmsReceiveNoticeDetail 入库通知明细
     * @return 结果
     */
    @Override
    public int insertWmsReceiveNoticeDetail(WmsReceiveNoticeDetail wmsReceiveNoticeDetail) {
        wmsReceiveNoticeDetail.setCreateTime(DateUtils.getNowDate());
        return wmsReceiveNoticeDetailMapper.insertWmsReceiveNoticeDetail(wmsReceiveNoticeDetail);
    }

    /**
     * 修改入库通知明细
     *
     * @param wmsReceiveNoticeDetail 入库通知明细
     * @return 结果
     */
    @Override
    public int updateWmsReceiveNoticeDetail(WmsReceiveNoticeDetail wmsReceiveNoticeDetail) {
        wmsReceiveNoticeDetail.setUpdateTime(DateUtils.getNowDate());
        return wmsReceiveNoticeDetailMapper.updateWmsReceiveNoticeDetail(wmsReceiveNoticeDetail);
    }

    /**
     * 批量删除入库通知明细
     *
     * @param ids 需要删除的入库通知明细主键
     * @return 结果
     */
    @Override
    public int deleteWmsReceiveNoticeDetailByIds(String[] ids) {
        return wmsReceiveNoticeDetailMapper.deleteWmsReceiveNoticeDetailByIds(ids);
    }

    /**
     * 删除入库通知明细信息
     *
     * @param id 入库通知明细主键
     * @return 结果
     */
    @Override
    public int deleteWmsReceiveNoticeDetailById(String id) {
        return wmsReceiveNoticeDetailMapper.deleteWmsReceiveNoticeDetailById(id);
    }

    /**
     * 收货
     *
     * @param wmsReceiveNoticeDetail 收货通知详情
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult receipt(WmsReceiveNoticeDetail wmsReceiveNoticeDetail) {

        WmsReceiveNoticeDetail noticeDetail = wmsReceiveNoticeDetailMapper.selectWmsReceiveNoticeDetailById(wmsReceiveNoticeDetail.getId());
        if (noticeDetail == null) {
            return AjaxResult.error("收货失败，收货通知不存在");
        }
        if (ReceiptStatus.CANCEL.getCode().equals(noticeDetail.getStatus())) {
            return AjaxResult.error("收货失败，收货通知已取消");
        }
        if (ReceiptStatus.RECEIVED.getCode().equals(noticeDetail.getStatus())) {
            return AjaxResult.error("收货失败，已收货的收货通知不能再次收货");
        }

        BigDecimal quantity = wmsReceiveNoticeDetail.getQuantity();
        BigDecimal allotQuantity = wmsReceiveNoticeDetail.getAllotQuantity();
        if (allotQuantity.compareTo(quantity) != 0) {
            return AjaxResult.error("收货数量不正确");
        }
        // 更新收货数量
        WmsReceiveNoticeDetail receiveNoticeDetail = new WmsReceiveNoticeDetail();
        receiveNoticeDetail.setId(wmsReceiveNoticeDetail.getId());
        receiveNoticeDetail.setProductDate(wmsReceiveNoticeDetail.getProductDate());
        receiveNoticeDetail.setExpireTime(wmsReceiveNoticeDetail.getExpireTime());
        receiveNoticeDetail.setBatchNo(wmsReceiveNoticeDetail.getBatchNo());
        receiveNoticeDetail.setLocationId(wmsReceiveNoticeDetail.getLocationId());
        receiveNoticeDetail.setReceiveQuantity(quantity);
        receiveNoticeDetail.setStatus(ReceiptStatus.RECEIVING.getCode());
        receiveNoticeDetail.setUpdateBy(SecurityUtils.getUserId());
        receiveNoticeDetail.setUpdateTime(DateUtils.getNowDate());
        int result = wmsReceiveNoticeDetailMapper.updateWmsReceiveNoticeDetail(receiveNoticeDetail);
        if (result == 0) {
            return AjaxResult.error("收货失败");
        }

        // 保存收货货物明细
        WmsReceiveItemDetail wmsReceiveItemDetail = new WmsReceiveItemDetail();

        // 如果没有生产日期,则根据批次号和货品编码获取生产日期
        if (null == wmsReceiveNoticeDetail.getProductDate()) {
            storageManager.getProductDate(wmsReceiveNoticeDetail);
        }
        if (null == wmsReceiveNoticeDetail.getExpireTime()) {
            Date expireDate = storageManager.getExpireDate(wmsReceiveNoticeDetail.getProductDate(), wmsReceiveNoticeDetail.getBatchNo(), wmsReceiveNoticeDetail.getItemCode());
            wmsReceiveNoticeDetail.setExpireTime(expireDate);
        }

        wmsReceiveItemDetail.setId(wmsReceiveNoticeDetail.getId());
        wmsReceiveItemDetail.setId(IdUtils.fastSimpleUUID());
        wmsReceiveItemDetail.setItemCode(wmsReceiveNoticeDetail.getItemCode());
        wmsReceiveItemDetail.setItemName(wmsReceiveNoticeDetail.getItemName());
        wmsReceiveItemDetail.setPrice(wmsReceiveNoticeDetail.getItemPrice());
        wmsReceiveItemDetail.setUnit(wmsReceiveNoticeDetail.getUnit());
        // 进料验收 待检验
        wmsReceiveItemDetail.setStatus(ReciveItemStatus.WAITING_FOR_INSPECTION.getCode());
        wmsReceiveItemDetail.setQuantity(allotQuantity);
        wmsReceiveItemDetail.setPassQuantity(BigDecimal.ZERO);
        wmsReceiveItemDetail.setProductDate(wmsReceiveNoticeDetail.getProductDate());
        wmsReceiveItemDetail.setExpireTime(wmsReceiveNoticeDetail.getExpireTime());
        wmsReceiveItemDetail.setWorkStatus(ReceiveItemDetailWorkStatus.WAIT_WORK.getCode());
        wmsReceiveItemDetail.setBatchNo(wmsReceiveNoticeDetail.getBatchNo());
        wmsReceiveItemDetail.setReceiveNoticeId(wmsReceiveNoticeDetail.getReceiveNoticeId());
        wmsReceiveItemDetail.setReceiveNoticeDetailId(wmsReceiveNoticeDetail.getId());
        wmsReceiveItemDetail.setCreateBy(SecurityUtils.getUserId());
        wmsReceiveItemDetail.setCreateTime(wmsReceiveNoticeDetail.getCreateTime());
        int addResult = wmsReceiveItemDetailMapper.insertWmsReceiveItemDetail(wmsReceiveItemDetail);
        if (addResult == 0) {
            return AjaxResult.error("收货失败");
        }

        return AjaxResult.success();
    }


    /**
     * 分配
     *
     * @param wmsReceiveNoticeDetail 收货通知详情
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult allot(WmsReceiveNoticeDetail wmsReceiveNoticeDetail) {

        // 校验库存单位和通知单位是否一致
        if (storageManager.checkUnit(wmsReceiveNoticeDetail)) {
            return AjaxResult.error("库存单位和通知单位不一致");
        }

        WmsReceiveNoticeDetail noticeDetail = wmsReceiveNoticeDetailMapper.selectWmsReceiveNoticeDetailById(wmsReceiveNoticeDetail.getId());
        if (noticeDetail == null) {
            return AjaxResult.error("分配失败，收货通知不存在");
        }
        if (ReceiptStatus.CANCEL.getCode().equals(noticeDetail.getStatus())) {
            return AjaxResult.error("分配失败，收货通知已取消");
        }
        if (ReceiptStatus.RECEIVED.getCode().equals(noticeDetail.getStatus())) {
            return AjaxResult.error("分配失败，已收货完成的通知不能再次收货");
        }

        // 分配数量
        BigDecimal allotQuantity = wmsReceiveNoticeDetail.getAllotQuantity();
        // 通知数量
        BigDecimal quantity = wmsReceiveNoticeDetail.getQuantity();
        // 已收货数量
        BigDecimal receivedQuantity = wmsReceiveNoticeDetail.getReceiveQuantity();

        if (allotQuantity.compareTo(quantity.subtract(receivedQuantity)) > 0) {
            return AjaxResult.error("分配数量不能大于剩余可分配数量");
        }

        // 剩余可分配数量
        WmsReceiveNoticeDetail receiveNoticeDetail = new WmsReceiveNoticeDetail();
        receiveNoticeDetail.setId(wmsReceiveNoticeDetail.getId());
        // 已收货数量
        receiveNoticeDetail.setReceiveQuantity(receivedQuantity.add(allotQuantity));
        receiveNoticeDetail.setProductDate(wmsReceiveNoticeDetail.getProductDate());
        receiveNoticeDetail.setBatchNo(wmsReceiveNoticeDetail.getBatchNo());
        receiveNoticeDetail.setUpdateBy(SecurityUtils.getUserId());
        receiveNoticeDetail.setUpdateTime(DateUtils.getNowDate());
        receiveNoticeDetail.setLocationId(wmsReceiveNoticeDetail.getLocationId());
        receiveNoticeDetail.setStatus(ReceiptStatus.RECEIVING.getCode());

        int result = wmsReceiveNoticeDetailMapper.updateWmsReceiveNoticeDetail(receiveNoticeDetail);
        if (result == 0) {
            return AjaxResult.error("收货失败");
        }
        // 如果没有生产日期,则根据批次号和货品编码获取生产日期 并获取过期日期
        if (null == wmsReceiveNoticeDetail.getProductDate()) {
            storageManager.getProductDate(wmsReceiveNoticeDetail);
        }

        if (null == wmsReceiveNoticeDetail.getExpireTime()) {
            Date expireDate = storageManager.getExpireDate(wmsReceiveNoticeDetail.getProductDate(), wmsReceiveNoticeDetail.getBatchNo(), wmsReceiveNoticeDetail.getItemCode());
            wmsReceiveNoticeDetail.setExpireTime(expireDate);
        }

        // 查询是否分配过
        WmsReceiveItemDetail searchInfo = new WmsReceiveItemDetail();
        searchInfo.setReceiveNoticeDetailId(wmsReceiveNoticeDetail.getId());
        searchInfo.setItemCode(wmsReceiveNoticeDetail.getItemCode());
        searchInfo.setBatchNo(wmsReceiveNoticeDetail.getBatchNo());
        WmsReceiveItemDetail wmsReceiveItemDetail = wmsReceiveItemDetailMapper.selectReceiveItemDetail(searchInfo);

        if (null != wmsReceiveItemDetail) {
            WmsReceiveItemDetail receiveItemDetail = new WmsReceiveItemDetail();
            receiveItemDetail.setId(wmsReceiveItemDetail.getId());
            receiveItemDetail.setQuantity(wmsReceiveItemDetail.getQuantity().add(allotQuantity));
            // 判断货品类型
            Integer itemType = wmsReceiveNoticeDetail.getItemType();
            if (itemType.equals(ItemType.PURCHASE_ACCEPTANCE.getCode()) || itemType.equals(ItemType.OTHER.getCode())) {
                // 进货验收
                receiveItemDetail.setPassQuantity(wmsReceiveItemDetail.getPassQuantity().add(allotQuantity));
            } else if (itemType.equals(ItemType.PROCUREMENT_SCRAP.getCode()) || itemType.equals(ItemType.SPECIAL_COLLECTION.getCode())) {
                // 采购报废
                receiveItemDetail.setFailQuantity(wmsReceiveItemDetail.getFailQuantity().add(allotQuantity));
            }
            receiveItemDetail.setUpdateBy(SecurityUtils.getUserId());
            receiveItemDetail.setUpdateTime(DateUtils.getNowDate());
            wmsReceiveItemDetailMapper.updateWmsReceiveItemDetail(receiveItemDetail);
        } else {
            // 保存收货货物明细
            WmsReceiveItemDetail addReceiveItemDetail = new WmsReceiveItemDetail();
            addReceiveItemDetail.setId(IdUtils.fastSimpleUUID());
            addReceiveItemDetail.setReceiveNoticeDetailId(wmsReceiveNoticeDetail.getId());
            addReceiveItemDetail.setReceiveNoticeId(wmsReceiveNoticeDetail.getReceiveNoticeId());
            addReceiveItemDetail.setItemCode(wmsReceiveNoticeDetail.getItemCode());
            addReceiveItemDetail.setItemName(wmsReceiveNoticeDetail.getItemName());
            addReceiveItemDetail.setUnit(wmsReceiveNoticeDetail.getUnit());
            addReceiveItemDetail.setQuantity(allotQuantity);
            addReceiveItemDetail.setExpireTime(wmsReceiveNoticeDetail.getExpireTime());
            addReceiveItemDetail.setPrice(wmsReceiveNoticeDetail.getItemPrice());
            // 判断货品类型
            Integer itemType = wmsReceiveNoticeDetail.getItemType();
            if (itemType.equals(ItemType.PURCHASE_ACCEPTANCE.getCode()) || itemType.equals(ItemType.OTHER.getCode())) {
                // 进货验收 合格
                addReceiveItemDetail.setStatus(ReciveItemStatus.QUALIFIED.getCode());
                addReceiveItemDetail.setPassQuantity(allotQuantity);
            } else if (itemType.equals(ItemType.PROCUREMENT_SCRAP.getCode()) || itemType.equals(ItemType.SPECIAL_COLLECTION.getCode())) {
                // 采购报废 不合格
                addReceiveItemDetail.setStatus(ReciveItemStatus.UNQUALIFIED.getCode());
                addReceiveItemDetail.setFailQuantity(allotQuantity);
            }

            // 分配后更新收货明细状态为收货中
            addReceiveItemDetail.setWorkStatus(ReceiveItemDetailWorkStatus.WAIT_WORK.getCode());
            addReceiveItemDetail.setProductDate(wmsReceiveNoticeDetail.getProductDate());
            addReceiveItemDetail.setBatchNo(wmsReceiveNoticeDetail.getBatchNo());
            addReceiveItemDetail.setCreateBy(SecurityUtils.getUserId());
            addReceiveItemDetail.setCreateTime(DateUtils.getNowDate());
            wmsReceiveItemDetailMapper.insertWmsReceiveItemDetail(addReceiveItemDetail);
        }

        return AjaxResult.success();
    }

}
