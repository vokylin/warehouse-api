package com.ruoyi.project.common.component;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.enums.*;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.project.common.entity.*;
import com.ruoyi.project.warehouse.domain.*;
import com.ruoyi.project.warehouse.mapper.*;
import com.ruoyi.project.warehouse.service.IWmsDeliveryNoticeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 收货通知单信息
 *
 * @author REM
 * @date 2023/04/08
 */
@Slf4j
@Component
public class ReceiveNoticeManager {


    @Autowired
    private WmsReceiveNoticeDetailMapper wmsReceiveNoticeDetailMapper;

    @Autowired
    private WmsReceiveItemDetailMapper wmsReceiveItemDetailMapper;

    @Autowired
    private WmsReceiveNoticeMapper wmsReceiveNoticeMapper;

//    @Autowired
//    private ApiFeignService apiFeignService;

    @Autowired
    private CodeService codeService;

    @Autowired
    private WmsStorageInOutRecordMapper storageInOutRecordMapper;

    @Autowired
    private WmsStorageInOutDetailMapper storageInOutDetailMapper;

    @Autowired
    private WmsItemStorageMapper wmsItemStorageMapper;

    @Autowired
    private IWmsDeliveryNoticeService wmsDeliveryNoticeService;

    /**
     * 获取收货通知物料明细以及 收货通知，收货通知明细
     *
     * @param itemDetailId 项目细节id
     * @return {@link ReceiveNoticeInfo}
     */
    public ReceiveNoticeInfo getReceiveNoticeInfoByItemDetailId(String itemDetailId) {
        WmsReceiveItemDetail wmsReceiveItemDetail = wmsReceiveItemDetailMapper.selectWmsReceiveItemDetailById(itemDetailId);
        WmsReceiveNotice wmsReceiveNotice = wmsReceiveNoticeMapper.selectWmsReceiveNoticeById(wmsReceiveItemDetail.getReceiveNoticeId());
        WmsReceiveNoticeDetail receiveNoticeDetail = wmsReceiveNoticeDetailMapper.selectWmsReceiveNoticeDetailById(wmsReceiveItemDetail.getReceiveNoticeDetailId());
        return ReceiveNoticeInfo.builder()
                .wmsReceiveNotice(wmsReceiveNotice)
                .wmsReceiveNoticeDetail(receiveNoticeDetail)
                .wmsReceiveItemDetail(wmsReceiveItemDetail)
                .build();
    }

    /**
     * 获取收货通知物料明细
     *
     * @param noticeId 通知单id
     * @return {@link ReceiveNoticeInfo}
     */
    public ReceiveNoticeInfo getReceiveNoticeInfoByNoticeId(String noticeId) {
        List<WmsReceiveItemDetail> wmsReceiveItemDetails = wmsReceiveItemDetailMapper.selectWmsReceiveItemDetailByReceiveNoticeId(noticeId);
        List<WmsReceiveNoticeDetail> wmsReceiveNoticeDetails = wmsReceiveNoticeDetailMapper.selectReceiveNoticeDetailListByNoticeId(noticeId);
        WmsReceiveNotice wmsReceiveNotice = wmsReceiveNoticeMapper.selectWmsReceiveNoticeById(noticeId);
        return ReceiveNoticeInfo.builder()
                .wmsReceiveItemDetailList(wmsReceiveItemDetails)
                .wmsReceiveNoticeDetailList(wmsReceiveNoticeDetails)
                .wmsReceiveNotice(wmsReceiveNotice)
                .build();
    }

    /**
     * 保存收货通知
     *
     * @param noticeWmsReceiveDto 注意wms收到dto
     */
    public void saveNotice(NoticeWmsReceiveDto noticeWmsReceiveDto) {
        String noticeCode = codeService.generateReceiveNoticeCode();
        WmsReceiveNotice wmsReceiveNotice = new WmsReceiveNotice();
        wmsReceiveNotice.setId(noticeCode);
        wmsReceiveNotice.setBusinessId(noticeWmsReceiveDto.getBusinessId());
        wmsReceiveNotice.setBusinessType(noticeWmsReceiveDto.getBusinessType());
        wmsReceiveNotice.setCompanyId(noticeWmsReceiveDto.getCompanyId());
        wmsReceiveNotice.setFromCode(noticeWmsReceiveDto.getFromCode());
        wmsReceiveNotice.setFromName(noticeWmsReceiveDto.getFromName());
        wmsReceiveNotice.setTaskId(noticeWmsReceiveDto.getTaskId());
        wmsReceiveNotice.setNoticeUser(noticeWmsReceiveDto.getNoticeUser());
        wmsReceiveNotice.setNoticeTime(DateUtils.getNowDate());
        wmsReceiveNotice.setDeptName(noticeWmsReceiveDto.getDeptName());
        wmsReceiveNotice.setNoticeDesc(noticeWmsReceiveDto.getNoticeDesc());
        wmsReceiveNotice.setStatus(DeliveryNoticeStatus.WAIT_ACTIVE.getCode());
        wmsReceiveNotice.setCreateTime(DateUtils.getNowDate());
        wmsReceiveNotice.setScrapSpecialFlag(noticeWmsReceiveDto.getScrapSpecialFlag());
        wmsReceiveNoticeMapper.insertWmsReceiveNotice(wmsReceiveNotice);
        List<NoticeWmsReceiveDetailDTO> receiveDetail = noticeWmsReceiveDto.getReceiveDetail();
        receiveDetail.forEach(item -> {
            WmsReceiveNoticeDetail wmsReceiveNoticeDetail = new WmsReceiveNoticeDetail();
            wmsReceiveNoticeDetail.setId(IdUtils.fastSimpleUUID());
            wmsReceiveNoticeDetail.setReceiveNoticeId(noticeCode);
            wmsReceiveNoticeDetail.setQuantity(item.getQuantity());
            wmsReceiveNoticeDetail.setReceiveQuantity(BigDecimal.ZERO);
            wmsReceiveNoticeDetail.setProductDate(item.getProductDate());
            wmsReceiveNoticeDetail.setItemCode(item.getProductCode());
            wmsReceiveNoticeDetail.setItemName(item.getProductName());
            wmsReceiveNoticeDetail.setUnit(item.getUnit());
            wmsReceiveNoticeDetail.setSpec(item.getSpec());
            wmsReceiveNoticeDetail.setBatchNo(item.getBatchNo());
            wmsReceiveNoticeDetail.setConversionRate(item.getConversionRate());
            wmsReceiveNoticeDetail.setItemType(item.getItemType());
            wmsReceiveNoticeDetail.setItemPrice(item.getItemPrice());
            wmsReceiveNoticeDetail.setBusinessQuantity(item.getBusinessQuantity());
            wmsReceiveNoticeDetail.setBusinesUnit(item.getBusinessUnit());
            wmsReceiveNoticeDetail.setStatus(ReceiptStatus.WAIT_ACTIVE.getCode());
            wmsReceiveNoticeDetail.setBelongs(item.getBelongs());
            wmsReceiveNoticeDetail.setProductDate(item.getProductDate());
            wmsReceiveNoticeDetail.setExpireTime(item.getExpireTime());
            wmsReceiveNoticeDetail.setCheckAcceptId(item.getFormId());
            wmsReceiveNoticeDetail.setCreateTime(DateUtils.getNowDate());
            wmsReceiveNoticeDetailMapper.insertWmsReceiveNoticeDetail(wmsReceiveNoticeDetail);
        });
    }


    /**
     * 入库未税金额调整
     *
     * @param noticeWmsReceiveDto 调整dto
     */
    public void adjustPrice(NoticeWmsReceiveDto noticeWmsReceiveDto) {
        // 记录已完成的入库通知
        String noticeCode = codeService.generateReceiveNoticeCode();
        WmsReceiveNotice wmsReceiveNotice = new WmsReceiveNotice();
        wmsReceiveNotice.setId(noticeCode);
        wmsReceiveNotice.setBusinessId(noticeWmsReceiveDto.getBusinessId());
        wmsReceiveNotice.setBusinessType(noticeWmsReceiveDto.getBusinessType());
        wmsReceiveNotice.setCompanyId(noticeWmsReceiveDto.getCompanyId());
        wmsReceiveNotice.setFromCode(noticeWmsReceiveDto.getFromCode());
        wmsReceiveNotice.setFromName(noticeWmsReceiveDto.getFromName());
        wmsReceiveNotice.setNoticeUser(noticeWmsReceiveDto.getNoticeUser());
        wmsReceiveNotice.setNoticeTime(DateUtils.getNowDate());
        wmsReceiveNotice.setStatus(ReceiveNoticeStatus.COMPLETE.getCode());
        wmsReceiveNotice.setReceiveSendType(WmsReceiveTypeEnum.PURCHASE.getCode());
        wmsReceiveNotice.setCreateTime(DateUtils.getNowDate());
        wmsReceiveNotice.setStartTime(DateUtils.getNowDate());
        wmsReceiveNotice.setEndTime(DateUtils.getNowDate());
        wmsReceiveNoticeMapper.insertWmsReceiveNotice(wmsReceiveNotice);

        List<NoticeWmsReceiveDetailDTO> receiveDetail = noticeWmsReceiveDto.getReceiveDetail();
        receiveDetail.forEach(detail -> {
            WmsStorageInOutRecord wmsStorageInOutRecord = new WmsStorageInOutRecord();
            wmsStorageInOutRecord.setItemCode(detail.getProductCode());
            wmsStorageInOutRecord.setType(StorageInOutRecordType.IN.getCode());
            List<WmsStorageInOutRecord> wmsStorageInOutRecords = storageInOutRecordMapper.selectWmsStorageInOutRecordList(wmsStorageInOutRecord)
                    .stream().sorted(Comparator.comparing(WmsStorageInOutRecord::getCreateTime).reversed()).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(wmsStorageInOutRecords)) {
                // 入库的businessNo
                wmsStorageInOutRecord.setBusinessNo(noticeWmsReceiveDto.getBusinessId());
                wmsStorageInOutRecord.setOpeningQuantity(BigDecimal.ZERO);
                wmsStorageInOutRecord.setOpeningAmount(BigDecimal.ZERO);
                wmsStorageInOutRecord.setSurplusQuantity(BigDecimal.ZERO);
                wmsStorageInOutRecord.setSurplusAmount(BigDecimal.ZERO);

                // 取最近一次的入库记录
                WmsStorageInOutRecord lastInRecord = wmsStorageInOutRecords.get(0);
                WmsItemStorage wmsItemStorage = new WmsItemStorage();
                wmsItemStorage.setItemCode(detail.getProductCode());
                WmsItemStorage storage = wmsItemStorageMapper.selectWmsItemStorage(wmsItemStorage);
                // 有库存
                if (storage != null) {
                    log.info("物料：{}，存在库存", detail.getProductCode());
                    // 库存金额
                    BigDecimal storagePrice = storage.getStoragePrice();
                    wmsStorageInOutRecord.setBeforeRollingPrice(storage.getActualPrice());
                    wmsStorageInOutRecord.setBeforeRollingTotalPrice(storagePrice);
                    wmsStorageInOutRecord.setBeforeRollingQuantity(storage.getQuantity());
                    wmsStorageInOutRecord.setAfterRollingQuantity(storage.getQuantity());
                    // 库存金额 + 调整金额，得到最终库存金额
                    BigDecimal endPrice = storagePrice.add(detail.getItemPrice()).setScale(6, RoundingMode.HALF_UP);

                    if (endPrice.compareTo(BigDecimal.ZERO) > 0) {
                        log.info("库存金额调整后大于0，调整前金额：{}，调整金额：{}， 调整后金额：{}", storagePrice, detail.getItemPrice(), endPrice);
                        // 最终库存金额大于0时，不做金额的出库，重新计算实时单价
                        BigDecimal actualPrice = endPrice.divide(storage.getQuantity(), 6, RoundingMode.HALF_UP);
                        wmsStorageInOutRecord.setAfterRollingPrice(actualPrice);
                        wmsStorageInOutRecord.setAfterRollingTotalPrice(endPrice);
                        storage.setActualPrice(actualPrice);
                        storage.setStoragePrice(endPrice);
                        generateStorageInRecord(wmsStorageInOutRecord, lastInRecord, detail, noticeWmsReceiveDto.getCompanyId(), noticeCode);
                    } else {
                        // 库存金额不足以抵消调整的金额时，将剩余负数金额做出库处理，库存金额调整为0
                        log.info("库存金额调整为0，调整前金额：{}，调整金额：{}", storagePrice, detail.getItemPrice());
                        storage.setActualPrice(BigDecimal.ZERO);
                        storage.setStoragePrice(BigDecimal.ZERO);
                        wmsStorageInOutRecord.setAfterRollingPrice(BigDecimal.ZERO);
                        wmsStorageInOutRecord.setAfterRollingTotalPrice(endPrice);
                        // 产生入库记录
                        generateStorageInRecord(wmsStorageInOutRecord, lastInRecord, detail, noticeWmsReceiveDto.getCompanyId(), noticeCode);
                        String deliveryNoticeId = createDeliveryNotice(noticeWmsReceiveDto, noticeCode);
                        // 产生出库记录，滚算前金额、出库金额均为入库抵消后的金额
                        wmsStorageInOutRecord.setBeforeRollingPrice(BigDecimal.ZERO);
                        wmsStorageInOutRecord.setBeforeRollingTotalPrice(endPrice);
                        wmsStorageInOutRecord.setBusinessNo(noticeCode);
                        wmsStorageInOutRecord.setNoticeId(deliveryNoticeId);
                        wmsStorageInOutRecord.setAfterRollingTotalPrice(BigDecimal.ZERO);
                        generateStorageOutRecord(wmsStorageInOutRecord, lastInRecord, endPrice);

                    }
                    storage.setUpdateTime(DateUtils.getNowDate());
                    // 更新库存金额、实时单价
                    wmsItemStorageMapper.updateWmsItemStorageAllPrice(storage);
                } else {
                    log.info("物料：{}，没有库存", detail.getProductCode());
                    // 没有库存,先产生一笔入库记录（将金额入进来），再产生一笔出库记录（将金额出给采购部）
                    wmsStorageInOutRecord.setBeforeRollingPrice(BigDecimal.ZERO);
                    wmsStorageInOutRecord.setBeforeRollingTotalPrice(BigDecimal.ZERO);
                    wmsStorageInOutRecord.setBeforeRollingQuantity(BigDecimal.ZERO);
                    wmsStorageInOutRecord.setAfterRollingPrice(BigDecimal.ZERO);
                    wmsStorageInOutRecord.setAfterRollingTotalPrice(detail.getItemPrice());
                    wmsStorageInOutRecord.setAfterRollingQuantity(BigDecimal.ZERO);

                    // 产生入库记录
                    generateStorageInRecord(wmsStorageInOutRecord, lastInRecord, detail, noticeWmsReceiveDto.getCompanyId(), noticeCode);
                    String deliveryNoticeId = createDeliveryNotice(noticeWmsReceiveDto, noticeCode);
                    // 产生出库记录
                    wmsStorageInOutRecord.setBeforeRollingTotalPrice(detail.getItemPrice());
                    wmsStorageInOutRecord.setAfterRollingTotalPrice(BigDecimal.ZERO);
                    wmsStorageInOutRecord.setBusinessNo(noticeCode);
                    wmsStorageInOutRecord.setNoticeId(deliveryNoticeId);
                    generateStorageOutRecord(wmsStorageInOutRecord, lastInRecord, detail.getItemPrice());
                }
            } else {
                log.error("物料=====>{}，还未产生过入库记录，无法做价格调整", detail.getProductCode());
            }
        });


    }

    /**
     * 生成出库通知
     *
     * @param noticeDto
     * @param receiveNoticeCode
     */
    private String createDeliveryNotice(NoticeWmsReceiveDto noticeDto, String receiveNoticeCode) {
        // 生成出库通知
        WmsDeliveryNotice wmsDeliveryNotice = new WmsDeliveryNotice();
        String deliveryNoticeId = codeService.generateDeliveryNoticeCode();
        wmsDeliveryNotice.setId(deliveryNoticeId);
        // 出库类型为：其他出库
        wmsDeliveryNotice.setBusinessType(WmsDeliveryTypeEnum.OTHER.getCode());
        wmsDeliveryNotice.setBusinessId(receiveNoticeCode);
        wmsDeliveryNotice.setCompanyId(noticeDto.getCompanyId());
        wmsDeliveryNotice.setDeptName("采购部");
        wmsDeliveryNotice.setNoticeUser(noticeDto.getNoticeUser());
        wmsDeliveryNotice.setNoticeUserId(noticeDto.getNoticeUserId());
        wmsDeliveryNotice.setStatus(DeliveryNoticeStatus.COMPLETE.getCode());
        wmsDeliveryNotice.setCreateTime(DateUtils.getNowDate());
        wmsDeliveryNotice.setDeliveryStartTime(DateUtils.getNowDate());
        wmsDeliveryNotice.setDeliveryEndTime(DateUtils.getNowDate());
        wmsDeliveryNoticeService.insertWmsDeliveryNotice(wmsDeliveryNotice);
        return deliveryNoticeId;
    }

    /**
     * 生成入库的金额调整记录
     *
     * @param wmsStorageInOutRecord 入库调整记录
     * @param lastInRecord          某物料最后一次入库记录
     * @param detail                调整详情
     * @param companyId             公司ID
     * @param noticeCode            入库通知ID
     */
    private void generateStorageInRecord(WmsStorageInOutRecord wmsStorageInOutRecord, WmsStorageInOutRecord lastInRecord,
                                         NoticeWmsReceiveDetailDTO detail, String companyId, String noticeCode) {
        // 产生一笔数量为0，金额为调整金额的入库记录
        String recordId = IdUtils.fastSimpleUUID();
        wmsStorageInOutRecord.setId(recordId);
        wmsStorageInOutRecord.setCompanyId(companyId);
        wmsStorageInOutRecord.setItemName(lastInRecord.getItemName());
        wmsStorageInOutRecord.setUnit(lastInRecord.getUnit());
        wmsStorageInOutRecord.setSpec(lastInRecord.getSpec());
        wmsStorageInOutRecord.setOccPrice(BigDecimal.ZERO);
        wmsStorageInOutRecord.setOccQuantity(BigDecimal.ZERO);
        wmsStorageInOutRecord.setOccTotalPrice(detail.getItemPrice());
        wmsStorageInOutRecord.setQuantity(BigDecimal.ZERO);
        wmsStorageInOutRecord.setNoticeId(noticeCode);
        wmsStorageInOutRecord.setBillType(BillType.IN.getCode());
        // 收发类别记录为采购入库 （由采购产生的调整）
        wmsStorageInOutRecord.setReceiveSendType(WmsReceiveTypeEnum.PURCHASE.getCode());
        wmsStorageInOutRecord.setCreateTime(DateUtils.getNowDate());
        wmsStorageInOutRecord.setOccTime(DateUtils.getNowDate());
        storageInOutRecordMapper.insertWmsStorageInOutRecord(wmsStorageInOutRecord);

        // 记录入库明细
        WmsStorageInOutDetail inDetail = new WmsStorageInOutDetail();
        inDetail.setId(IdUtils.fastSimpleUUID());
        inDetail.setRecodeId(recordId);
        inDetail.setWarehouseId(lastInRecord.getWarehouseId());
        inDetail.setWarehouseCode(lastInRecord.getWarehouseCode());
        inDetail.setLocationId(lastInRecord.getLocationId());
        inDetail.setQuantity(BigDecimal.ZERO);
        inDetail.setItemCode(detail.getProductCode());
        inDetail.setCreateTime(DateUtils.getNowDate());
        storageInOutDetailMapper.insertWmsStorageInOutDetail(inDetail);
    }

    /**
     * 仓库该调整物料没有库存，需要将调整金额做出库
     *
     * @param wmsStorageInOutRecord 入库调整记录
     * @param lastInRecord          某物料最后一次入库记录
     * @param occTotalPrice         调整详情
     */
    private void generateStorageOutRecord(WmsStorageInOutRecord wmsStorageInOutRecord, WmsStorageInOutRecord lastInRecord,
                                          BigDecimal occTotalPrice) {
        // 产生一笔数量为0，金额为调整金额的入库记录
        String recordId = IdUtils.fastSimpleUUID();
        wmsStorageInOutRecord.setId(recordId);
        wmsStorageInOutRecord.setType(StorageInOutRecordType.OUT.getCode());
        wmsStorageInOutRecord.setOccTotalPrice(occTotalPrice);
        wmsStorageInOutRecord.setBillType(BillType.RECEIVE.getCode());
        // 收发类别记录为采购入库 （由采购产生的调整）
        wmsStorageInOutRecord.setReceiveSendType(WmsDeliveryTypeEnum.OTHER.getCode());
        wmsStorageInOutRecord.setCreateTime(DateUtils.getNowDate());
        wmsStorageInOutRecord.setOccTime(DateUtils.getNowDate());
        storageInOutRecordMapper.insertWmsStorageInOutRecord(wmsStorageInOutRecord);

        // 记录入库明细
        WmsStorageInOutDetail inDetail = new WmsStorageInOutDetail();
        inDetail.setId(IdUtils.fastSimpleUUID());
        inDetail.setRecodeId(recordId);
        inDetail.setWarehouseId(lastInRecord.getWarehouseId());
        inDetail.setWarehouseCode(lastInRecord.getWarehouseCode());
        inDetail.setLocationId(lastInRecord.getLocationId());
        inDetail.setQuantity(BigDecimal.ZERO);
        inDetail.setItemCode(wmsStorageInOutRecord.getItemCode());
        inDetail.setCreateTime(DateUtils.getNowDate());
        storageInOutDetailMapper.insertWmsStorageInOutDetail(inDetail);
    }


    /**
     * 通知其他系统作废通知
     *
     * @param wmsReceiveNotice wms收到通知
     */
    public void invalidNotice(WmsReceiveNotice wmsReceiveNotice) throws Exception {
        WmsCancelDTO wmsCancelDTO = new WmsCancelDTO();
        wmsCancelDTO.setBusinessId(wmsReceiveNotice.getBusinessId());
        wmsCancelDTO.setBusinessType(wmsReceiveNotice.getBusinessType());
        wmsCancelDTO.setTaskId(wmsReceiveNotice.getTaskId());
        // 调用API 通知其他系统作废通知
//        apiFeignService.cancelNotice(wmsCancelDTO);
    }


    /**
     * 收货完成
     *
     * @param receiveNoticeInfo 收到通知信息
     */
    public void receiveNoticeFinish(ReceiveNoticeInfo receiveNoticeInfo) throws Exception {

        WmsReceiveNotice wmsReceiveNotice = receiveNoticeInfo.getWmsReceiveNotice();
        List<WmsReceiveItemDetail> wmsReceiveItemDetails = receiveNoticeInfo.getWmsReceiveItemDetailList();
        List<WmsReceiveNoticeDetail> wmsReceiveNoticeDetailList = receiveNoticeInfo.getWmsReceiveNoticeDetailList();
        Map<String, WmsReceiveNoticeDetail> noticeDetailMap = wmsReceiveNoticeDetailList.stream().collect(Collectors.toMap(WmsReceiveNoticeDetail::getItemCode, Function.identity(), (k1, k2) -> k2));

        List<BaseDetailDTO> detailDTOList = new ArrayList<>();
        // 根据物料编码分组
        Map<String, List<WmsReceiveItemDetail>> itemDetailMap = wmsReceiveItemDetails.stream().collect(Collectors.groupingBy(WmsReceiveItemDetail::getItemCode));

        if (!itemDetailMap.isEmpty()) {
            itemDetailMap.forEach((code, list) -> {
                WmsReceiveNoticeDetail noticeDetail = noticeDetailMap.get(code);
                if (noticeDetail != null) {
                    // 换算率
                    BigDecimal conversionRate = noticeDetail.getConversionRate();
                    // 计算退货总数
                    BigDecimal quantity;
                    if (ScrapSpecialFlag.SCRAP.getCode().equals(wmsReceiveNotice.getScrapSpecialFlag()) || ScrapSpecialFlag.SPECIAL.getCode().equals(wmsReceiveNotice.getScrapSpecialFlag())) {
                        quantity = list.stream().map(item -> item.getFailQuantity() == null ? BigDecimal.ZERO : item.getFailQuantity())
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                    } else {
                        quantity = list.stream().map(item -> item.getPassQuantity() == null ? BigDecimal.ZERO : item.getPassQuantity())
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                    }
                    BaseDetailDTO baseDetailDTO = new BaseDetailDTO();
                    baseDetailDTO.setItemCode(noticeDetail.getItemCode());
                    baseDetailDTO.setItemName(noticeDetail.getItemName());
                    baseDetailDTO.setItemUnit(noticeDetail.getUnit());
                    baseDetailDTO.setSpec(noticeDetail.getSpec());
                    // 上架数量总数 * 换算率（4舍5入）
                    baseDetailDTO.setQuantity(quantity.multiply(conversionRate).setScale(0, RoundingMode.HALF_UP));
                    // 将入库时的单价（通过换算率计算过的单价）带回，供发运单计算实际入库的未税金额使用
                    baseDetailDTO.setPrice(noticeDetail.getItemPrice());
                    baseDetailDTO.setReceiveQuantity(quantity);
                    detailDTOList.add(baseDetailDTO);
                } else {
                    log.error("WmsReceiveItemDetail中的物料编码获取不到WmsReceiveNoticeDetail对象, itemCode =====>{}", code);
                }
            });
        }

        BigDecimal totalQuantity = detailDTOList.stream().map(BaseDetailDTO::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
        BaseNoticeDTO baseNoticeDTO = new BaseNoticeDTO();
        baseNoticeDTO.setId(wmsReceiveNotice.getId());
        baseNoticeDTO.setBusinessId(wmsReceiveNotice.getBusinessId());
        baseNoticeDTO.setBusinessType(wmsReceiveNotice.getBusinessType());
        baseNoticeDTO.setCompanyId(wmsReceiveNotice.getCompanyId());
        baseNoticeDTO.setQuantity(totalQuantity);
        baseNoticeDTO.setDetailDTOList(detailDTOList);
        baseNoticeDTO.setScrapSpecialFlag(wmsReceiveNotice.getScrapSpecialFlag());
        log.info("收货完成通知:{}", JSON.toJSONString(baseNoticeDTO));
//        apiFeignService.receiveFinished(baseNoticeDTO);

    }
}
