package com.ruoyi.project.warehouse.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageHelper;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.enums.IsDeletedEnum;
import com.ruoyi.common.enums.StorageInOutRecordType;
import com.ruoyi.common.exception.business.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.system.domain.SysDictData;
import com.ruoyi.project.system.service.ISysDictTypeService;
import com.ruoyi.project.warehouse.domain.WmsMonthCheckOut;
import com.ruoyi.project.warehouse.domain.WmsStorageInOutRecord;
import com.ruoyi.project.warehouse.mapper.WmsDeliveryNoticeMapper;
import com.ruoyi.project.warehouse.mapper.WmsMonthCheckOutMapper;
import com.ruoyi.project.warehouse.mapper.WmsReceiveNoticeMapper;
import com.ruoyi.project.warehouse.mapper.WmsStorageInOutRecordMapper;
import com.ruoyi.project.warehouse.service.IWmsMonthCheckOutService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 月结Service业务层处理
 *
 * @author gaomian
 * @date 2023-05-27
 */
@Service
@Slf4j
public class WmsMonthCheckOutServiceImpl implements IWmsMonthCheckOutService {
    @Autowired
    private WmsMonthCheckOutMapper wmsMonthCheckOutMapper;

    @Autowired
    private ISysDictTypeService dictTypeService;

    @Autowired
    private WmsStorageInOutRecordMapper wmsStorageInOutRecordMapper;

    @Autowired
    private WmsReceiveNoticeMapper wmsReceiveNoticeMapper;

    @Autowired
    private WmsDeliveryNoticeMapper wmsDeliveryNoticeMapper;

    /**
     * 查询月结列表
     *
     * @param wmsMonthCheckOut 月结
     * @return 月结
     */
    @Override
    public List<WmsMonthCheckOut> selectWmsMonthCheckOutList(WmsMonthCheckOut wmsMonthCheckOut) {
        // 查询条件不带年月时，查询当前月的月结信息
        if (Strings.isEmpty(wmsMonthCheckOut.getYearMonths())) {
            wmsMonthCheckOut.setYearMonths(DateUtils.dateForYearMonth());
        }
        return wmsMonthCheckOutMapper.selectWmsMonthCheckOutList(wmsMonthCheckOut);
    }

    @Override
    public AjaxResult checkNoticeMonth(String month) {

        Date yearMonthDate;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.YYYY_MM);
            yearMonthDate = simpleDateFormat.parse(month);
        } catch (ParseException e) {
            log.error("月结月份格式错误 ", e);
            return AjaxResult.error("需要月结的月份格式错误");
        }
        String startTime = DateUtils.getMonthFirstDay(yearMonthDate) + " 00:00:00";
        String endTime = DateUtils.getMonthLastDay(yearMonthDate) + " 23:59:59";

        int receiveNoticeNum = wmsReceiveNoticeMapper.selectUnfinishedReceiveNotice(startTime, endTime);
        int deliveryNoticeNum = wmsDeliveryNoticeMapper.selectUnfinishedDeliveryNotice(startTime, endTime);

        StringBuffer sb = new StringBuffer();
        if (receiveNoticeNum > 0) {
            sb.append(String.format("%s个入库通知未完成，", receiveNoticeNum));
        }
        if (deliveryNoticeNum > 0) {
            sb.append(String.format("%s个出库通知未完成，", deliveryNoticeNum));
        }

        return AjaxResult.success(sb.toString());
    }

    @Override
    public AjaxResult getCheckOutMonth() {
        PageHelper.clearPage();
        String lastCheckOutMonth = wmsMonthCheckOutMapper.getLastCheckOutMonth();
        String monthLastDay = null;
        if (Strings.isNotEmpty(lastCheckOutMonth)) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.YYYY_MM);
                Date yearMonthDate = simpleDateFormat.parse(lastCheckOutMonth);
                monthLastDay = DateUtils.getMonthLastDay(yearMonthDate);
            } catch (ParseException e) {
                log.error("查询月份格式错误", e);
                return AjaxResult.error("月结的月份格式错误");
            }
        }

        return AjaxResult.success(monthLastDay);
    }


    /**
     * 月结
     *
     * @param wmsMonthCheckOut
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult monthSettlement(WmsMonthCheckOut wmsMonthCheckOut) {
        AjaxResult ajaxResult = checkMonthSettlementParams(wmsMonthCheckOut);
        if (ajaxResult != null) {
            return ajaxResult;
        }

        try {
            String yearMonths = wmsMonthCheckOut.getYearMonths();
            wmsMonthCheckOut.setYearMonths(DateUtils.getLastMonth(yearMonths));
            // 上一月月结数据
            Map<String, WmsMonthCheckOut> lastMonthMap = new HashMap<>();
            List<WmsMonthCheckOut> lastMonthCheckOuts = wmsMonthCheckOutMapper.selectWmsMonthCheckOutList(wmsMonthCheckOut);
            if (CollectionUtils.isNotEmpty(lastMonthCheckOuts)) {
                lastMonthMap = lastMonthCheckOuts.stream().collect(Collectors.toMap(WmsMonthCheckOut::getItemCode, Function.identity(), (k1, k2) -> k2));
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.YYYY_MM);
            Date yearMonthDate = simpleDateFormat.parse(yearMonths);
            WmsStorageInOutRecord wmsStorageInOutRecord = new WmsStorageInOutRecord();
            wmsStorageInOutRecord.setCompanyId(wmsMonthCheckOut.getCompanyId());
            wmsStorageInOutRecord.setBeginTime(DateUtils.getMonthFirstDay(yearMonthDate) + " 00:00:00");
            wmsStorageInOutRecord.setEndTime(DateUtils.getMonthLastDay(yearMonthDate) + " 23:59:59");
            // 获取月结月份的出入库记录
            List<WmsStorageInOutRecord> wmsStorageInOutRecords = wmsStorageInOutRecordMapper.selectWmsStorageInOutRecordList(wmsStorageInOutRecord);

            // 以物料编码进行分组
            Map<String, List<WmsStorageInOutRecord>> inOutRecordMap = wmsStorageInOutRecords.stream().collect(Collectors.groupingBy(WmsStorageInOutRecord::getItemCode));

            String[] yearMonthArr = yearMonths.split("-");
            List<WmsMonthCheckOut> itemInfo = wmsMonthCheckOutMapper.findItemInfo();
            for (WmsMonthCheckOut item : itemInfo) {
                item.setId(IdUtils.fastSimpleUUID());
                item.setCompanyId(wmsMonthCheckOut.getCompanyId());
                item.setYearMonths(yearMonths);
                item.setYearStr(yearMonthArr[0]);
                item.setMonthStr(yearMonthArr[1]);
                item.setItemCode(item.getItemCode().trim());
                WmsMonthCheckOut lastMonthInfo = lastMonthMap.get(item.getItemCode());
                // 存在上月月结信息时，使用上月月结的期末数据作为所选月份的期初数据
                if (lastMonthInfo != null) {
                    item.setOpeningPrice(lastMonthInfo.getEndingPrice());
                    item.setOpeningQuantity(lastMonthInfo.getEndingQuantity());
                    item.setOpeningTotal(lastMonthInfo.getEndingTotal());
                } else {
                    item.setOpeningPrice(BigDecimal.ZERO);
                    item.setOpeningQuantity(BigDecimal.ZERO);
                    item.setOpeningTotal(BigDecimal.ZERO);
                }

                List<WmsStorageInOutRecord> inOutRecordsList = inOutRecordMap.get(item.getItemCode());
                if (CollectionUtils.isNotEmpty(inOutRecordsList)) {
                    // 月结月份的入库记录
                    List<WmsStorageInOutRecord> inRecordList = inOutRecordsList.stream().filter(record -> StorageInOutRecordType.IN.getCode().equals(record.getType()))
                            .sorted(Comparator.comparing(WmsStorageInOutRecord::getOccTime)).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(inRecordList)) {
                        try {
                            // 将物料编码 + 批号 + 出入库完成时间进行分组，出入库记录相同物料相同批次只有一条数据，️而出入库详情中可能会有多条数据
                            Map<String, List<WmsStorageInOutRecord>> inRecordMap = inRecordList.stream().collect(Collectors.groupingBy((inRecord) -> inRecord.getItemCode() + inRecord.getBatchNo() + inRecord.getCompletionTime()));
                            BigDecimal inQuantity = BigDecimal.ZERO;
                            BigDecimal inTotal = BigDecimal.ZERO;
                            for (Map.Entry<String, List<WmsStorageInOutRecord>> inRecordEntry : inRecordMap.entrySet()) {
                                List<WmsStorageInOutRecord> storageInRecordList = inRecordEntry.getValue();
                                inQuantity = inQuantity.add(storageInRecordList.get(0).getOccQuantity());
                                inTotal = inTotal.add(storageInRecordList.get(0).getOccTotalPrice());
                            }
                            item.setInQuantity(inQuantity);
                            item.setInTotal(inTotal);
                            // 入库数量若为0，则入库平均价格为0
                            BigDecimal inPrice = inQuantity.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO :
                                    inTotal.divide(inQuantity, 6, RoundingMode.HALF_UP);
                            item.setInPrice(inPrice);
                        } catch (Exception e) {
                            log.error("入库记录失败", e);
                            log.info("inRecordList ==========>{}", JSONObject.toJSONString(inRecordList));
                            throw new BusinessException("月结发生异常");
                        }
                    } else {
                        item.setInQuantity(BigDecimal.ZERO);
                        item.setInTotal(BigDecimal.ZERO);
                        item.setInPrice(BigDecimal.ZERO);
                    }

                    // 月结月份的出库记录
                    List<WmsStorageInOutRecord> outRecordList = inOutRecordsList.stream().filter(record -> StorageInOutRecordType.OUT.getCode().equals(record.getType()))
                            .sorted(Comparator.comparing(WmsStorageInOutRecord::getOccTime)).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(outRecordList)) {
                        try {
                            // 将物料编码 + 批号 + 出入库完成时间进行分组，出入库记录相同物料相同批次只有一条数据，️而出入库详情中可能会有多条数据
                            Map<String, List<WmsStorageInOutRecord>> outRecordMap = outRecordList.stream().collect(Collectors.groupingBy((outRecord) -> outRecord.getItemCode() + outRecord.getBatchNo() + outRecord.getCompletionTime()));
                            BigDecimal outQuantity = BigDecimal.ZERO;
                            BigDecimal outTotal = BigDecimal.ZERO;
                            for (Map.Entry<String, List<WmsStorageInOutRecord>> outRecordEntry : outRecordMap.entrySet()) {
                                List<WmsStorageInOutRecord> storageOutRecordList = outRecordEntry.getValue();
                                outQuantity = outQuantity.add(storageOutRecordList.get(0).getOccQuantity());
                                outTotal = outTotal.add(storageOutRecordList.get(0).getOccTotalPrice());
                            }
                            item.setOutQuantity(outQuantity);
                            item.setOutTotal(outTotal);
                            // 出库数量若为0，则出库平均价格为0
                            BigDecimal outPrice = outQuantity.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO :
                                    outTotal.divide(outQuantity, 6, RoundingMode.HALF_UP);
                            item.setOutPrice(outPrice);
                        } catch (Exception e) {
                            log.error("出库记录失败", e);
                            log.info("outRecordList ==========>{}", JSONObject.toJSONString(inRecordList));
                            throw new BusinessException("月结发生异常");
                        }
                    } else {
                        item.setOutPrice(BigDecimal.ZERO);
                        item.setOutQuantity(BigDecimal.ZERO);
                        item.setOutTotal(BigDecimal.ZERO);
                    }
                } else {
                    // 物料没有出入库记录
                    item.setInPrice(BigDecimal.ZERO);
                    item.setInQuantity(BigDecimal.ZERO);
                    item.setInTotal(BigDecimal.ZERO);
                    item.setOutPrice(BigDecimal.ZERO);
                    item.setOutQuantity(BigDecimal.ZERO);
                    item.setOutTotal(BigDecimal.ZERO);
                }
                item.setIsDelete(IsDeletedEnum.NORMAL.getVal());
                try {
                    // 期末库存 = 期初库存 + 入库总数量 - 出库总数量
                    item.setEndingQuantity(item.getOpeningQuantity().add(item.getInQuantity()).subtract(item.getOutQuantity()));
                    // 期末金额 = 期初金额 + 入库总金额 - 出库总金额; 金额保留2位小数（金额不能保留2位小数，不然出完的时候，金额会错误）
                    item.setEndingTotal(item.getOpeningTotal().add(item.getInTotal().subtract(item.getOutTotal())));
                    // 期末平均单价
                    BigDecimal endPrice = item.getEndingQuantity().compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO :
                            item.getEndingTotal().divide(item.getEndingQuantity(), 6, RoundingMode.HALF_UP);

                    // 期末数量为0，有尾差小于1分钱的，取0
                    BigDecimal difference = new BigDecimal(0.01);
                    if (BigDecimal.ZERO.compareTo(item.getEndingQuantity()) == 0
                            && BigDecimal.ZERO.compareTo(item.getEndingTotal()) != 0
                            && difference.compareTo(item.getEndingTotal()) > 0) {
                        // 将不足1分的尾差抹掉
                        log.info("数量出完，还剩的金额：{}", item.getEndingTotal());
                        item.setEndingTotal(BigDecimal.ZERO);
                    }

                    item.setEndingPrice(endPrice);
                    item.setCreateBy(SecurityUtils.getUserId());
                    item.setCreateTime(DateUtils.getNowDate());
                } catch (Exception e) {
                    log.error("计算期末数据出错===========>", e);
                    log.error("item info ===============>{}", JSONObject.toJSONString(item));
                    throw new BusinessException("月结发生异常");
                }
            }
            batchInsert(itemInfo);
            return AjaxResult.success();
        } catch (ParseException e) {
            log.error("月结月份格式错误 ", e);
            return AjaxResult.error("需要月结的月份格式错误");
        }
    }

    /**
     * 反月结
     *
     * @param wmsMonthCheckOut
     * @return
     */
    @Override
    public AjaxResult reverseSettlement(WmsMonthCheckOut wmsMonthCheckOut) {
        if (Strings.isEmpty(wmsMonthCheckOut.getYearMonths())) {
            return AjaxResult.error("请选择需要月结的月份");
        }
        if (Strings.isEmpty(wmsMonthCheckOut.getCompanyId())) {
            return AjaxResult.error("公司ID不能为空");
        }
        wmsMonthCheckOut.setUpdateBy(SecurityUtils.getUserId());
        wmsMonthCheckOut.setUpdateTime(DateUtils.getNowDate());
        wmsMonthCheckOutMapper.reverseSettlement(wmsMonthCheckOut);
        return AjaxResult.success();
    }


    private void batchInsert(List<WmsMonthCheckOut> itemInfo) {
        int insertLength = itemInfo.size();
        int i = 0;
        while (insertLength > 300) {
            wmsMonthCheckOutMapper.insertBatch(itemInfo.subList(i, i + 300));
            i = i + 300;
            insertLength = insertLength - 300;
        }
        if (insertLength > 0) {
            wmsMonthCheckOutMapper.insertBatch(itemInfo.subList(i, i + insertLength));
        }
    }


    private AjaxResult checkMonthSettlementParams(WmsMonthCheckOut wmsMonthCheckOut) {
        if (Strings.isEmpty(wmsMonthCheckOut.getYearMonths())) {
            return AjaxResult.error("请选择需要月结的月份");
        }
        if (Strings.isEmpty(wmsMonthCheckOut.getCompanyId())) {
            return AjaxResult.error("公司ID不能为空");
        }
        int num = wmsMonthCheckOutMapper.findCheckOut(wmsMonthCheckOut);
        if (num > 0) {
            return AjaxResult.error(String.format("月份：%s 已经月结，无需再月结", wmsMonthCheckOut.getYearMonths()));
        }

        String currentMonth = DateUtils.dateForYearMonth();
        if (wmsMonthCheckOut.getYearMonths().compareTo(currentMonth) > 0) {
            return AjaxResult.error("不能对未来的月份进行月结");
        }

        List<SysDictData> sysDictData = dictTypeService.selectDictDataByType(Constants.WMS_MONTH_CHECK_OUT_INIT);
        if (sysDictData.isEmpty()) {
            return AjaxResult.error("请先配置月结初始月份");
        }
        String initYearMonths = sysDictData.get(0).getDictValue();
        if (wmsMonthCheckOut.getYearMonths().compareTo(initYearMonths) < 0) {
            return AjaxResult.error(String.format("不能对月结初始月份（%s）之前的月份进行月结", initYearMonths));
        }
        // 月结月份不是初始月份时，需要检查上一月是否月结完成
        if (wmsMonthCheckOut.getYearMonths().compareTo(initYearMonths) > 0) {
            String lastMonth = DateUtils.getLastMonth(wmsMonthCheckOut.getYearMonths());
            if (lastMonth == null) {
                return AjaxResult.error("需要月结的月份格式错误");
            }
            WmsMonthCheckOut checkOut = new WmsMonthCheckOut();
            checkOut.setYearMonths(lastMonth);
            checkOut.setCompanyId(wmsMonthCheckOut.getCompanyId());
            int lastNum = wmsMonthCheckOutMapper.findCheckOut(checkOut);
            if (lastNum == 0) {
                return AjaxResult.error(String.format("月份：%s 未月结,请先月结该月份数据", lastMonth));
            }
        }
        return null;
    }
}
