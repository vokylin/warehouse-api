package com.ruoyi.project.common.component;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.enums.WorkType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.warehouse.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 通知编号生成
 *
 * @author REM
 * @date 2023/04/06
 */
@Component
public class CodeService {

    @Autowired
    private IWmsReceiveNoticeService wmsReceiveNoticeService;

    @Autowired
    private IWmsDeliveryNoticeService wmsDeliveryNoticeService;

    @Autowired
    private IWmsExamineWorkService wmsExamineWorkService;

    @Autowired
    private IWmsWorkNoticeService wmsWorkNoticeService;

    @Autowired
    private IWmsBillOfLoadingService wmsBillOfLoadingService;

    @Autowired
    private IWmsStorageInOutRecordService wmsStorageInOutRecordService;

    @Autowired
    private IWmsBreakageDocService wmsBreakageDocService;

    @Autowired
    private IWmsInventoryPlanService wmsInventoryPlanService;

    /**
     * 生成入库通知单号
     *
     * @return {@link String}
     */
    public String generateReceiveNoticeCode() {
        // 查询当天通知数量
        int count = wmsReceiveNoticeService.selectWmsReceiveNoticeCountByToday();
        return Constants.RECEIVE_NOTICE_CODE_PREFIX + DateUtils.dateTimeNow(DateUtils.YYYYMMDD) + String.format("%05d", count + 1);
    }

    /**
     * 生成出库作业通知单号
     */
    public String generateDeliveryNoticeCode() {
        int count = wmsDeliveryNoticeService.selectWmsDeliveryNoticeCountByToday();
        return Constants.SEND_NOTICE_CODE_PREFIX + DateUtils.dateTimeNow(DateUtils.YYYYMMDD) + String.format("%05d", count + 1);
    }


    /**
     * 生成质检作业通知单号
     */
    public String generateExamineWorkCode() {
        int count = wmsExamineWorkService.selectWmsExamineWorkCountByToday();
        return Constants.EXAMINE_WORK_CODE_PREFIX + DateUtils.dateTimeNow(DateUtils.YYYYMMDD) + String.format("%05d", count + 1);
    }

    /**
     * 生成上架作业通知单号
     */
    public String generateShelfWorkCode() {
        int count = wmsWorkNoticeService.selectWmsWorkNoticeCountByToday(WorkType.SHELF.getCode());
        return Constants.SHELF_NOTICE_CODE_PREFIX + DateUtils.dateTimeNow(DateUtils.YYYYMMDD) + String.format("%05d", count + 1);
    }

    /**
     * 生成拣货作业通知单号
     */
    public String generatePickWorkCode() {
        int count = wmsWorkNoticeService.selectWmsWorkNoticeCountByToday(WorkType.PICK.getCode());
        return Constants.PICK_NOTICE_CODE_PREFIX + DateUtils.dateTimeNow(DateUtils.YYYYMMDD) + String.format("%05d", count + 1);
    }


    /**
     * 生成发运通知单号
     *
     * @return {@link String}
     */
    public String generateBillOfLoadingCode() {
        int count = wmsBillOfLoadingService.selectWmsBillOfLoadingCountByToday();
        return Constants.BILL_OF_LOADING_CODE_PREFIX + DateUtils.dateTimeNow(DateUtils.YYYYMMDD) + String.format("%05d", count + 1);
    }

    /**
     * 生成退货作业通知单号
     */
    public String generateRefundWorkCode() {
        int count = wmsWorkNoticeService.selectWmsWorkNoticeCountByToday(WorkType.REFUND.getCode());
        return Constants.RETURN_NOTICE_CODE_PREFIX + DateUtils.dateTimeNow(DateUtils.YYYYMMDD) + String.format("%05d", count + 1);
    }

    /**
     * 生成打印编号
     *
     * @param warehouseCode 仓库代码
     * @param billType      比尔类型
     * @return {@link String}
     */
    public String generatePrintCode(String noticeId, String warehouseCode, Integer billType, Date occTime) {
        int count = wmsStorageInOutRecordService.getBillCode(noticeId, warehouseCode, billType, occTime);
        String dateStr = DateUtils.parseDateToStr(DateUtils.YYYYMM, occTime);
        return warehouseCode + "-" + dateStr + "-" + String.format("%03d", count);
    }

    /**
     * 生成报损单号
     */
    public String generateBreakageDocCode() {
        int count = wmsBreakageDocService.selectCountByToday();
        return Constants.BREAKAGE_DOC_CODE_PREFIX + DateUtils.dateTimeNow(DateUtils.YYYYMMDD) + String.format("%05d", count + 1);
    }

    public String generateInventoryPlanCode() {
        int count = wmsInventoryPlanService.selectCountByToday();
        return Constants.INVENTORY_PLAN_PREFIX + DateUtils.dateTimeNow(DateUtils.YYYYMMDD) + String.format("%05d", count + 1);
    }


}
