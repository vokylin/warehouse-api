package com.ruoyi.project.warehouse.service.impl;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.enums.BillType;
import com.ruoyi.common.enums.BreakageDocStatus;
import com.ruoyi.common.enums.RelateType;
import com.ruoyi.common.enums.StorageInOutRecordType;
import com.ruoyi.common.exception.business.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.component.DingTalkManager;
import com.ruoyi.project.system.domain.SysDept;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.domain.Warehouse;
import com.ruoyi.project.system.service.ISysDeptService;
import com.ruoyi.project.system.service.ISysUserService;
import com.ruoyi.project.system.service.IWarehouseService;
import com.ruoyi.project.warehouse.domain.*;
import com.ruoyi.project.warehouse.mapper.WmsBreakageDocMapper;
import com.ruoyi.project.warehouse.mapper.WmsStorageInOutDetailMapper;
import com.ruoyi.project.warehouse.mapper.WmsStorageInOutRecordMapper;
import com.ruoyi.project.warehouse.service.IWmsBreakageDocDetailService;
import com.ruoyi.project.warehouse.service.IWmsBreakageDocService;
import com.ruoyi.project.warehouse.service.IWmsItemStorageDetailService;
import com.ruoyi.project.warehouse.service.IWmsItemStorageService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

/**
 * 报损单Service业务层处理
 *
 * @author Rem
 * @date 2023-05-20
 */
@Service
public class WmsBreakageDocServiceImpl implements IWmsBreakageDocService {
    @Autowired
    private WmsBreakageDocMapper wmsBreakageDocMapper;

    @Autowired
    private IWmsBreakageDocDetailService wmsBreakageDocDetailService;

    @Autowired
    private DingTalkManager dingTalkManager;

    @Autowired
    private IWmsItemStorageDetailService itemStorageDetailService;

    @Autowired
    private IWmsItemStorageService itemStorageService;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private WmsStorageInOutRecordMapper wmsStorageInOutRecordMapper;

    @Autowired
    private WmsStorageInOutDetailMapper wmsStorageInOutDetailMapper;

    @Autowired
    private IWarehouseService warehouseService;

    /**
     * 查询报损单
     *
     * @param id 报损单主键
     * @return 报损单
     */
    @Override
    public WmsBreakageDoc selectWmsBreakageDocById(String id) {
        return wmsBreakageDocMapper.selectWmsBreakageDocById(id);
    }

    /**
     * 查询报损单列表
     *
     * @param wmsBreakageDoc 报损单
     * @return 报损单
     */
    @Override
    public List<WmsBreakageDoc> selectWmsBreakageDocList(WmsBreakageDoc wmsBreakageDoc) {
        return wmsBreakageDocMapper.selectWmsBreakageDocList(wmsBreakageDoc);
    }

    /**
     * 新增报损单
     *
     * @param wmsBreakageDoc 报损单
     * @return 结果
     */
    @Override
    public int insertWmsBreakageDoc(WmsBreakageDoc wmsBreakageDoc) {
        wmsBreakageDoc.setCreateTime(DateUtils.getNowDate());
        wmsBreakageDoc.setCompanyId(ServletUtils.getCompanyId());
        Long userId = SecurityUtils.getUserId();
        wmsBreakageDoc.setCreateBy(userId);
        SysUser sysUser = userService.selectUserById(userId);
        if (null == sysUser) {
            throw new BusinessException("用户不存在");
        }
        wmsBreakageDoc.setApplicant(sysUser.getNickName());
        wmsBreakageDoc.setApplicantId(sysUser.getUserId());
        SysDept sysDept = deptService.selectDeptById(sysUser.getDeptId());
        if (null == sysDept) {
            throw new BusinessException("部门不存在");
        }
        wmsBreakageDoc.setOfficeId(sysDept.getDeptId());
        wmsBreakageDoc.setOfficeName(sysDept.getDeptName());
        return wmsBreakageDocMapper.insertWmsBreakageDoc(wmsBreakageDoc);
    }

    /**
     * 修改报损单
     *
     * @param wmsBreakageDoc 报损单
     * @return 结果
     */
    @Override
    public int updateWmsBreakageDoc(WmsBreakageDoc wmsBreakageDoc) {
        return wmsBreakageDocMapper.updateWmsBreakageDoc(wmsBreakageDoc);
    }

    /**
     * 批量删除报损单
     *
     * @param ids 需要删除的报损单主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult deleteWmsBreakageDocByIds(String[] ids) throws Exception {
        for (String id : ids) {
            // 回退库存
            BreakageDocVO breakageDocVO = this.selectById(id);
            if (breakageDocVO == null) {
                throw new BusinessException("报损单不存在");
            }
            List<WmsBreakageDocDetail> breakageDocItemVOList = breakageDocVO.getWmsBreakageDocDetailList();
            if (null == breakageDocItemVOList) {
                throw new BusinessException("报损单明细不存在");
            }
            for (WmsBreakageDocDetail breakageDocDetail : breakageDocItemVOList) {
                wmsBreakageDocDetailService.cancelAllotItemStorageAndDelete(breakageDocDetail);
            }
        }
        wmsBreakageDocMapper.deleteWmsBreakageDocByIds(ids);
        return AjaxResult.success();
    }


    @Override
    public BreakageDocVO selectById(String id) {
        return wmsBreakageDocMapper.selectById(id);
    }

    /**
     * 查询今日报损单数量
     *
     * @return int
     */
    @Override
    public int selectCountByToday() {
        return wmsBreakageDocMapper.selectCountByToday();
    }

    /**
     * 提交报损单
     *
     * @param id id
     * @return {@link AjaxResult}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult submitWmsBreakageDocById(String id) throws Exception {
        BreakageDocVO breakageDocVO = this.selectById(id);
        if (breakageDocVO == null) {
            return AjaxResult.error("报损单不存在");
        }
        if (!breakageDocVO.getStatus().equals(BreakageDocStatus.SUBMIT.getCode())) {
            return AjaxResult.error("报损单状态不正确");
        }

        // 提交钉钉审批
        String instanceId = dingTalkManager.submitApproval(breakageDocVO);
        if (StringUtils.isBlank(instanceId)) {
            return AjaxResult.error("提交审批失败");
        }

        WmsBreakageDoc wmsBreakageDoc = new WmsBreakageDoc();
        wmsBreakageDoc.setId(id);
        wmsBreakageDoc.setStatus(BreakageDocStatus.AUDIT.getCode());
        wmsBreakageDoc.setUpdateBy(SecurityUtils.getUserId());
        wmsBreakageDoc.setUpdateTime(DateUtils.getNowDate());
        this.updateWmsBreakageDoc(wmsBreakageDoc);

        return AjaxResult.success();
    }

    @Override
    public void approved(String id) {
        // 审批通过
        WmsBreakageDoc wmsBreakageDoc = new WmsBreakageDoc();
        wmsBreakageDoc.setId(id);
        wmsBreakageDoc.setStatus(BreakageDocStatus.PASS.getCode());
        wmsBreakageDoc.setUpdateBy(Constants.SYSTEM_USER_ID);
        wmsBreakageDoc.setUpdateTime(DateUtils.getNowDate());
        this.updateWmsBreakageDoc(wmsBreakageDoc);

        BreakageDocVO breakageDocVO = this.selectById(id);
        if (breakageDocVO == null) {
            throw new BusinessException("报损单不存在");
        }
        List<WmsBreakageDocDetail> breakageDocItemVOList = breakageDocVO.getWmsBreakageDocDetailList();
        if (null == breakageDocItemVOList || breakageDocItemVOList.size() == 0) {
            throw new BusinessException("报损单明细为空");
        }

        // 删除关联的库存
        WmsItemStorageDetail storageDetail = new WmsItemStorageDetail();
        storageDetail.setRelateId(id);
        storageDetail.setRelateType(RelateType.LOSS_IN_PROGRESS.getCode());
        List<WmsItemStorageDetail> wmsItemStorageDetails = itemStorageDetailService.selectWmsItemStorageDetailList(storageDetail);
        if (null == wmsItemStorageDetails || wmsItemStorageDetails.size() == 0) {
            throw new BusinessException("报损单关联的库存为空");
        }

        wmsItemStorageDetails.forEach(item -> {

            // 查询物料的所有库存
            WmsItemStorage itemStorage = new WmsItemStorage();
            itemStorage.setCompanyId(breakageDocVO.getCompanyId());
            itemStorage.setItemCode(item.getItemCode());
            List<WmsItemStorage> wmsItemStorages = itemStorageService.selectWmsItemStorageByCompany(itemStorage);
            // 总库存数
            BigDecimal beforeRollingQuantity = wmsItemStorages.stream().map(WmsItemStorage::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);

            // 查询物料批次的库存数量
            WmsItemStorage itemInfo = new WmsItemStorage();
            itemInfo.setItemCode(item.getItemCode());
            itemInfo.setBatchNo(item.getBatchNo());
            itemInfo.setCompanyId(breakageDocVO.getCompanyId());
            BigDecimal itemBatchNoQuantity = itemStorageService.getItemStorageBatchNoQuantity(itemInfo);

            // 查询当前物料的库存汇总
            WmsItemStorage wmsItemStorage = itemStorageService.selectWmsItemStorageById(item.getStorageId());
            if (null == wmsItemStorage) {
                throw new BusinessException("库存汇总不存在");
            }

            // 报损数量
            BigDecimal quantity = item.getQuantity();
            // 滚算前库存金额
            BigDecimal beforeStoragePrice = wmsItemStorage.getStoragePrice();
            // 滚算前单价
            BigDecimal beforeRollingPrice = wmsItemStorage.getActualPrice() == null ? BigDecimal.ZERO : wmsItemStorage.getActualPrice();
            // 报损库存金额
            BigDecimal outPrice = beforeRollingPrice.multiply(quantity);
            // 报损后库存金额
            BigDecimal surplusAmount = beforeStoragePrice.subtract(outPrice);
            // 报损后库存数量
            BigDecimal surplusQuantity = itemBatchNoQuantity.subtract(quantity);
            wmsItemStorage.setStoragePrice(surplusAmount);
            // 库存金额不变，滚算出最新单价
            BigDecimal afterRollingPrice = surplusAmount.divide(beforeRollingPrice.subtract(quantity), 6, RoundingMode.HALF_UP);

            // 出库记录
            WmsStorageInOutRecord wmsStorageInOutRecord = new WmsStorageInOutRecord();
            wmsStorageInOutRecord.setId(IdUtils.fastSimpleUUID());
            wmsStorageInOutRecord.setItemCode(item.getItemCode());
            wmsStorageInOutRecord.setItemName(item.getItemName());
            wmsStorageInOutRecord.setUnit(item.getUnit());
            wmsStorageInOutRecord.setSpec(item.getSpec());
            wmsStorageInOutRecord.setCompanyId(breakageDocVO.getCompanyId());
            wmsStorageInOutRecord.setBatchNo(item.getBatchNo());
            wmsStorageInOutRecord.setQuantity(quantity);
            wmsStorageInOutRecord.setNoticeId(breakageDocVO.getId());
            wmsStorageInOutRecord.setBillType(BillType.SCRAP.getCode());
            wmsStorageInOutRecord.setReceiveSendType(breakageDocVO.getReceiveSendType());
            wmsStorageInOutRecord.setType(StorageInOutRecordType.OUT.getCode());

            // 出库前单价、库存金额、库存数量
            wmsStorageInOutRecord.setBeforeRollingPrice(beforeRollingPrice);
            wmsStorageInOutRecord.setBeforeRollingTotalPrice(beforeStoragePrice);
            wmsStorageInOutRecord.setBeforeRollingQuantity(beforeRollingQuantity);

            wmsStorageInOutRecord.setOccQuantity(quantity);
            wmsStorageInOutRecord.setOccTotalPrice(BigDecimal.ZERO);
            wmsStorageInOutRecord.setOccPrice(BigDecimal.ZERO);

            wmsStorageInOutRecord.setOpeningQuantity(itemBatchNoQuantity);
            wmsStorageInOutRecord.setOpeningAmount(itemBatchNoQuantity.multiply(wmsItemStorage.getActualPrice()));

            // 判断即将删除的库存是否等于汇总数量, 如果等于则删除汇总 , 否则更新汇总数量
            if (quantity.compareTo(wmsItemStorage.getQuantity()) == 0) {
                // 删除汇总
                itemStorageService.deleteWmsItemStorageById(item.getStorageId());
                // 删除库存明细
                itemStorageDetailService.deleteWmsItemStorageDetailById(item.getId());

                // 出库金额就是库存的金额，重新计算出库的单价
                wmsStorageInOutRecord.setOccTotalPrice(beforeStoragePrice);
                wmsStorageInOutRecord.setOccPrice(beforeStoragePrice.divide(quantity, 6, RoundingMode.HALF_UP));

                wmsStorageInOutRecord.setAfterRollingPrice(BigDecimal.ZERO);
                wmsStorageInOutRecord.setAfterRollingQuantity(BigDecimal.ZERO);
                wmsStorageInOutRecord.setAfterRollingTotalPrice(BigDecimal.ZERO);

                wmsStorageInOutRecord.setOpeningQuantity(quantity);
                wmsStorageInOutRecord.setOpeningAmount(beforeStoragePrice);

                wmsStorageInOutRecord.setSurplusQuantity(BigDecimal.ZERO);
                wmsStorageInOutRecord.setSurplusAmount(BigDecimal.ZERO);

                // 只是报损当前仓库的所有库存
                if (beforeRollingQuantity.compareTo(quantity) != 0) {
                    // 更新该物料的的所有库存汇总的实时单价 和 库存金额
                    WmsItemStorage storage = new WmsItemStorage();
                    storage.setItemCode(wmsItemStorage.getItemCode());
                    storage.setActualPrice(afterRollingPrice);
                    storage.setCompanyId(wmsItemStorage.getCompanyId());
                    storage.setStoragePrice(surplusAmount);
                    itemStorageService.updateWmsItemStorageAllPrice(wmsItemStorage);

                    wmsStorageInOutRecord.setSurplusQuantity(beforeRollingQuantity.subtract(quantity));
                    wmsStorageInOutRecord.setSurplusAmount(surplusAmount);
                }

            } else {
                // 更新汇总数量
                wmsItemStorage.setQuantity(wmsItemStorage.getQuantity().subtract(quantity));
                itemStorageService.updateWmsItemStorage(wmsItemStorage);

                // 删除库存明细
                itemStorageDetailService.deleteWmsItemStorageDetailById(item.getId());

                // 更新该物料的的所有库存汇总的实时单价 和 库存金额
                WmsItemStorage storage = new WmsItemStorage();
                storage.setItemCode(wmsItemStorage.getItemCode());
                storage.setActualPrice(afterRollingPrice);
                storage.setCompanyId(wmsItemStorage.getCompanyId());
                storage.setStoragePrice(surplusAmount);
                itemStorageService.updateWmsItemStorageAllPrice(wmsItemStorage);

                wmsStorageInOutRecord.setOccPrice(afterRollingPrice);
                wmsStorageInOutRecord.setOccTotalPrice(outPrice);
                wmsStorageInOutRecord.setAfterRollingPrice(afterRollingPrice);
                wmsStorageInOutRecord.setAfterRollingQuantity(beforeRollingQuantity.subtract(quantity));
                wmsStorageInOutRecord.setAfterRollingTotalPrice(surplusAmount);
                wmsStorageInOutRecord.setOpeningAmount(itemBatchNoQuantity.multiply(beforeRollingPrice));

                wmsStorageInOutRecord.setSurplusQuantity(surplusQuantity);
                wmsStorageInOutRecord.setSurplusAmount(surplusAmount);

            }

            Date nowDate = DateUtils.getNowDate();
            wmsStorageInOutRecord.setOccTime(nowDate);
            wmsStorageInOutRecord.setCreateBy(Constants.SYSTEM_USER_ID);
            wmsStorageInOutRecord.setCreateTime(nowDate);
            wmsStorageInOutRecordMapper.insertWmsStorageInOutRecord(wmsStorageInOutRecord);

            // 查询仓库信息
            Warehouse warehouse = warehouseService.selectWarehouseById(item.getWarehouseId());

            // 记录出库明细
            WmsStorageInOutDetail wmsStorageInOutDetail = new WmsStorageInOutDetail();
            wmsStorageInOutDetail.setId(IdUtils.fastSimpleUUID());
            wmsStorageInOutDetail.setRecodeId(wmsStorageInOutRecord.getId());
            wmsStorageInOutDetail.setWarehouseCode(warehouse.getCode());
            wmsStorageInOutDetail.setWarehouseId(warehouse.getId());
            wmsStorageInOutDetail.setLocationId(item.getLocationId());
            wmsStorageInOutDetail.setQuantity(quantity);
            wmsStorageInOutDetail.setItemCode(item.getItemCode());
            wmsStorageInOutDetail.setCreateBy(Constants.SYSTEM_USER_ID);
            wmsStorageInOutDetail.setCreateTime(nowDate);
            wmsStorageInOutDetailMapper.insertWmsStorageInOutDetail(wmsStorageInOutDetail);
        });

    }

    @Override
    public void rejected(String id) throws Exception {
        // 审批驳回
        WmsBreakageDoc wmsBreakageDoc = new WmsBreakageDoc();
        wmsBreakageDoc.setId(id);
        wmsBreakageDoc.setStatus(BreakageDocStatus.REJECT.getCode());
        wmsBreakageDoc.setUpdateBy(Constants.SYSTEM_USER_ID);
        wmsBreakageDoc.setUpdateTime(DateUtils.getNowDate());
        this.updateWmsBreakageDoc(wmsBreakageDoc);

        // 回退库存
        BreakageDocVO breakageDocVO = this.selectById(id);
        if (breakageDocVO == null) {
            return;
        }
        List<WmsBreakageDocDetail> breakageDocItemVOList = breakageDocVO.getWmsBreakageDocDetailList();
        if (null == breakageDocItemVOList) {
            return;
        }
        for (WmsBreakageDocDetail breakageDocDetail : breakageDocItemVOList) {
            wmsBreakageDocDetailService.cancelAllotItemStorage(breakageDocDetail);
        }
    }

    @Override
    public void cancel(String id) throws Exception {
        // 审批取消
        WmsBreakageDoc wmsBreakageDoc = new WmsBreakageDoc();
        wmsBreakageDoc.setId(id);
        wmsBreakageDoc.setStatus(BreakageDocStatus.REJECT.getCode());
        wmsBreakageDoc.setUpdateBy(Constants.SYSTEM_USER_ID);
        wmsBreakageDoc.setUpdateTime(DateUtils.getNowDate());
        this.updateWmsBreakageDoc(wmsBreakageDoc);

        // 回退库存
        BreakageDocVO breakageDocVO = this.selectById(id);
        if (breakageDocVO == null) {
            return;
        }
        List<WmsBreakageDocDetail> breakageDocItemVOList = breakageDocVO.getWmsBreakageDocDetailList();
        if (null == breakageDocItemVOList) {
            return;
        }
        for (WmsBreakageDocDetail breakageDocDetail : breakageDocItemVOList) {
            wmsBreakageDocDetailService.cancelAllotItemStorage(breakageDocDetail);
        }
    }


    @Override
    public List<BreakageDocPrintInfo> getBreakageDocPrintInfo(String noticeId) {
        List<BreakageDocPrintInfo> breakageDocPrintInfos = wmsBreakageDocMapper.getBreakageDocPrintInfo(noticeId);
        if (CollectionUtils.isNotEmpty(breakageDocPrintInfos)) {
            breakageDocPrintInfos.forEach(item -> {
                int rowNum = wmsBreakageDocDetailService.generatePrintNumber(item);
                String printNumber = Constants.DISPOSAL_CODE_PREFIX + "-" + DateUtils.parseDateToStr(DateUtils.YYYYMM, item.getOccTime()) + "-" + String.format("%03d", rowNum);
                item.setPrintNumber(printNumber);
            });
        }
        return breakageDocPrintInfos;
    }


}
