package com.ruoyi.project.warehouse.service;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.enums.BillType;
import com.ruoyi.common.enums.DocumentType;
import com.ruoyi.common.exception.business.BusinessException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.common.component.CodeService;
import com.ruoyi.project.system.domain.Company;
import com.ruoyi.project.system.domain.SysDictData;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.mapper.SysUserMapper;
import com.ruoyi.project.system.service.ICompanyService;
import com.ruoyi.project.system.service.ISysDictTypeService;
import com.ruoyi.project.warehouse.domain.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class PrintService {

    @Autowired
    private IWmsReceiveNoticeService wmsReceiveNoticeService;

    @Autowired
    private IWmsStorageInOutDetailService wmsStorageInOutDetailService;

//    @Autowired
//    private OrderFeignService orderFeignService;

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private IWmsDeliveryNoticeService wmsDeliveryNoticeService;

    @Autowired
    private IWmsExamineWorkService wmsExamineWorkService;

    @Autowired
    private CodeService codeService;

    @Autowired
    private IWmsReceiveNoticeDetailService wmsReceiveNoticeDetailService;

    @Autowired
    private IWmsBreakageDocService wmsBreakageDocService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private ISysDictTypeService dictTypeService;

    public PrintList getPrintInfo(Integer documentType, String noticeId) {
        DocumentType documentTypeEnum = DocumentType.getDocumentType(documentType);
        if (documentTypeEnum == null) {
            return null;
        }
        switch (documentTypeEnum) {
            // 进货验收单
            case PURCHASE_ACCEPTANCE:
                return getPurchaseAcceptancePrintInfo(noticeId);
            //  入库单
            case WAREHOUSE_ENTRY:
                return getWarehouseEntryPrintInfo(noticeId);
            //  退料单
            case RETURN_MATERIAL:
                return getReturnMaterialPrintInfo(noticeId);
            //  领料单
            case PICKING_LIST:
                return getPickingListPrintInfo(noticeId);
            default:
                return null;
        }
    }

    /**
     * 领料单打印信息
     *
     * @param noticeId 注意id
     * @return {@link List}<{@link PrintList}>
     */
    private PrintList getPickingListPrintInfo(String noticeId) {
        WmsDeliveryNotice wmsDeliveryNotice = wmsDeliveryNoticeService.selectWmsDeliveryNoticeById(noticeId);
        // 根据业务单号查询入库记录
        List<StorageRecordVo> storageRecordVos = wmsStorageInOutDetailService.selectByNoticeId(noticeId, BillType.RECEIVE.getCode());
        if (null == storageRecordVos || storageRecordVos.size() == 0) {
            return null;
        }
        Company company = companyService.selectCompanyById(wmsDeliveryNotice.getCompanyId());
        // 根据仓库分组
        Map<String, List<StorageRecordVo>> recordMap = storageRecordVos.stream().collect(Collectors.groupingBy((storageRecordVo) -> storageRecordVo.getWarehouseCode() + "/" + storageRecordVo.getWarehouseName()));

        PrintList printInfos = new PrintList();
        for (Map.Entry<String, List<StorageRecordVo>> entry : recordMap.entrySet()) {
            String warehouseCode = entry.getKey().split("/")[0];
            String warehouseName = entry.getKey().split("/")[1];
            BigDecimal totalAmount = BigDecimal.ZERO;
            List<PrintItemDetail> printItemDetailList = new ArrayList<>();

            // 相同批次 相同编码的物料合并
            Map<String, List<StorageRecordVo>> itemMap = entry.getValue().stream().collect(Collectors.groupingBy((storageRecordVo) -> storageRecordVo.getItemCode() + storageRecordVo.getBatchNo()));
            for (Map.Entry<String, List<StorageRecordVo>> itemEntry : itemMap.entrySet()) {
                List<StorageRecordVo> storageRecordVoList = itemEntry.getValue();
                String recordDetailIds = storageRecordVoList.stream().map(StorageRecordVo::getRecordDetailId).collect(Collectors.joining(","));
                BigDecimal price = BigDecimal.ZERO;
                if (null != storageRecordVoList.get(0).getOccPrice()) {
                    price = storageRecordVoList.get(0).getOccPrice();
                }
                BigDecimal amount = storageRecordVoList.get(0).getOccTotalPrice();
                PrintItemDetail printItemDetail = new PrintItemDetail();
                printItemDetail.setRecordDetailId(recordDetailIds);
                printItemDetail.setItemCode(storageRecordVoList.get(0).getItemCode());
                printItemDetail.setItemName(storageRecordVoList.get(0).getItemName());
                printItemDetail.setUnit(storageRecordVoList.get(0).getUnit());
                printItemDetail.setPrice(price);
                printItemDetail.setQuantity(storageRecordVoList.get(0).getOccQuantity());
                printItemDetail.setAmount(amount);
                printItemDetail.setBatchNo(storageRecordVoList.get(0).getBatchNo());
                printItemDetail.setModel(storageRecordVoList.get(0).getSpec());
                printItemDetail.setRemark(storageRecordVoList.get(0).getRemark());
                totalAmount = totalAmount.add(amount);
                printItemDetailList.add(printItemDetail);
            }
            SysUser sysUser = sysUserMapper.selectUserById(wmsDeliveryNotice.getUpdateBy());
            String warehouseOperator = "";
            if (sysUser != null) {
                warehouseOperator = sysUser.getNickName();
            }
            List<SysDictData> sysDictData = dictTypeService.selectDictDataByType(Constants.DEPT_MANAGER);
            Map<String, String> dictMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(sysDictData)) {
                dictMap = sysDictData.stream().collect(Collectors.toMap(SysDictData::getDictLabel, SysDictData::getDictValue, (oldVal, newVal) -> newVal));
            }
            printInfos.add(PrintInfo.builder()
                    .documentType(DocumentType.PURCHASE_ACCEPTANCE.getCode())
                    .dateTime(wmsDeliveryNotice.getDeliveryEndTime())
                    .documentNumber(codeService.generatePrintCode(noticeId, warehouseCode, BillType.RECEIVE.getCode(), wmsDeliveryNotice.getDeliveryEndTime()))
                    .warehouseName(warehouseName)
                    .warehouseCode(warehouseCode)
                    .warehouseOperator(warehouseOperator)
                    .warehouseOperatorManager(dictMap.get("财务部"))
                    .totalAmount(totalAmount)
                    .deptName(wmsDeliveryNotice.getDeptName())
                    .operator(wmsDeliveryNotice.getNoticeUser())
                    .operatorManager(dictMap.get(wmsDeliveryNotice.getDeptName()))
                    .companyName(company.getName())
                    .printItemDetailList(printItemDetailList)
                    .build());
        }
        return printInfos;
    }

    /**
     * 获取进货验收单打印信息
     *
     * @param noticeId
     * @return
     */
    private PrintList getPurchaseAcceptancePrintInfo(String noticeId) {
        PrintList printInfos = new PrintList();

        WmsReceiveNotice wmsReceiveNotice = wmsReceiveNoticeService.selectWmsReceiveNoticeById(noticeId);
        // 根据业务单号查询入库记录
        List<StorageRecordVo> storageRecordVos = wmsStorageInOutDetailService.selectByNoticeId(noticeId, DocumentType.PURCHASE_ACCEPTANCE.getCode());
        if (null == storageRecordVos || storageRecordVos.size() == 0) {
            return null;
        }
        Company company = companyService.selectCompanyById(wmsReceiveNotice.getCompanyId());
        // 根据仓库分组
        Map<String, List<StorageRecordVo>> recordMap = storageRecordVos.stream().collect(Collectors.groupingBy((storageRecordVo) -> storageRecordVo.getWarehouseCode() + "/" + storageRecordVo.getWarehouseName()));

        for (Map.Entry<String, List<StorageRecordVo>> entry : recordMap.entrySet()) {

            String warehouseCode = entry.getKey().split("/")[0];
            String warehouseName = entry.getKey().split("/")[1];
            BigDecimal totalAmount = BigDecimal.ZERO;
            List<PrintItemDetail> printItemDetailList = new ArrayList<>();

            // 相同批次 相同编码的物料合并
            Map<String, List<StorageRecordVo>> itemMap = entry.getValue().stream().collect(Collectors.groupingBy((storageRecordVo) -> storageRecordVo.getItemCode() + storageRecordVo.getBatchNo()));

            for (Map.Entry<String, List<StorageRecordVo>> itemEntry : itemMap.entrySet()) {
                List<StorageRecordVo> storageRecordVoList = itemEntry.getValue();
                BigDecimal price = storageRecordVoList.get(0).getOccPrice();
                BigDecimal quantity = storageRecordVoList.stream().map(StorageRecordVo::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal amount = price.multiply(quantity);
                PrintItemDetail printItemDetail = new PrintItemDetail();
                printItemDetail.setItemCode(storageRecordVoList.get(0).getItemCode());
                printItemDetail.setItemName(storageRecordVoList.get(0).getItemName());
                printItemDetail.setUnit(storageRecordVoList.get(0).getUnit());
                printItemDetail.setPrice(price);
                printItemDetail.setQuantity(quantity);
                printItemDetail.setAmount(amount);
                printItemDetail.setBatchNo(storageRecordVoList.get(0).getBatchNo());
                printItemDetail.setModel(storageRecordVoList.get(0).getSpec());
                printItemDetail.setPurchaseOrderNumber(wmsReceiveNotice.getBusinessId());
                totalAmount = totalAmount.add(amount);
                printItemDetailList.add(printItemDetail);
            }
            printInfos.add(PrintInfo.builder()
                    .documentType(DocumentType.PURCHASE_ACCEPTANCE.getCode())
                    .dateTime(wmsReceiveNotice.getEndTime())
                    .documentNumber(codeService.generatePrintCode(noticeId, warehouseCode, BillType.RECEIVE.getCode(), wmsReceiveNotice.getStartTime()))
                    .warehouseName(warehouseName)
                    .warehouseCode(warehouseCode)
                    .warehouseOperator(SecurityUtils.getNickName())
                    .totalAmount(totalAmount)
                    .supply(wmsReceiveNotice.getFromName())
                    .operator(SecurityUtils.getNickName())
                    .companyName(company.getName())
                    .printItemDetailList(printItemDetailList)
                    .build());
        }
        return printInfos;
    }


    /**
     * 获取入库单打印信息
     *
     * @param noticeId
     * @return
     */
    private PrintList getWarehouseEntryPrintInfo(String noticeId) {
        PrintList printInfos = new PrintList();

        WmsReceiveNotice wmsReceiveNotice = wmsReceiveNoticeService.selectWmsReceiveNoticeById(noticeId);
        // 根据业务单号查询入库记录
        List<StorageRecordVo> storageRecordVos = wmsStorageInOutDetailService.selectByNoticeId(noticeId, BillType.IN.getCode());
        if (null == storageRecordVos || storageRecordVos.size() == 0) {
            return null;
        }
        Company company = companyService.selectCompanyById(wmsReceiveNotice.getCompanyId());
        // 根据仓库分组
        Map<String, List<StorageRecordVo>> recordMap = storageRecordVos.stream().collect(Collectors.groupingBy((storageRecordVo) -> storageRecordVo.getWarehouseCode() + "/" + storageRecordVo.getWarehouseName()));

        for (Map.Entry<String, List<StorageRecordVo>> entry : recordMap.entrySet()) {

            String warehouseCode = entry.getKey().split("/")[0];
            String warehouseName = entry.getKey().split("/")[1];
            BigDecimal totalAmount = BigDecimal.ZERO;
            List<PrintItemDetail> printItemDetailList = new ArrayList<>();

            // 相同批次 相同编码的物料合并
            Map<String, List<StorageRecordVo>> itemMap = entry.getValue().stream().collect(Collectors.groupingBy((storageRecordVo) -> storageRecordVo.getItemCode() + storageRecordVo.getBatchNo()));

            for (Map.Entry<String, List<StorageRecordVo>> itemEntry : itemMap.entrySet()) {
                List<StorageRecordVo> storageRecordVoList = itemEntry.getValue();
                String recordDetailIds = storageRecordVoList.stream().map(StorageRecordVo::getRecordDetailId).collect(Collectors.joining(","));
                BigDecimal price = BigDecimal.ZERO;
                if (null != storageRecordVoList.get(0).getOccPrice()) {
                    price = storageRecordVoList.get(0).getOccPrice();
                }
                BigDecimal amount = storageRecordVoList.get(0).getOccTotalPrice();
                PrintItemDetail printItemDetail = new PrintItemDetail();
                printItemDetail.setRecordDetailId(recordDetailIds);
                printItemDetail.setItemCode(storageRecordVoList.get(0).getItemCode());
                printItemDetail.setItemName(storageRecordVoList.get(0).getItemName());
                printItemDetail.setUnit(storageRecordVoList.get(0).getUnit());
                printItemDetail.setPrice(price);
                printItemDetail.setQuantity(storageRecordVoList.get(0).getOccQuantity());
                printItemDetail.setAmount(amount);
                printItemDetail.setBatchNo(storageRecordVoList.get(0).getBatchNo());
                printItemDetail.setModel(storageRecordVoList.get(0).getSpec());
                printItemDetail.setPurchaseOrderNumber(wmsReceiveNotice.getBusinessId());
                printItemDetail.setRemark(storageRecordVoList.get(0).getRemark());
                totalAmount = totalAmount.add(amount);
                printItemDetailList.add(printItemDetail);
            }

            SysUser sysUser = sysUserMapper.selectUserById(wmsReceiveNotice.getUpdateBy());
            String warehouseOperator = "";
            if (sysUser != null) {
                warehouseOperator = sysUser.getNickName();
            }
            List<SysDictData> sysDictData = dictTypeService.selectDictDataByType(Constants.DEPT_MANAGER);
            Map<String, String> dictMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(sysDictData)) {
                dictMap = sysDictData.stream().collect(Collectors.toMap(SysDictData::getDictLabel, SysDictData::getDictValue, (oldVal, newVal) -> newVal));
            }
            printInfos.add(PrintInfo.builder()
                    .documentType(DocumentType.PURCHASE_ACCEPTANCE.getCode())
                    .dateTime(wmsReceiveNotice.getEndTime())
                    .documentNumber(codeService.generatePrintCode(noticeId, warehouseCode, BillType.IN.getCode(), wmsReceiveNotice.getEndTime()))
                    .warehouseName(warehouseName)
                    .warehouseCode(warehouseCode)
                    .deptName(wmsReceiveNotice.getDeptName())
                    .warehouseOperator(warehouseOperator)
                    .warehouseOperatorManager(dictMap.get("财务部"))
                    .totalAmount(totalAmount)
                    .supply(wmsReceiveNotice.getFromName())
                    .operator(wmsReceiveNotice.getNoticeUser())
                    .operatorManager(dictMap.get(wmsReceiveNotice.getDeptName()))
                    .printItemDetailList(printItemDetailList)
                    .companyName(company.getName())
                    .build());
        }
        return printInfos;
    }

    /**
     * 获取退料单打印信息
     *
     * @param noticeId
     * @return
     */
    private PrintList getReturnMaterialPrintInfo(String noticeId) {
        PrintList printInfos = new PrintList();

        WmsReceiveNotice wmsReceiveNotice = wmsReceiveNoticeService.selectWmsReceiveNoticeById(noticeId);
        // 根据业务单号查询入库记录
        List<StorageRecordVo> storageRecordVos = wmsStorageInOutDetailService.selectByNoticeId(noticeId, BillType.RETURN.getCode());
        if (null == storageRecordVos || storageRecordVos.size() == 0) {
            return null;
        }
        Company company = companyService.selectCompanyById(wmsReceiveNotice.getCompanyId());

        // 根据仓库分组
        Map<String, List<StorageRecordVo>> recordMap = storageRecordVos.stream().collect(Collectors.groupingBy((storageRecordVo) -> storageRecordVo.getWarehouseCode() + "/" + storageRecordVo.getWarehouseName()));
        for (Map.Entry<String, List<StorageRecordVo>> entry : recordMap.entrySet()) {
            String warehouseCode = entry.getKey().split("/")[0];
            String warehouseName = entry.getKey().split("/")[1];
            BigDecimal totalAmount = BigDecimal.ZERO;
            List<PrintItemDetail> printItemDetailList = new ArrayList<>();

            // 相同批次 相同编码的物料合并
            Map<String, List<StorageRecordVo>> itemMap = entry.getValue().stream().collect(Collectors.groupingBy((storageRecordVo) -> storageRecordVo.getItemCode() + storageRecordVo.getBatchNo()));

            for (Map.Entry<String, List<StorageRecordVo>> itemEntry : itemMap.entrySet()) {
                List<StorageRecordVo> storageRecordVoList = itemEntry.getValue();
                String recordDetailIds = storageRecordVoList.stream().map(StorageRecordVo::getRecordDetailId).collect(Collectors.joining(","));
                BigDecimal price = storageRecordVoList.get(0).getOccPrice();
                BigDecimal amount = storageRecordVoList.get(0).getOccTotalPrice();
                PrintItemDetail printItemDetail = new PrintItemDetail();
                printItemDetail.setRecordDetailId(recordDetailIds);
                printItemDetail.setItemCode(storageRecordVoList.get(0).getItemCode());
                printItemDetail.setItemName(storageRecordVoList.get(0).getItemName());
                printItemDetail.setQuantity(storageRecordVoList.get(0).getOccQuantity());
                printItemDetail.setUnit(storageRecordVoList.get(0).getUnit());
                printItemDetail.setPrice(price);
                printItemDetail.setAmount(amount);
                printItemDetail.setBatchNo(storageRecordVoList.get(0).getBatchNo());
                printItemDetail.setModel(storageRecordVoList.get(0).getSpec());
                printItemDetail.setPurchaseOrderNumber(wmsReceiveNotice.getBusinessId());
                printItemDetail.setRemark(storageRecordVoList.get(0).getRemark());
                totalAmount = totalAmount.add(amount);
                printItemDetailList.add(printItemDetail);
            }
            SysUser sysUser = sysUserMapper.selectUserById(wmsReceiveNotice.getUpdateBy());
            String warehouseOperator = "";
            if (sysUser != null) {
                warehouseOperator = sysUser.getNickName();
            }
            List<SysDictData> sysDictData = dictTypeService.selectDictDataByType(Constants.DEPT_MANAGER);
            Map<String, String> dictMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(sysDictData)) {
                dictMap = sysDictData.stream().collect(Collectors.toMap(SysDictData::getDictLabel, SysDictData::getDictValue, (oldVal, newVal) -> newVal));
            }
            printInfos.add(PrintInfo.builder()
                    .documentType(DocumentType.PURCHASE_ACCEPTANCE.getCode())
                    .dateTime(wmsReceiveNotice.getEndTime())
                    .documentNumber(codeService.generatePrintCode(noticeId, warehouseCode, BillType.RETURN.getCode(), wmsReceiveNotice.getEndTime()))
                    .warehouseName(warehouseName)
                    .warehouseCode(warehouseCode)
                    .warehouseOperator(warehouseOperator)
                    .warehouseOperatorManager(dictMap.get("财务部"))
                    .totalAmount(totalAmount)
                    .supply(wmsReceiveNotice.getFromName())
                    .deptName(wmsReceiveNotice.getDeptName())
                    .operator(wmsReceiveNotice.getNoticeUser())
                    .operatorManager(dictMap.get(wmsReceiveNotice.getDeptName()))
                    .companyName(company.getName())
                    .printItemDetailList(printItemDetailList)
                    .build());
        }
        return printInfos;
    }


    /**
     * 进料验收单打印
     *
     * @param examineWorkId examineWorkId
     * @return {@link String}
     */
    public FeedstockFormPrintDetailVO getFeedstockPrintInfo(String examineWorkId) throws Exception {
        WmsExamineWork wmsExamineWork = wmsExamineWorkService.selectWmsExamineWorkById(examineWorkId);
        if (null == wmsExamineWork) {
            throw new BusinessException("未查询到质检信息");
        }
        String noticeDetailId = wmsExamineWork.getNoticeDetailId();
        if (StringUtils.isBlank(noticeDetailId)) {
            throw new BusinessException("入库通知详情id为空");
        }
        WmsReceiveNoticeDetail receiveNoticeDetail = wmsReceiveNoticeDetailService.selectWmsReceiveNoticeDetailById(noticeDetailId);
        if (null == receiveNoticeDetail) {
            throw new BusinessException("未查询到入库通知详情信息");
        }
////        FeedstockFormPrintResult result = orderFeignService.getFeedstockPrintInfo(receiveNoticeDetail.getCheckAcceptId());
//        if (null == result) {
//            throw new BusinessException("查询打印信息失败");
//        }
        return null;
    }


    /**
     * 获取报损单打印信息
     *
     * @param noticeId 注意id
     * @return {@link BreakageDocVO}
     */
    public List<BreakageDocPrintInfo> getBreakageDocPrintInfo(String noticeId) {
        return wmsBreakageDocService.getBreakageDocPrintInfo(noticeId);
    }

}
