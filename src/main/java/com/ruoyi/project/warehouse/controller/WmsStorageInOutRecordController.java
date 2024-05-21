package com.ruoyi.project.warehouse.controller;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.common.component.CodeService;
import com.ruoyi.project.system.service.IRoleWhService;
import com.ruoyi.project.warehouse.domain.PrintItemDetail;
import com.ruoyi.project.warehouse.domain.WmsStorageInOutRecord;
import com.ruoyi.project.warehouse.service.IWmsStorageInOutRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * 出入库记录Controller
 *
 * @author Rem
 * @date 2023-04-08
 */
@RestController
@RequestMapping("/warehouse/storageInOutRecord")
public class WmsStorageInOutRecordController extends BaseController {
    @Autowired
    private IWmsStorageInOutRecordService wmsStorageInOutRecordService;
    @Autowired
    private IRoleWhService roleWhService;

    @Autowired
    private CodeService codeService;

    /**
     * 查询出入库记录列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:storageInOutRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmsStorageInOutRecord wmsStorageInOutRecord) {
        wmsStorageInOutRecord.setCompanyId(getCompanyId());
        if (!SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
            List<String> warehouseIdList = roleWhService.selectLitByUser();
            if (warehouseIdList != null && warehouseIdList.size() > 0) {
                wmsStorageInOutRecord.setWarehouseIdList(warehouseIdList);
            } else {
                return getDataTable(new ArrayList<>());
            }
        }
        startPage();
        List<WmsStorageInOutRecord> list = wmsStorageInOutRecordService.selectWmsStorageInOutRecordList(wmsStorageInOutRecord);
        return getDataTable(list);
    }

    /**
     * 导出出入库记录列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:storageInOutRecord:export')")
    @Log(title = "出入库记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsStorageInOutRecord wmsStorageInOutRecord) {
        ExcelUtil<WmsStorageInOutRecord> util = new ExcelUtil<WmsStorageInOutRecord>(WmsStorageInOutRecord.class);
        wmsStorageInOutRecord.setCompanyId(getCompanyId());
        if (!SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
            List<String> warehouseIdList = roleWhService.selectLitByUser();
            if (warehouseIdList != null && warehouseIdList.size() > 0) {
                wmsStorageInOutRecord.setWarehouseIdList(warehouseIdList);
            } else {
                List<WmsStorageInOutRecord> list = new ArrayList<>();
                util.exportExcel(response, list, "出入库记录");
                return;
            }
        }
        List<WmsStorageInOutRecord> list = wmsStorageInOutRecordService.selectWmsStorageInOutRecordList(wmsStorageInOutRecord);
        // 生成单号 (报损、盘点除外)
        list.stream()
                .filter(record -> !record.getNoticeId().startsWith(Constants.BREAKAGE_DOC_CODE_PREFIX) && !record.getNoticeId().startsWith(Constants.INVENTORY_PLAN_PREFIX))
                .forEach(record -> {
                    String number = codeService.generatePrintCode(record.getNoticeId(), record.getWarehouseCode(), record.getBillType(), record.getCompletionTime());
                    record.setNumber(number);
                });
        // 若物料出入库为多个位置时，金额 = 发生时单价 * 数量
        list.stream()
                .filter(record -> !record.getOccQuantity().equals(record.getQuantity()))
                .forEach(record -> {
                    BigDecimal totalPrice = record.getOccPrice().multiply(record.getQuantity()).setScale(6, RoundingMode.HALF_UP);
                    record.setOccTotalPrice(totalPrice);
                });

        util.exportExcel(response, list, "出入库记录");
    }

    /**
     * 获取出入库记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('warehouse:storageInOutRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(wmsStorageInOutRecordService.selectWmsStorageInOutRecordById(id));
    }

    /**
     * 新增出入库记录
     */
    @PreAuthorize("@ss.hasPermi('warehouse:storageInOutRecord:add')")
    @Log(title = "出入库记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsStorageInOutRecord wmsStorageInOutRecord) {
        return toAjax(wmsStorageInOutRecordService.insertWmsStorageInOutRecord(wmsStorageInOutRecord));
    }

    /**
     * 修改出入库记录
     */
    @PreAuthorize("@ss.hasPermi('warehouse:storageInOutRecord:edit')")
    @Log(title = "出入库记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsStorageInOutRecord wmsStorageInOutRecord) {
        return toAjax(wmsStorageInOutRecordService.updateWmsStorageInOutRecord(wmsStorageInOutRecord));
    }

    /**
     * 删除出入库记录
     */
    @PreAuthorize("@ss.hasPermi('warehouse:storageInOutRecord:remove')")
    @Log(title = "出入库记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(wmsStorageInOutRecordService.deleteWmsStorageInOutRecordByIds(ids));
    }

    /**
     * 保存备注
     */
    @Log(title = "出入库记录", businessType = BusinessType.UPDATE)
    @PutMapping("/remark")
    public AjaxResult editRemark(@RequestBody List<PrintItemDetail> printItemDetails) {
        return toAjax(wmsStorageInOutRecordService.updateRemark(printItemDetails));
    }
}
