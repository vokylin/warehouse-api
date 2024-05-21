package com.ruoyi.project.warehouse.service.impl;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.enums.*;
import com.ruoyi.common.exception.business.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.component.CodeService;
import com.ruoyi.project.common.component.ReceiveNoticeManager;
import com.ruoyi.project.common.component.StorageManager;
import com.ruoyi.project.common.component.StorageRecordManager;
import com.ruoyi.project.warehouse.domain.*;
import com.ruoyi.project.warehouse.mapper.*;
import com.ruoyi.project.warehouse.service.IWmsReceiveItemDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 入库货物明细Service业务层处理
 *
 * @author Rem
 * @date 2023-03-31
 */
@Service
public class WmsReceiveItemDetailServiceImpl implements IWmsReceiveItemDetailService {
    @Autowired
    private WmsReceiveItemDetailMapper wmsReceiveItemDetailMapper;

    @Autowired
    private WmsReceiveNoticeDetailMapper wmsReceiveNoticeDetailMapper;

    @Autowired
    private WmsReceiveNoticeMapper wmsReceiveNoticeMapper;

    @Autowired
    private WmsExamineWorkMapper wmsExamineWorkMapper;

    @Autowired
    private CodeService codeService;

    @Autowired
    private ReceiveNoticeManager receiveNoticeManager;

    @Autowired
    private StorageManager storageManager;

    @Autowired
    private StorageRecordManager storageRecordManager;

    @Autowired
    private WmsWorkNoticeMapper wmsWorkNoticeMapper;

    @Autowired
    private WmsWorkNoticeDetailMapper wmsWorkNoticeDetailMapper;


    /**
     * 查询入库货物明细
     *
     * @param id 入库货物明细主键
     * @return 入库货物明细
     */
    @Override
    public WmsReceiveItemDetail selectWmsReceiveItemDetailById(String id) {
        return wmsReceiveItemDetailMapper.selectWmsReceiveItemDetailById(id);
    }

    /**
     * 查询入库货物明细列表
     *
     * @param wmsReceiveItemDetail 入库货物明细
     * @return 入库货物明细
     */
    @Override
    public List<WmsReceiveItemDetail> selectWmsReceiveItemDetailList(WmsReceiveItemDetail wmsReceiveItemDetail) {
        return wmsReceiveItemDetailMapper.selectWmsReceiveItemDetailList(wmsReceiveItemDetail);
    }

    /**
     * 新增入库货物明细
     *
     * @param wmsReceiveItemDetail 入库货物明细
     * @return 结果
     */
    @Override
    public int insertWmsReceiveItemDetail(WmsReceiveItemDetail wmsReceiveItemDetail) {
        wmsReceiveItemDetail.setCreateTime(DateUtils.getNowDate());
        return wmsReceiveItemDetailMapper.insertWmsReceiveItemDetail(wmsReceiveItemDetail);
    }

    /**
     * 修改入库货物明细
     *
     * @param wmsReceiveItemDetail 入库货物明细
     * @return 结果
     */
    @Override
    public int updateWmsReceiveItemDetail(WmsReceiveItemDetail wmsReceiveItemDetail) {
        wmsReceiveItemDetail.setUpdateTime(DateUtils.getNowDate());
        return wmsReceiveItemDetailMapper.updateWmsReceiveItemDetail(wmsReceiveItemDetail);
    }

    /**
     * 批量删除入库货物明细
     *
     * @param ids 需要删除的入库货物明细主键
     * @return 结果
     */
    @Override
    public int deleteWmsReceiveItemDetailByIds(String[] ids) {
        return wmsReceiveItemDetailMapper.deleteWmsReceiveItemDetailByIds(ids);
    }

    /**
     * 删除入库货物明细信息
     *
     * @param id 入库货物明细主键
     * @return 结果
     */
    @Override
    public int deleteWmsReceiveItemDetailById(String id) {
        return wmsReceiveItemDetailMapper.deleteWmsReceiveItemDetailById(id);
    }

    /**
     * 上架
     *
     * @param wmsReceiveItemDetail@return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult shelves(WmsReceiveItemDetail wmsReceiveItemDetail) {

        ReceiveNoticeInfo receiveNoticeInfo = receiveNoticeManager.getReceiveNoticeInfoByItemDetailId(wmsReceiveItemDetail.getId());
        WmsReceiveItemDetail receiveItemDetail = receiveNoticeInfo.getWmsReceiveItemDetail();
        WmsReceiveNotice receiveNotice = receiveNoticeInfo.getWmsReceiveNotice();
        WmsReceiveNoticeDetail receiveNoticeDetail = receiveNoticeInfo.getWmsReceiveNoticeDetail();

        if (null == receiveNotice || null == receiveNoticeDetail || null == receiveItemDetail) {
            return AjaxResult.error("上架失败，未找到对应的入库通知单或者入库通知单明细或者入库通知单货物明细");
        }
        if (wmsReceiveItemDetail.getWorkStatus().equals(ReceiveItemDetailWorkStatus.ON_THE_SHELF.getCode())) {
            return AjaxResult.error("上架失败, 该货物明细已经上架");
        }
        // 上架数量
        BigDecimal shelvesQuantity = BigDecimal.ZERO;

        // 如果是采购报废 或者 是特采
        if (ScrapSpecialFlag.SCRAP.getCode().equals(receiveNotice.getScrapSpecialFlag()) || ScrapSpecialFlag.SPECIAL.getCode().equals(receiveNotice.getScrapSpecialFlag())) {
            if (wmsReceiveItemDetail.getFailQuantity().compareTo(BigDecimal.ZERO) <= 0) {
                return AjaxResult.error("数量必须大于0,才能上架");
            }
            shelvesQuantity = wmsReceiveItemDetail.getFailQuantity();
        } else {
            // 合格数量等于 0时，不能产生上架作业
            if (wmsReceiveItemDetail.getPassQuantity().compareTo(BigDecimal.ZERO) <= 0) {
                return AjaxResult.error("合格数量必须大于0，才能上架");
            }
            shelvesQuantity = wmsReceiveItemDetail.getPassQuantity();
        }


        // 判断货物类型
        if (receiveNoticeDetail.getItemType().equals(ItemType.INBOUND_ACCEPTANCE.getCode())) {
            // 进料验收上架 需要更新质检通知单的状态 防止重复上架
            WmsExamineWork examineWork = wmsExamineWorkMapper.selectWmsExamineWorkByReceiveItemDetailId(receiveItemDetail.getId());
            examineWork.setOperateStatus(ExamineWorkNoticeStatus.ON_THE_SHELF.getCode());
            examineWork.setUpdateBy(SecurityUtils.getUserId());
            examineWork.setUpdateTime(DateUtils.getNowDate());
            wmsExamineWorkMapper.updateWmsExamineWork(examineWork);
        }


        // 更新入库货物明细
        receiveItemDetail.setWorkStatus(ReceiveItemDetailWorkStatus.ON_THE_SHELF.getCode());
        receiveItemDetail.setUpdateTime(DateUtils.getNowDate());
        receiveItemDetail.setUpdateBy(SecurityUtils.getUserId());
        wmsReceiveItemDetailMapper.updateWmsReceiveItemDetail(receiveItemDetail);

        // 生成上架通知
        String code = codeService.generateShelfWorkCode();
        WmsWorkNotice wmsWorkNotice = new WmsWorkNotice();
        wmsWorkNotice.setId(code);
        wmsWorkNotice.setNoticeId(receiveNotice.getId());
        wmsWorkNotice.setNoticeItemDetailId(receiveItemDetail.getId());
        wmsWorkNotice.setType(WorkType.SHELF.getCode());
        wmsWorkNotice.setStatus(ShelfWorkNoticeStatus.NORMAL.getCode());
        wmsWorkNotice.setCompanyId(receiveNotice.getCompanyId());
        wmsWorkNotice.setReceiveSendType(receiveNotice.getReceiveSendType());
        wmsWorkNotice.setCreateBy(SecurityUtils.getUserId());
        wmsWorkNotice.setCreateTime(DateUtils.getNowDate());
        wmsWorkNoticeMapper.insertWmsWorkNotice(wmsWorkNotice);

        // 生成上架通知明细
        WmsWorkNoticeDetail wmsWorkNoticeDetail = new WmsWorkNoticeDetail();
        wmsWorkNoticeDetail.setId(IdUtils.fastSimpleUUID());
        wmsWorkNoticeDetail.setWorkNoticeId(code);
        wmsWorkNoticeDetail.setNoticeDetailId(receiveNoticeDetail.getId());
        wmsWorkNoticeDetail.setNoticeItemDetailId(receiveItemDetail.getId());
        wmsWorkNoticeDetail.setItemCode(receiveItemDetail.getItemCode());

        // 通知数量等于上架数量
        wmsWorkNoticeDetail.setQuantity(shelvesQuantity);

        wmsWorkNoticeDetail.setAllotQuantity(BigDecimal.ZERO);
        wmsWorkNoticeDetail.setBatchNo(receiveItemDetail.getBatchNo());
        wmsWorkNoticeDetail.setSourceLocationId(receiveNoticeDetail.getLocationId());
        wmsWorkNoticeDetail.setCreateBy(SecurityUtils.getUserId());
        wmsWorkNoticeDetail.setCreateTime(DateUtils.getNowDate());
        wmsWorkNoticeDetailMapper.insertWmsWorkNoticeDetail(wmsWorkNoticeDetail);

        receiveNoticeInfo.setWorkNoticeId(code);
        // 记录入库信息
        List<BigDecimal> priceInfo = storageRecordManager.saveInRecord(receiveNoticeInfo);
        // 保存库存信息
        storageManager.storage(receiveNoticeInfo, priceInfo);

        return AjaxResult.success();
    }

    /**
     * 质检上架
     *
     * @param wmsExamineWork
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult shelves(WmsExamineWork wmsExamineWork) {
        ReceiveNoticeInfo receiveNoticeInfo = receiveNoticeManager.getReceiveNoticeInfoByItemDetailId(wmsExamineWork.getReceiveItemDetailId());
        WmsReceiveItemDetail receiveItemDetail = receiveNoticeInfo.getWmsReceiveItemDetail();
        WmsReceiveNotice receiveNotice = receiveNoticeInfo.getWmsReceiveNotice();
        WmsReceiveNoticeDetail receiveNoticeDetail = receiveNoticeInfo.getWmsReceiveNoticeDetail();
        WmsExamineWork examineWork = wmsExamineWorkMapper.selectWmsExamineWorkByReceiveItemDetailId(wmsExamineWork.getReceiveItemDetailId());
        if (null == examineWork) {
            return AjaxResult.error("上架失败，未找到对应的质检通知单");
        }
        if (examineWork.getOperateStatus().equals(ExamineWorkNoticeStatus.ON_THE_SHELF.getCode())) {
            return AjaxResult.error("该货物已经上架，不能重复上架");
        }
        if (null == receiveNotice || null == receiveNoticeDetail || null == receiveItemDetail) {
            return AjaxResult.error("上架失败，未找到对应的入库通知单或者入库通知单明细或者入库通知单货物明细");
        }
        if (receiveItemDetail.getDepositQuantity().compareTo(BigDecimal.ZERO) > 0) {
            return AjaxResult.error("该货物已经上架，不能重复上架");
        }
        // 判断货物类型
        if (receiveNoticeDetail.getItemType().equals(ItemType.INBOUND_ACCEPTANCE.getCode())) {
            // 进料验收上架 需要更新质检通知单的状态 防止重复上架
            examineWork.setOperateStatus(ExamineWorkNoticeStatus.ON_THE_SHELF.getCode());
            examineWork.setUpdateBy(SecurityUtils.getUserId());
            examineWork.setUpdateTime(DateUtils.getNowDate());
            wmsExamineWorkMapper.updateWmsExamineWork(examineWork);
        }

        // 更新入库货物明细
        receiveItemDetail.setWorkStatus(ReceiveItemDetailWorkStatus.ON_THE_SHELF.getCode());
        receiveItemDetail.setUpdateTime(DateUtils.getNowDate());
        receiveItemDetail.setUpdateBy(SecurityUtils.getUserId());
        wmsReceiveItemDetailMapper.updateWmsReceiveItemDetail(receiveItemDetail);

        // 生成上架通知
        String code = codeService.generateShelfWorkCode();
        WmsWorkNotice wmsWorkNotice = new WmsWorkNotice();
        wmsWorkNotice.setId(code);
        wmsWorkNotice.setNoticeId(receiveNotice.getId());
        wmsWorkNotice.setNoticeItemDetailId(receiveItemDetail.getId());
        wmsWorkNotice.setType(WorkType.SHELF.getCode());
        wmsWorkNotice.setStatus(ShelfWorkNoticeStatus.NORMAL.getCode());
        wmsWorkNotice.setCompanyId(receiveNotice.getCompanyId());
        wmsWorkNotice.setReceiveSendType(receiveNotice.getReceiveSendType());
        wmsWorkNotice.setCreateBy(SecurityUtils.getUserId());
        wmsWorkNotice.setCreateTime(DateUtils.getNowDate());
        wmsWorkNoticeMapper.insertWmsWorkNotice(wmsWorkNotice);

        // 生成上架通知明细
        WmsWorkNoticeDetail wmsWorkNoticeDetail = new WmsWorkNoticeDetail();
        wmsWorkNoticeDetail.setId(IdUtils.fastSimpleUUID());
        wmsWorkNoticeDetail.setWorkNoticeId(code);
        wmsWorkNoticeDetail.setNoticeDetailId(receiveNoticeDetail.getId());
        wmsWorkNoticeDetail.setNoticeItemDetailId(receiveItemDetail.getId());
        wmsWorkNoticeDetail.setItemCode(receiveItemDetail.getItemCode());

        // 通知数量等于合格数量
        wmsWorkNoticeDetail.setQuantity(wmsExamineWork.getPassQuantity());

        // 质检扣除数量 = 收货数量 - 合格数量
        wmsWorkNoticeDetail.setExamineLessNum(wmsExamineWork.getAllQuantity().subtract(wmsExamineWork.getPassQuantity()));
        wmsWorkNoticeDetail.setAllotQuantity(BigDecimal.ZERO);
        wmsWorkNoticeDetail.setBatchNo(receiveItemDetail.getBatchNo());
        wmsWorkNoticeDetail.setSourceLocationId(receiveNoticeDetail.getLocationId());
        wmsWorkNoticeDetail.setCreateBy(SecurityUtils.getUserId());
        wmsWorkNoticeDetail.setCreateTime(DateUtils.getNowDate());
        wmsWorkNoticeDetailMapper.insertWmsWorkNoticeDetail(wmsWorkNoticeDetail);

        receiveNoticeInfo.setWorkNoticeId(code);
        // 记录入库信息
        List<BigDecimal> priceInfo = storageRecordManager.saveInRecord(receiveNoticeInfo);
        // 保存库存信息
        storageManager.storage(receiveNoticeInfo, priceInfo);
        return AjaxResult.success();
    }

    /**
     * 取消收货
     *
     * @param wmsReceiveItemDetail wms收到项目细节
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult cancel(WmsReceiveItemDetail wmsReceiveItemDetail) {
        WmsReceiveNoticeDetail receiveNoticeDetail = wmsReceiveNoticeDetailMapper.selectWmsReceiveNoticeDetailById(wmsReceiveItemDetail.getReceiveNoticeDetailId());
        if (null == receiveNoticeDetail) {
            return AjaxResult.error("收货通知单明细不存在");
        }

        // 删除收货明细
        int affectRows = wmsReceiveItemDetailMapper.deleteWmsReceiveItemDetailById(wmsReceiveItemDetail.getId());
        if (affectRows <= 0) {
            return AjaxResult.error("取消失败, 请勿重复取消");
        }
        receiveNoticeDetail.setReceiveQuantity(receiveNoticeDetail.getReceiveQuantity().subtract(wmsReceiveItemDetail.getQuantity()));
        receiveNoticeDetail.setLocationId("");
        receiveNoticeDetail.setUpdateBy(SecurityUtils.getUserId());
        receiveNoticeDetail.setUpdateTime(DateUtils.getNowDate());
        receiveNoticeDetail.setStatus(ReceiptStatus.RECEIVING.getCode());
        wmsReceiveNoticeDetailMapper.updateWmsReceiveNoticeDetail(receiveNoticeDetail);

        return AjaxResult.success();
    }


    /**
     * 取消全部
     *
     * @param wmsReceiveItemDetail wms收到项目细节
     * @return int
     */
    @Override
    public AjaxResult cancelAll(WmsReceiveItemDetail wmsReceiveItemDetail) {
        List<WmsReceiveItemDetail> wmsReceiveItemDetails = wmsReceiveItemDetailMapper.selectWmsReceiveItemDetailByReceiveNoticeId(wmsReceiveItemDetail.getReceiveNoticeId());
        if (wmsReceiveItemDetails.isEmpty()) {
            return AjaxResult.error("没有分配货物, 无法取消");
        }
        List<WmsReceiveItemDetail> itemDetails = wmsReceiveItemDetails.stream().filter(item -> item.getWorkStatus().equals(ReceiveItemDetailWorkStatus.WAIT_WORK.getCode())).collect(Collectors.toList());
        if (itemDetails.isEmpty()) {
            return AjaxResult.error("没有可以取消的分配记录");
        }
        itemDetails.forEach(this::cancel);
        return AjaxResult.success();
    }

    /**
     * 质检
     *
     * @param wmsReceiveItemDetail wms收到项目细节
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult qualityCheck(WmsReceiveItemDetail wmsReceiveItemDetail) {
        WmsReceiveItemDetail receiveItemDetail = wmsReceiveItemDetailMapper.selectWmsReceiveItemDetailById(wmsReceiveItemDetail.getId());
        if (null == receiveItemDetail) {
            return AjaxResult.error("收货明细不存在");
        }
        if (!receiveItemDetail.getWorkStatus().equals(ReceiveItemDetailWorkStatus.WAIT_WORK.getCode())) {
            return AjaxResult.error("收货明细状态不正确");
        }
        WmsExamineWork examineWork = wmsExamineWorkMapper.selectWmsExamineWorkByReceiveItemDetailId(wmsReceiveItemDetail.getId());
        if (null != examineWork) {
            return AjaxResult.error("请勿重复质检");
        }

        // 生成质检通知
        WmsExamineWork wmsExamineWork = new WmsExamineWork();
        wmsExamineWork.setId(codeService.generateExamineWorkCode());
        wmsExamineWork.setCompanyId(ServletUtils.getCompanyId());
        wmsExamineWork.setReceiveNoticeId(wmsReceiveItemDetail.getReceiveNoticeId());
        wmsExamineWork.setNoticeDetailId(wmsReceiveItemDetail.getReceiveNoticeDetailId());
        wmsExamineWork.setReceiveItemDetailId(wmsReceiveItemDetail.getId());
        wmsExamineWork.setStatus(ReciveItemStatus.WAITING_FOR_INSPECTION.getCode());
        wmsExamineWork.setOperateStatus(ExamineWorkNoticeStatus.UNNOTIFIED.getCode());
        wmsExamineWork.setBatchNo(wmsReceiveItemDetail.getBatchNo());
        wmsExamineWork.setItemCode(wmsReceiveItemDetail.getItemCode());
        wmsExamineWork.setAllQuantity(wmsReceiveItemDetail.getQuantity());
        wmsExamineWork.setCompanyId(wmsExamineWork.getCompanyId());
        wmsExamineWork.setCreateBy(SecurityUtils.getUserId());
        wmsExamineWork.setCreateTime(DateUtils.getNowDate());
        wmsExamineWorkMapper.insertWmsExamineWork(wmsExamineWork);

        // 更新收货明细状态
        wmsReceiveItemDetail.setStatus(ReciveItemStatus.INSPECTION_IN_PROGRESS.getCode());
        wmsReceiveItemDetail.setWorkStatus(ReceiveItemDetailWorkStatus.QUALITY_INSPECTION.getCode());
        wmsReceiveItemDetail.setUpdateBy(SecurityUtils.getUserId());
        wmsReceiveItemDetail.setUpdateTime(DateUtils.getNowDate());
        wmsReceiveItemDetailMapper.updateWmsReceiveItemDetail(wmsReceiveItemDetail);
        return AjaxResult.success();
    }

    /**
     * 批量上架
     *
     * @param receiveItemDto
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchShelves(WmsReceiveItemDetail receiveItemDto) throws Exception {

        WmsReceiveNotice wmsReceiveNotice = wmsReceiveNoticeMapper.selectWmsReceiveNoticeById(receiveItemDto.getReceiveNoticeId());
        if (null == wmsReceiveNotice) {
            throw new Exception("收货通知单不存在");
        }
        if (!wmsReceiveNotice.getStatus().equals(ReceiptStatus.RECEIVING.getCode())) {
            throw new Exception("收货通知单状态不正确");
        }

        // 生成上架通知
        String code = codeService.generateShelfWorkCode();
        WmsWorkNotice wmsWorkNotice = new WmsWorkNotice();
        wmsWorkNotice.setId(code);
        wmsWorkNotice.setNoticeId(wmsReceiveNotice.getId());
        wmsWorkNotice.setType(WorkType.SHELF.getCode());
        wmsWorkNotice.setStatus(ShelfWorkNoticeStatus.NORMAL.getCode());
        wmsWorkNotice.setCompanyId(wmsReceiveNotice.getCompanyId());
        wmsWorkNotice.setReceiveSendType(wmsReceiveNotice.getReceiveSendType());
        wmsWorkNotice.setCreateBy(SecurityUtils.getUserId());
        wmsWorkNotice.setCreateTime(DateUtils.getNowDate());
        wmsWorkNoticeMapper.insertWmsWorkNotice(wmsWorkNotice);

        for (String id : receiveItemDto.getIds()) {
            ReceiveNoticeInfo receiveNoticeInfo = receiveNoticeManager.getReceiveNoticeInfoByItemDetailId(id);
            WmsReceiveItemDetail wmsReceiveItemDetail = receiveNoticeInfo.getWmsReceiveItemDetail();
            WmsReceiveNoticeDetail wmsReceiveNoticeDetail = receiveNoticeInfo.getWmsReceiveNoticeDetail();
            if (wmsReceiveItemDetail.getWorkStatus().equals(ReceiveItemDetailWorkStatus.ON_THE_SHELF.getCode())) {
                throw new Exception("收货明细已上架, 请勿重复操作");
            }
            // 上架数量
            BigDecimal shelvesQuantity;

            // 如果是采购报废
            if (ScrapSpecialFlag.SCRAP.getCode().equals(wmsReceiveNotice.getScrapSpecialFlag()) || ScrapSpecialFlag.SPECIAL.getCode().equals(wmsReceiveNotice.getScrapSpecialFlag())) {
                if (wmsReceiveItemDetail.getFailQuantity().compareTo(BigDecimal.ZERO) <= 0) {
                    throw new BusinessException("报废数量必须大于0，才能上架");
                }
                shelvesQuantity = wmsReceiveItemDetail.getFailQuantity();
            } else {
                // 合格数量等于 0时，不能产生上架作业
                if (wmsReceiveItemDetail.getPassQuantity().compareTo(BigDecimal.ZERO) <= 0) {
                    throw new BusinessException("合格数量必须大于0，才能上架");
                }
                shelvesQuantity = wmsReceiveItemDetail.getPassQuantity();
            }

            // 保存库存信息时 关联ID 为上架通知ID
            receiveNoticeInfo.setWorkNoticeId(code);
            // 判断物料类型
            if (wmsReceiveNoticeDetail.getItemType().equals(ItemType.INBOUND_ACCEPTANCE.getCode())) {
                // 判断质检状态
                if (wmsReceiveItemDetail.getStatus().equals(ReciveItemStatus.WAITING_FOR_INSPECTION.getCode()) || wmsReceiveItemDetail.getStatus().equals(ReciveItemStatus.INSPECTION_IN_PROGRESS.getCode())) {
                    throw new Exception("物料未质检");
                }
                // 进料验收上架 需要更新质检通知单的状态 防止重复上架
                WmsExamineWork examineWork = wmsExamineWorkMapper.selectWmsExamineWorkByReceiveItemDetailId(wmsReceiveItemDetail.getId());
                examineWork.setOperateStatus(ExamineWorkNoticeStatus.ON_THE_SHELF.getCode());
                examineWork.setUpdateBy(SecurityUtils.getUserId());
                examineWork.setUpdateTime(DateUtils.getNowDate());
                wmsExamineWorkMapper.updateWmsExamineWork(examineWork);
            }
            // 更新入库货物明细
            wmsReceiveItemDetail.setWorkStatus(ReceiveItemDetailWorkStatus.ON_THE_SHELF.getCode());
            wmsReceiveItemDetail.setUpdateTime(DateUtils.getNowDate());
            wmsReceiveItemDetail.setUpdateBy(SecurityUtils.getUserId());
            wmsReceiveItemDetailMapper.updateWmsReceiveItemDetail(wmsReceiveItemDetail);

            // 生成上架通知明细
            WmsWorkNoticeDetail wmsWorkNoticeDetail = new WmsWorkNoticeDetail();
            wmsWorkNoticeDetail.setId(IdUtils.fastSimpleUUID());
            wmsWorkNoticeDetail.setWorkNoticeId(code);
            wmsWorkNoticeDetail.setNoticeDetailId(wmsReceiveNoticeDetail.getId());
            wmsWorkNoticeDetail.setNoticeItemDetailId(wmsReceiveItemDetail.getId());
            wmsWorkNoticeDetail.setItemCode(wmsReceiveItemDetail.getItemCode());
            // 通知数量等于上架数量
            wmsWorkNoticeDetail.setQuantity(shelvesQuantity);

            wmsWorkNoticeDetail.setAllotQuantity(BigDecimal.ZERO);
            wmsWorkNoticeDetail.setBatchNo(wmsReceiveItemDetail.getBatchNo());
            wmsWorkNoticeDetail.setSourceLocationId(wmsReceiveNoticeDetail.getLocationId());
            wmsWorkNoticeDetail.setCreateBy(SecurityUtils.getUserId());
            wmsWorkNoticeDetail.setCreateTime(DateUtils.getNowDate());
            wmsWorkNoticeDetailMapper.insertWmsWorkNoticeDetail(wmsWorkNoticeDetail);

            // 记录入库信息
            List<BigDecimal> priceInfo = storageRecordManager.saveInRecord(receiveNoticeInfo);

            // 产生上架作业时 保存库存信息
            storageManager.storage(receiveNoticeInfo, priceInfo);
        }


        return Constants.SUCCESS_CODE;
    }


}
