package com.ruoyi.project.common.component;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.enums.*;
import com.ruoyi.common.exception.business.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.entity.*;
import com.ruoyi.project.system.domain.Customer;
import com.ruoyi.project.system.domain.SysDictData;
import com.ruoyi.project.system.domain.WmsDeliverySure;
import com.ruoyi.project.system.domain.WmsDeliverySureDetail;
import com.ruoyi.project.system.service.ICustomerService;
import com.ruoyi.project.system.service.ISysDictTypeService;
import com.ruoyi.project.system.service.IWmsDeliverySureDetailService;
import com.ruoyi.project.system.service.IWmsDeliverySureService;
import com.ruoyi.project.warehouse.domain.*;
import com.ruoyi.project.warehouse.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 收货通知服务
 *
 * @author REM
 * @date 2023/04/17
 */
@Slf4j
@Component
public class DeliveryNoticeManager {

    @Autowired
    private IWmsDeliveryNoticeService wmsDeliveryNoticeService;

    @Autowired
    private IWmsDeliveryNoticeDetailService wmsDeliveryNoticeDetailService;

    @Autowired
    private IWmsDeliverySureService wmsDeliverySureService;

    @Autowired
    private IWmsDeliverySureDetailService wmsDeliverySureDetailService;

    @Autowired
    private IWmsBillOfLoadingService wmsBillOfLoadingService;

    @Autowired
    private IWmsItemStorageDetailService wmsItemStorageDetailService;

    @Autowired
    private IWmsItemStorageService wmsItemStorageService;

    @Autowired
    private IWmsWorkNoticeService wmsWorkNoticeService;

    @Autowired
    private IWmsPickingWorkNoticeDetailService wmsPickingWorkNoticeDetailService;

    @Autowired
    private CodeService codeService;

    @Autowired
    private StorageManager storageManager;

    @Autowired
    private ReceiveNoticeManager receiveNoticeManager;

//    @Autowired
//    private ApiFeignService apiFeignService;

    @Autowired
    private ISysDictTypeService dictTypeService;

    @Autowired
    private IWmsStorageInOutDetailService wmsStorageInOutDetailService;

    @Autowired
    private IWmsStorageInOutRecordService wmsStorageInOutRecordService;

    @Autowired
    private IWmsExamineWorkService wmsExamineWorkService;

    @Autowired
    private ICustomerService customerService;


    /**
     * 收货确认
     *
     * @param deliveryConfirmInfo 交付确认信息
     * @return {@link AjaxResult}
     */
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult deliveryConfirm(DeliveryConfirmInfo deliveryConfirmInfo) throws Exception {

        // 查询出库通知
        WmsDeliveryNotice wmsDeliveryNotice = wmsDeliveryNoticeService.selectWmsDeliveryNoticeById(deliveryConfirmInfo.getDeliveryNoticeId());

        // 判断出库通知结果
        Integer deliveryResult = deliveryConfirmInfo.getDeliveryResult();
        DeliveryResult deliveryResultEnum = DeliveryResult.getDeliveryResult(deliveryResult);
        if (deliveryResultEnum == null) {
            return AjaxResult.error("发运结果不正确");
        }
        switch (deliveryResultEnum) {
            case AGREE:
                return agree(deliveryConfirmInfo, wmsDeliveryNotice);
            case REJECT:
                return reject(wmsDeliveryNotice);
            default:
                return AjaxResult.error("发运结果不正确");
        }
    }

    /**
     * 同意发运
     */
    private AjaxResult agree(DeliveryConfirmInfo deliveryConfirmInfo, WmsDeliveryNotice wmsDeliveryNotice) throws Exception {

        // 判断是否可以发运
        AjaxResult ajaxResult = checkDeliveryNotice(wmsDeliveryNotice);
        if (ajaxResult.isError()) {
            return ajaxResult;
        }

        // 保存出库确认记录
        saveDeliverySureData(deliveryConfirmInfo);

        // 获取需要产生发运通知的单据类型
        List<SysDictData> sysDictData = dictTypeService.selectDictDataByType(Constants.GENERATE_BILL_OF_LOADING_BUSINESS_TYPE);
        if (sysDictData.isEmpty()) {
            return AjaxResult.error("请先配置发运通知单据类型");
        }
        List<String> list = sysDictData.stream().map(SysDictData::getDictValue).collect(Collectors.toList());
        String businessType = wmsDeliveryNotice.getBusinessType();

        // 需要产生发运通知
        if (list.contains(businessType)) {
            // 产生发运通知
            generateBillOfLoading(wmsDeliveryNotice);
        } else {
            // 更新出库通知明细
            List<WmsDeliveryNoticeDetail> wmsDeliveryNoticeDetails = wmsDeliveryNoticeDetailService.selectByNoticeId(deliveryConfirmInfo.getDeliveryNoticeId());
            List<BaseDetailDTO> detailDTOList = new ArrayList<>();
            wmsDeliveryNoticeDetails.forEach((wmsDeliveryNoticeDetail) -> {
                wmsDeliveryNoticeDetail.setDeliveryQuantity(wmsDeliveryNoticeDetail.getWorkFinishQuantity());
                wmsDeliveryNoticeDetail.setUpdateBy(SecurityUtils.getUserId());
                wmsDeliveryNoticeDetail.setUpdateTime(DateUtils.getNowDate());
                wmsDeliveryNoticeDetailService.updateWmsDeliveryNoticeDetail(wmsDeliveryNoticeDetail);
                // 提前构造通知企通链的内容，在仓库执行完之后，再通知企通链
                buildDeliveryBaseDetail(wmsDeliveryNoticeDetail, detailDTOList, wmsDeliveryNotice.getId());
            });

            // 更新出库通知状态
            WmsDeliveryNotice deliveryNotice = new WmsDeliveryNotice();
            deliveryNotice.setId(deliveryConfirmInfo.getDeliveryNoticeId());
            deliveryNotice.setStatus(DeliveryNoticeStatus.COMPLETE.getCode());
            deliveryNotice.setDeliveryEndTime(DateUtils.getNowDate());
            deliveryNotice.setUpdateBy(SecurityUtils.getUserId());
            deliveryNotice.setUpdateTime(DateUtils.getNowDate());
            wmsDeliveryNoticeService.updateWmsDeliveryNotice(deliveryNotice);

            //不需要产生发运通知  更新库存信息，记录出入库明细
            storageManager.outBound(deliveryConfirmInfo.getDeliveryNoticeId());

            // 通知其他系统
            deliveryNoticeFinish(deliveryConfirmInfo.getDeliveryNoticeId(), detailDTOList);
        }

        return AjaxResult.success();
    }


    private void buildDeliveryBaseDetail(WmsDeliveryNoticeDetail noticeDetail, List<BaseDetailDTO> detailDTOList, String deliveryNoticeId) {
        // 查询拣货作业明细
        List<WmsPickingWorkNoticeDetail> wmsPickingWorkNoticeDetails = wmsPickingWorkNoticeDetailService.selectDetailByDeliveryNoticeDetailId(noticeDetail.getId());
        if (wmsPickingWorkNoticeDetails.isEmpty()) {
            throw new BusinessException("拣货作业明细不存在");
        }
        Map<String, List<WmsPickingWorkNoticeDetail>> collect = wmsPickingWorkNoticeDetails.stream().collect(Collectors.groupingBy(WmsPickingWorkNoticeDetail::getBatchNo));
        collect.forEach((batchNo, lists) -> {
            WmsPickingWorkNoticeDetail pickingWorkNoticeDetail = lists.get(0);
            WmsItemStorageDetail selectDetail = new WmsItemStorageDetail();
            selectDetail.setBatchNo(pickingWorkNoticeDetail.getBatchNo());
            selectDetail.setItemCode(pickingWorkNoticeDetail.getItemCode());
            selectDetail.setLocationId(pickingWorkNoticeDetail.getSourceLocationId());
            selectDetail.setRelateType(RelateType.DELIVERY.getCode());
            selectDetail.setWorkStatus(WorkStatus.WORKING.getCode());
            selectDetail.setRelateId(deliveryNoticeId);
            WmsItemStorageDetail wmsItemStorageDetailInfo = wmsItemStorageDetailService.selectWmsItemStorageDetailByBatchNo(selectDetail);

            BaseDetailDTO baseDetailDTO = new BaseDetailDTO();
            baseDetailDTO.setItemCode(noticeDetail.getItemCode());
            baseDetailDTO.setQuantity(lists.stream().map(WmsPickingWorkNoticeDetail::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add));
            baseDetailDTO.setItemName(noticeDetail.getItemName());
            baseDetailDTO.setBatchNo(pickingWorkNoticeDetail.getBatchNo());
            baseDetailDTO.setItemUnit(noticeDetail.getItemUnit());
            baseDetailDTO.setSpec(noticeDetail.getItemSpec());
            baseDetailDTO.setPrice(wmsItemStorageDetailInfo.getActualPrice());
            baseDetailDTO.setExpireTime(wmsItemStorageDetailInfo.getExpireDate());
            baseDetailDTO.setProduceTime(wmsItemStorageDetailInfo.getProductDate());
            detailDTOList.add(baseDetailDTO);
        });
    }

    /**
     * 拒绝发运
     * <p>
     * 拒绝发运需要将库存中关联编号为：该出库通知ID、该出库通知下的拣货作业ID的库存，进行作业回退
     * 拒绝发运后，将出库通知的分配中（将分配物料回退）、拣货中、拣货完成的拣货任务完成数量变为0，状态变为‘取消’，
     * 并将这些拣货任务下的物料进行回退（若这些物料（库位、批号、物料编码一样）在库存中作业状态是‘正常’并且库存状态是‘合格’的库存，将物料数量回退叠加后删除原作业中的物料，
     * 若在库存中没有符合回退叠加的库存，则将该物料的关联状态改为：‘未关联’、作业状态改为：‘正常’）
     */
    private AjaxResult reject(WmsDeliveryNotice wmsDeliveryNotice) throws Exception {

        String deliveryNoticeId = wmsDeliveryNotice.getId();

        // 查询拣货作业通知明细
        List<WmsPickingWorkNoticeDetail> pickingWorkNoticeDetails = wmsPickingWorkNoticeDetailService.selectDetailByDeliveryNoticeId(deliveryNoticeId);

        // 退回拣货作业中的库存
        storageManager.batchCancelAllotByWorkNotice(pickingWorkNoticeDetails);

        // 更新拣货完成数量为0
        WmsPickingWorkNoticeDetail updatePickWorkDetail = new WmsPickingWorkNoticeDetail();
        updatePickWorkDetail.setWorkNoticeId(deliveryNoticeId);
        updatePickWorkDetail.setAllotQuantity(BigDecimal.ZERO);
        updatePickWorkDetail.setUpdateBy(SecurityUtils.getUserId());
        updatePickWorkDetail.setUpdateTime(DateUtils.getNowDate());
        wmsPickingWorkNoticeDetailService.updateWmsPickingWorkNoticeDetail(updatePickWorkDetail);

        // 更新拣货作业通知状态
        WmsWorkNotice wmsWorkNotice = new WmsWorkNotice();
        wmsWorkNotice.setNoticeId(deliveryNoticeId);
        wmsWorkNotice.setStatus(PickWorkNoticeStatus.CANCEL.getCode());
        wmsWorkNotice.setUpdateBy(SecurityUtils.getUserId());
        wmsWorkNotice.setUpdateTime(DateUtils.getNowDate());
        wmsWorkNoticeService.updatePickWorkNoticeStatus(wmsWorkNotice);

        // 查询出库通知明细
        List<WmsDeliveryNoticeDetail> wmsDeliveryNoticeDetails = wmsDeliveryNoticeDetailService.selectByNoticeId(deliveryNoticeId);
        // 退回出库通知中的 分配中 拣货完的库存
        storageManager.batchCancelAllotByDeliveryNotice(wmsDeliveryNoticeDetails);

        // 更新出库通知明细的数量
        WmsDeliveryNoticeDetail updateDetail = new WmsDeliveryNoticeDetail();
        updateDetail.setDeliveryNoticeId(deliveryNoticeId);
        updateDetail.setWorkFinishQuantity(BigDecimal.ZERO);
        updateDetail.setWorkQuantity(BigDecimal.ZERO);
        updateDetail.setAllotQuantity(BigDecimal.ZERO);
        updateDetail.setDeliveryQuantity(BigDecimal.ZERO);
        updateDetail.setUpdateBy(SecurityUtils.getUserId());
        updateDetail.setUpdateTime(DateUtils.getNowDate());
        wmsDeliveryNoticeDetailService.updateByNotice(updateDetail);

        // 更新出库通知状态
        wmsDeliveryNotice.setStatus(DeliveryNoticeStatus.REFUSE.getCode());
        wmsDeliveryNotice.setUpdateBy(SecurityUtils.getUserId());
        wmsDeliveryNotice.setUpdateTime(DateUtils.getNowDate());
        wmsDeliveryNotice.setDeliveryEndTime(DateUtils.getNowDate());
        wmsDeliveryNoticeService.updateWmsDeliveryNotice(wmsDeliveryNotice);

        // 通知其他系统
        invalidNotice(wmsDeliveryNotice);
        return AjaxResult.success();
    }


    /**
     * 保存出库确认信息
     *
     * @param deliveryConfirmInfo 交付确认信息
     * @param wmsDeliveryNotice   wms交货通知
     */
    private void saveDeliverySureData(DeliveryConfirmInfo deliveryConfirmInfo) {

        // 保存出库确认信息
        WmsDeliverySure wmsDeliverySure = new WmsDeliverySure();
        wmsDeliverySure.setId(IdUtils.fastSimpleUUID());
        wmsDeliverySure.setDeliveryNoticeId(deliveryConfirmInfo.getDeliveryNoticeId());
        wmsDeliverySure.setRemark(deliveryConfirmInfo.getRemark());
        wmsDeliverySure.setCreateBy(SecurityUtils.getUserId());
        wmsDeliverySure.setResult(deliveryConfirmInfo.getDeliveryResult());
        wmsDeliverySure.setCreateTime(DateUtils.getNowDate());
        wmsDeliverySureService.insertWmsDeliverySure(wmsDeliverySure);

        // 保存出库确认信息明细
        List<WmsDeliverySureDetail> wmsDeliverySureDetailList = deliveryConfirmInfo.getWmsDeliverySureDetailList();
        wmsDeliverySureDetailList.forEach(wmsDeliverySureDetail -> {
            WmsDeliverySureDetail deliverySureDetail = new WmsDeliverySureDetail();
            deliverySureDetail.setId(IdUtils.fastSimpleUUID());
            deliverySureDetail.setSureId(wmsDeliverySure.getId());
            deliverySureDetail.setCheckId(wmsDeliverySureDetail.getId());
            deliverySureDetail.setCheckItem(wmsDeliverySureDetail.getCheckItem());
            deliverySureDetail.setCheckRequirement(wmsDeliverySureDetail.getCheckRequirement());
            deliverySureDetail.setSort(wmsDeliverySureDetail.getSort());
            deliverySureDetail.setResult(wmsDeliverySureDetail.getResult());
            deliverySureDetail.setCreateBy(SecurityUtils.getUserId());
            deliverySureDetail.setCreateTime(DateUtils.getNowDate());
            wmsDeliverySureDetailService.insertWmsDeliverySureDetail(deliverySureDetail);
        });

    }

    public void generateBillOfLoading(WmsDeliveryNotice wmsDeliveryNotice) {
        List<WmsDeliveryNoticeDetail> wmsDeliveryNoticeDetails = wmsDeliveryNoticeDetailService.selectByNoticeId(wmsDeliveryNotice.getId());
        BigDecimal plannedQuantity = wmsDeliveryNoticeDetails.stream().map(WmsDeliveryNoticeDetail::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal actualQuantity = wmsDeliveryNoticeDetails.stream().map(WmsDeliveryNoticeDetail::getWorkFinishQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);


        // 生成发运通知
        WmsBillOfLoading wmsBillOfLoading = new WmsBillOfLoading();
        wmsBillOfLoading.setId(codeService.generateBillOfLoadingCode());
        wmsBillOfLoading.setDeliveryNoticeId(wmsDeliveryNotice.getId());
        wmsBillOfLoading.setBusinessId(wmsDeliveryNotice.getBusinessId());
        wmsBillOfLoading.setCompanyId(wmsDeliveryNotice.getCompanyId());
        wmsBillOfLoading.setBusinessType(wmsDeliveryNotice.getBusinessType());
        wmsBillOfLoading.setPlanQuantity(plannedQuantity);
        wmsBillOfLoading.setQuantity(actualQuantity);
        String customerCode = wmsDeliveryNotice.getToCode();
        if (StringUtils.isBlank(customerCode)) {
            wmsBillOfLoading.setCustomerName(wmsDeliveryNotice.getToName());
        } else {
            Customer customer = customerService.selectByCode(customerCode);
            if (null != customer) {
                wmsBillOfLoading.setCustomerId(customer.getId());
                wmsBillOfLoading.setCustomerName(customer.getCustomerName());
                wmsBillOfLoading.setCustomerCode(customer.getCustomerCode());
            }
        }

        wmsBillOfLoading.setStatus(BillOfLoadingStatus.WAITING.getCode());
        wmsBillOfLoading.setCreateBy(SecurityUtils.getUserId());
        wmsBillOfLoading.setCreateDate(DateUtils.getNowDate());
        wmsBillOfLoadingService.insertWmsBillOfLoading(wmsBillOfLoading);

        // 更新出库通知状态
        wmsDeliveryNotice.setStatus(DeliveryNoticeStatus.WAIT_DELIVERY.getCode());
        wmsDeliveryNoticeService.updateWmsDeliveryNotice(wmsDeliveryNotice);
    }


    /**
     * 校验是否可以收货
     */
    private AjaxResult checkDeliveryNotice(WmsDeliveryNotice wmsDeliveryNotice) {

        // 根据业务类型判断
        String businessType = wmsDeliveryNotice.getBusinessType();
        List<WmsDeliveryNoticeDetail> wmsDeliveryNoticeDetails = wmsDeliveryNoticeDetailService.selectByNoticeId(wmsDeliveryNotice.getId());
        // 判断明细拣货数量是否等于通知数量
        for (WmsDeliveryNoticeDetail wmsDeliveryNoticeDetail : wmsDeliveryNoticeDetails) {
            // 如果是销售出库
            if (businessType.equals(WmsDeliveryTypeEnum.SALE.getCode())) {
                if (wmsDeliveryNoticeDetail.getWorkFinishQuantity().compareTo(wmsDeliveryNoticeDetail.getQuantity()) > 0) {
                    return AjaxResult.error(StringUtils.format("物料: {},拣货数量不能大于通知数量", wmsDeliveryNoticeDetail.getItemName()));
                } else if (wmsDeliveryNoticeDetail.getWorkFinishQuantity().compareTo(wmsDeliveryNoticeDetail.getQuantity()) < 0) {
                    return AjaxResult.error(StringUtils.format("物料: {},拣货未完成", wmsDeliveryNoticeDetail.getItemName()));
                }
            } else {
                if (wmsDeliveryNoticeDetail.getWorkFinishQuantity().compareTo(wmsDeliveryNoticeDetail.getQuantity()) < 0) {
                    return AjaxResult.error(StringUtils.format("物料: {},拣货未完成", wmsDeliveryNoticeDetail.getItemName()));
                }
            }
        }
        return AjaxResult.success();
    }


    /**
     * 保存出库通知
     *
     * @param wmsReceiveNotice wms收到通知
     */
    public void createDeliveryNotice(WmsReceiveNotice wmsReceiveNotice) {
        ReceiveNoticeInfo receiveNoticeInfo = receiveNoticeManager.getReceiveNoticeInfoByNoticeId(wmsReceiveNotice.getId());
        List<WmsReceiveItemDetail> wmsReceiveItemDetailList = receiveNoticeInfo.getWmsReceiveItemDetailList();
        // 过滤出 合格数量大于0的 并且过滤 质检留样 + 抽样数量 - 抽样退还数 大于零的
        List<WmsReceiveItemDetail> itemDetails = wmsReceiveItemDetailList.stream()
                .filter(item -> item.getPassQuantity().compareTo(BigDecimal.ZERO) > 0)
                .filter(item -> item.getKeepQuantity().add(item.getSampleQuantity()).subtract(item.getSampleRefundQuantity()).compareTo(BigDecimal.ZERO) > 0)
                .collect(Collectors.toList());
        if (itemDetails.isEmpty()) {
            return;
        }


        // 查询质检人 质检人就是领料人
        WmsExamineWork examineWork = wmsExamineWorkService.selectByReceiveNoticeId(wmsReceiveNotice.getId());

        WmsReceiveNotice receiveNotice = receiveNoticeInfo.getWmsReceiveNotice();
        // 保存出库通知
        WmsDeliveryNotice wmsDeliveryNotice = new WmsDeliveryNotice();
        String code = codeService.generateDeliveryNoticeCode();
        wmsDeliveryNotice.setId(code);
        wmsDeliveryNotice.setReceiveSendType(WmsDeliveryTypeEnum.KEEP_SAMPLE.getCode());
        wmsDeliveryNotice.setBusinessType(WmsDeliveryTypeEnum.QUALITY_INSPECTION_PICKING.getCode());
        wmsDeliveryNotice.setBusinessId(receiveNotice.getId());
        wmsDeliveryNotice.setCompanyId(receiveNotice.getCompanyId());
        wmsDeliveryNotice.setStatus(DeliveryNoticeStatus.COMPLETE.getCode());
        wmsDeliveryNotice.setNoticeUser(examineWork.getExamineBy());
        wmsDeliveryNotice.setDeptName("质量部");
        wmsDeliveryNotice.setCreateBy(SecurityUtils.getUserId());
        wmsDeliveryNotice.setCreateTime(DateUtils.getNowDate());
        wmsDeliveryNotice.setDeliveryStartTime(DateUtils.getNowDate());
        wmsDeliveryNotice.setDeliveryEndTime(DateUtils.getNowDate());
        wmsDeliveryNoticeService.insertWmsDeliveryNotice(wmsDeliveryNotice);


        // 保存出库通知明细
        itemDetails.forEach((itemDetail) -> {
            BigDecimal totalQuantity = itemDetail.getKeepQuantity().add(itemDetail.getSampleQuantity()).subtract(itemDetail.getSampleRefundQuantity());
            WmsDeliveryNoticeDetail wmsDeliveryNoticeDetail = new WmsDeliveryNoticeDetail();
            wmsDeliveryNoticeDetail.setId(IdUtils.fastSimpleUUID());
            wmsDeliveryNoticeDetail.setDeliveryNoticeId(wmsDeliveryNotice.getId());
            wmsDeliveryNoticeDetail.setQuantity(totalQuantity);
            wmsDeliveryNoticeDetail.setWorkFinishQuantity(BigDecimal.ZERO);
            wmsDeliveryNoticeDetail.setAllotQuantity(BigDecimal.ZERO);
            wmsDeliveryNoticeDetail.setItemCode(itemDetail.getItemCode());
            wmsDeliveryNoticeDetail.setItemName(itemDetail.getItemName());
            wmsDeliveryNoticeDetail.setItemUnit(itemDetail.getUnit());
            wmsDeliveryNoticeDetail.setBusinessQuantity(totalQuantity);
            wmsDeliveryNoticeDetail.setBusinessUnit(itemDetail.getUnit());
            wmsDeliveryNoticeDetail.setCreateBy(SecurityUtils.getUserId());
            wmsDeliveryNoticeDetail.setCreateTime(DateUtils.getNowDate());
            wmsDeliveryNoticeDetailService.insertWmsDeliveryNoticeDetail(wmsDeliveryNoticeDetail);

            // 查询入库记录 获取物料上架的库位, 然后生成出库通知, 保存出入库记录, 最后扣减库存
            List<StorageInOutRecordVO> storageInOutRecordVOs = wmsStorageInOutRecordService.selectStorageRecord(itemDetail.getItemCode(), itemDetail.getBatchNo(), itemDetail.getReceiveNoticeId());
            if (storageInOutRecordVOs.isEmpty()) {
                throw new BusinessException("物料: " + itemDetail.getItemName() + " 未找到入库记录");
            }
            // 查询物料的库存汇总
            WmsItemStorage itemStorage = new WmsItemStorage();
            itemStorage.setItemCode(itemDetail.getItemCode());
            itemStorage.setCompanyId(receiveNotice.getCompanyId());
            List<WmsItemStorage> wmsItemStorageList = wmsItemStorageService.selectWmsItemStorageByCompany(itemStorage);
            BigDecimal beforeRollingQuantity = wmsItemStorageList.stream().map(WmsItemStorage::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
            WmsItemStorage storage = wmsItemStorageList.get(0);

            for (StorageInOutRecordVO storageInOutRecordVO : storageInOutRecordVOs) {
                if (storageInOutRecordVO.getQuantity().compareTo(totalQuantity) > 0) {
                    // 查询物料批次的库存数量
                    WmsItemStorage itemInfo = new WmsItemStorage();
                    itemInfo.setItemCode(storageInOutRecordVO.getItemCode());
                    itemInfo.setBatchNo(storageInOutRecordVO.getBatchNo());
                    itemInfo.setCompanyId(storageInOutRecordVO.getCompanyId());
                    BigDecimal itemBatchNoQuantity = wmsItemStorageService.getItemStorageBatchNoQuantity(itemInfo);
                    // 物料批次的原库存数量、结存库存数量
                    BigDecimal openingQuantity = itemBatchNoQuantity == null ? BigDecimal.ZERO : itemBatchNoQuantity;
                    BigDecimal surplusQuantity = openingQuantity.subtract(totalQuantity);

                    // 保存出入库记录
                    WmsStorageInOutRecord wmsStorageInOutRecord = new WmsStorageInOutRecord();
                    wmsStorageInOutRecord.setId(IdUtils.fastSimpleUUID());
                    wmsStorageInOutRecord.setItemCode(storageInOutRecordVO.getItemCode());
                    wmsStorageInOutRecord.setItemName(storageInOutRecordVO.getItemName());
                    wmsStorageInOutRecord.setUnit(storageInOutRecordVO.getUnit());
                    wmsStorageInOutRecord.setSpec(storageInOutRecordVO.getSpec());
                    wmsStorageInOutRecord.setCompanyId(storageInOutRecordVO.getCompanyId());
                    wmsStorageInOutRecord.setBatchNo(storageInOutRecordVO.getBatchNo());
                    wmsStorageInOutRecord.setQuantity(totalQuantity);
                    // 出库前单价、库存金额、库存数量 （是质检入库后价格做了单价滚算）
                    wmsStorageInOutRecord.setBeforeRollingPrice(storage.getActualPrice());
                    wmsStorageInOutRecord.setBeforeRollingTotalPrice(storage.getStoragePrice());
                    wmsStorageInOutRecord.setBeforeRollingQuantity(beforeRollingQuantity);
                    // 出库的单价，使用入库前的物料单价
                    wmsStorageInOutRecord.setOccPrice(storageInOutRecordVO.getOccPrice());
                    wmsStorageInOutRecord.setOccQuantity(totalQuantity);
                    wmsStorageInOutRecord.setOccTotalPrice(totalQuantity.multiply(storageInOutRecordVO.getOccPrice()));
                    // 重新滚算单价
                    BigDecimal afterRollingQuantity = beforeRollingQuantity.subtract(wmsStorageInOutRecord.getOccQuantity());
                    BigDecimal afterRollingTotalPrice = storage.getStoragePrice().subtract(wmsStorageInOutRecord.getOccTotalPrice());
                    BigDecimal afterRollingPrice = afterRollingQuantity.equals(BigDecimal.ZERO) ? BigDecimal.ZERO
                            : afterRollingTotalPrice.divide(afterRollingQuantity, 6, RoundingMode.HALF_UP);
                    wmsStorageInOutRecord.setAfterRollingPrice(afterRollingPrice);
                    wmsStorageInOutRecord.setAfterRollingQuantity(afterRollingQuantity);
                    wmsStorageInOutRecord.setAfterRollingTotalPrice(afterRollingTotalPrice);
                    // 物料该批次库存, 结存的金额是结存数 * 滚算后价格得到结存金额 （出库的金额扣减是不分批次的，总金额上的减少，然后进行单价滚算）
                    wmsStorageInOutRecord.setOpeningQuantity(openingQuantity);
                    wmsStorageInOutRecord.setOpeningAmount(openingQuantity.multiply(storage.getActualPrice()));
                    wmsStorageInOutRecord.setSurplusQuantity(surplusQuantity);
                    wmsStorageInOutRecord.setSurplusAmount(surplusQuantity.multiply(afterRollingPrice));
                    wmsStorageInOutRecord.setOccTime(wmsDeliveryNotice.getDeliveryStartTime());
                    wmsStorageInOutRecord.setNoticeId(wmsDeliveryNotice.getId());
                    wmsStorageInOutRecord.setBusinessNo(wmsDeliveryNotice.getBusinessId());
                    wmsStorageInOutRecord.setBillType(BillType.RECEIVE.getCode());
                    wmsStorageInOutRecord.setReceiveSendType(wmsDeliveryNotice.getReceiveSendType());
                    wmsStorageInOutRecord.setType(StorageInOutRecordType.OUT.getCode());
                    wmsStorageInOutRecord.setCreateBy(SecurityUtils.getUserId());
                    wmsStorageInOutRecord.setCreateTime(DateUtils.getNowDate());
                    wmsStorageInOutRecordService.insertWmsStorageInOutRecord(wmsStorageInOutRecord);

                    // 记录出库明细
                    WmsStorageInOutDetail wmsStorageInOutDetail = new WmsStorageInOutDetail();
                    wmsStorageInOutDetail.setId(IdUtils.fastSimpleUUID());
                    wmsStorageInOutDetail.setRecodeId(wmsStorageInOutRecord.getId());
                    wmsStorageInOutDetail.setWarehouseCode(storageInOutRecordVO.getWarehouseCode());
                    wmsStorageInOutDetail.setWarehouseId(storageInOutRecordVO.getWarehouseId());
                    wmsStorageInOutDetail.setLocationId(storageInOutRecordVO.getLocationId());
                    wmsStorageInOutDetail.setQuantity(totalQuantity);
                    wmsStorageInOutDetail.setItemCode(storageInOutRecordVO.getItemCode());
                    wmsStorageInOutDetail.setCreateBy(SecurityUtils.getUserId());
                    wmsStorageInOutDetail.setCreateTime(DateUtils.getNowDate());
                    wmsStorageInOutDetailService.insertWmsStorageInOutDetail(wmsStorageInOutDetail);

                    // 扣减库存
                    WmsItemStorageDetail itemStorageDetail = new WmsItemStorageDetail();
                    itemStorageDetail.setWarehouseId(storageInOutRecordVO.getWarehouseId());
                    itemStorageDetail.setLocationId(storageInOutRecordVO.getLocationId());
                    itemStorageDetail.setItemCode(storageInOutRecordVO.getItemCode());
                    itemStorageDetail.setBatchNo(storageInOutRecordVO.getBatchNo());
                    WmsItemStorageDetail storageDetail = wmsItemStorageDetailService.selectWmsItemStorageDetailByBatchNo(itemStorageDetail);
                    if (null == storageDetail) {
                        throw new BusinessException("物料: " + itemDetail.getItemName() + " 未找到库存记录");
                    }
                    storageDetail.setQuantity(storageDetail.getQuantity().subtract(totalQuantity));
                    if (storageDetail.getQuantity().compareTo(BigDecimal.ZERO) == 0) {
                        wmsItemStorageDetailService.deleteWmsItemStorageDetailById(storageDetail.getId());
                    } else {
                        storageDetail.setUpdateTime(DateUtils.getNowDate());
                        storageDetail.setUpdateBy(SecurityUtils.getUserId());
                        wmsItemStorageDetailService.updateWmsItemStorageDetail(storageDetail);
                    }

                    // 更新库存汇总
                    WmsItemStorage wmsItemStorage = wmsItemStorageService.selectWmsItemStorageById(storageDetail.getStorageId());
                    wmsItemStorage.setQuantity(wmsItemStorage.getQuantity().subtract(totalQuantity));
                    if (wmsItemStorage.getQuantity().compareTo(BigDecimal.ZERO) == 0) {
                        wmsItemStorageService.deleteWmsItemStorageById(wmsItemStorage.getId());
                    } else {
                        wmsItemStorage.setUpdateBy(SecurityUtils.getUserId());
                        wmsItemStorage.setUpdateTime(DateUtils.getNowDate());
                        wmsItemStorageService.updateWmsItemStorage(wmsItemStorage);
                    }
                    // 更新库存的实时单价、库存金额
                    wmsItemStorage.setActualPrice(afterRollingPrice);
                    wmsItemStorage.setStoragePrice(afterRollingTotalPrice);
                    wmsItemStorageService.updateWmsItemStorageAllPrice(wmsItemStorage);
                    break;
                }
            }
        });

    }


    /**
     * 保存出库通知
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveNotice(NoticeWmsDeliveryDto noticeWmsDeliveryDto) {
        // 保存出库通知
        WmsDeliveryNotice wmsDeliveryNotice = new WmsDeliveryNotice();
        wmsDeliveryNotice.setId(codeService.generateDeliveryNoticeCode());
        wmsDeliveryNotice.setBusinessType(noticeWmsDeliveryDto.getBusinessType());
        wmsDeliveryNotice.setBusinessId(noticeWmsDeliveryDto.getBusinessId());
        wmsDeliveryNotice.setCompanyId(noticeWmsDeliveryDto.getCompanyId());
        wmsDeliveryNotice.setToCode(noticeWmsDeliveryDto.getToCode());
        wmsDeliveryNotice.setToName(noticeWmsDeliveryDto.getToName());
        wmsDeliveryNotice.setToAddress(noticeWmsDeliveryDto.getToAddress());
        wmsDeliveryNotice.setToContacts(noticeWmsDeliveryDto.getToContacts());
        wmsDeliveryNotice.setDeptName(noticeWmsDeliveryDto.getDeptName());
        wmsDeliveryNotice.setToContactsTel(noticeWmsDeliveryDto.getToContactsTel());
        wmsDeliveryNotice.setNoticeUser(noticeWmsDeliveryDto.getNoticeUser());
        wmsDeliveryNotice.setNoticeUserId(noticeWmsDeliveryDto.getNoticeUserId());
        wmsDeliveryNotice.setNoticeDesc(noticeWmsDeliveryDto.getNoticeDesc());
        wmsDeliveryNotice.setStatus(DeliveryNoticeStatus.WAIT_ACTIVE.getCode());
        wmsDeliveryNotice.setCreateTime(DateUtils.getNowDate());
        wmsDeliveryNoticeService.insertWmsDeliveryNotice(wmsDeliveryNotice);
        List<NoticeWmsDeliveryDetailDTO> deliveryDetailList = noticeWmsDeliveryDto.getDeliveryDetailList();
        // 保存出库通知明细
        deliveryDetailList.forEach((deliveryDetail) -> {
            WmsDeliveryNoticeDetail wmsDeliveryNoticeDetail = new WmsDeliveryNoticeDetail();
            wmsDeliveryNoticeDetail.setId(IdUtils.fastSimpleUUID());
            wmsDeliveryNoticeDetail.setDeliveryNoticeId(wmsDeliveryNotice.getId());
            wmsDeliveryNoticeDetail.setQuantity(deliveryDetail.getQuantity());
            wmsDeliveryNoticeDetail.setWorkFinishQuantity(BigDecimal.ZERO);
            wmsDeliveryNoticeDetail.setWorkQuantity(BigDecimal.ZERO);
            wmsDeliveryNoticeDetail.setAllotQuantity(BigDecimal.ZERO);
            wmsDeliveryNoticeDetail.setDeliveryQuantity(BigDecimal.ZERO);
            wmsDeliveryNoticeDetail.setItemCode(deliveryDetail.getProductCode());
            wmsDeliveryNoticeDetail.setItemName(deliveryDetail.getProductName());
            wmsDeliveryNoticeDetail.setItemUnit(deliveryDetail.getUnit());
            wmsDeliveryNoticeDetail.setItemSpec(deliveryDetail.getSpec());
            wmsDeliveryNoticeDetail.setBusinessQuantity(deliveryDetail.getBusinessQuantity());
            wmsDeliveryNoticeDetail.setBusinessUnit(deliveryDetail.getBusinessUnit());
            wmsDeliveryNoticeDetail.setBelongs(deliveryDetail.getBelongs());
            wmsDeliveryNoticeDetail.setConversionRate(deliveryDetail.getConversionRate());
            wmsDeliveryNoticeDetail.setCreateTime(DateUtils.getNowDate());
            wmsDeliveryNoticeDetailService.insertWmsDeliveryNoticeDetail(wmsDeliveryNoticeDetail);
        });
    }

    /**
     * 发货完成
     *
     * @param noticeId 注意id
     * @throws Exception 异常
     */
    public void deliveryNoticeFinish(String noticeId, List<BaseDetailDTO> detailDTOList) throws Exception {

        // 出库通知
        WmsDeliveryNotice wmsDeliveryNotice = wmsDeliveryNoticeService.selectWmsDeliveryNoticeById(noticeId);

        WmsBillOfLoading wmsBillOfLoading = wmsBillOfLoadingService.selectByNoticeId(noticeId);
        DeliveryNoticeDTO deliveryNoticeDTO = new DeliveryNoticeDTO();
        if (null != wmsBillOfLoading) {
            // 不需要发货没有这些信息
            deliveryNoticeDTO.setCarInfo(wmsBillOfLoading.getCarInfo());
            deliveryNoticeDTO.setCarriersCode(wmsBillOfLoading.getLogisticsCode());
            deliveryNoticeDTO.setCarriersName(wmsBillOfLoading.getLogisticsName());
            deliveryNoticeDTO.setLogisticsNo(wmsBillOfLoading.getLogisticsNo());
        }
        deliveryNoticeDTO.setId(wmsDeliveryNotice.getId());
        deliveryNoticeDTO.setCompanyId(wmsDeliveryNotice.getCompanyId());
        deliveryNoticeDTO.setBusinessId(wmsDeliveryNotice.getBusinessId());
        deliveryNoticeDTO.setBusinessType(wmsDeliveryNotice.getBusinessType());
        deliveryNoticeDTO.setQuantity(detailDTOList.stream().map(BaseDetailDTO::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add));
        deliveryNoticeDTO.setDetailDTOList(detailDTOList);

        log.info("发货完成通知wms参数：{}", JSON.toJSONString(deliveryNoticeDTO));
//        apiFeignService.deliveryFinished(deliveryNoticeDTO);

    }


    /**
     * 作废出库通知
     *
     * @param wmsDeliveryNotice wms交货通知
     */
    public void invalidNotice(WmsDeliveryNotice wmsDeliveryNotice) throws Exception {
        WmsCancelDTO wmsCancelDTO = new WmsCancelDTO();
        wmsCancelDTO.setBusinessId(wmsDeliveryNotice.getBusinessId());
        wmsCancelDTO.setBusinessType(wmsDeliveryNotice.getBusinessType());
//        apiFeignService.cancelNotice(wmsCancelDTO);
    }


}
