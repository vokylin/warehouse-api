package com.ruoyi.project.warehouse.controller;

import com.ruoyi.common.enums.ReceiveNoticeStatus;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.warehouse.domain.WmsReceiveNotice;
import com.ruoyi.project.warehouse.service.IWmsReceiveNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 入库通知Controller
 *
 * @author Rem
 * @date 2023-03-31
 */
@RestController
@RequestMapping("/warehouse/wmsReceiveNotice")
public class WmsReceiveNoticeController extends BaseController {
    @Autowired
    private IWmsReceiveNoticeService wmsReceiveNoticeService;

    /**
     * 查询入库通知列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:receiveNotice:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmsReceiveNotice wmsReceiveNotice) {
        wmsReceiveNotice.setCompanyId(getCompanyId());
        startPage();
        List<WmsReceiveNotice> list = wmsReceiveNoticeService.selectWmsReceiveNoticeList(wmsReceiveNotice);
        return getDataTable(list);
    }

    /**
     * 导出入库通知列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:receiveNotice:export')")
    @Log(title = "入库通知", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmsReceiveNotice wmsReceiveNotice) {
        List<WmsReceiveNotice> list = wmsReceiveNoticeService.selectWmsReceiveNoticeList(wmsReceiveNotice);
        ExcelUtil<WmsReceiveNotice> util = new ExcelUtil<WmsReceiveNotice>(WmsReceiveNotice.class);
        util.exportExcel(response, list, "入库通知数据");
    }

    /**
     * 获取入库通知详细信息
     */
    @PreAuthorize("@ss.hasPermi('warehouse:receiveNotice:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(wmsReceiveNoticeService.selectWmsReceiveNoticeById(id));
    }

    /**
     * 新增入库通知
     */
    @PreAuthorize("@ss.hasPermi('warehouse:receiveNotice:add')")
    @Log(title = "入库通知", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsReceiveNotice wmsReceiveNotice) {
        return toAjax(wmsReceiveNoticeService.insertWmsReceiveNotice(wmsReceiveNotice));
    }

    /**
     * 修改入库通知
     */
    @PreAuthorize("@ss.hasPermi('warehouse:receiveNotice:edit')")
    @Log(title = "入库通知", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsReceiveNotice wmsReceiveNotice) {
        return toAjax(wmsReceiveNoticeService.updateWmsReceiveNotice(wmsReceiveNotice));
    }

    /**
     * 删除入库通知
     */
    @PreAuthorize("@ss.hasPermi('warehouse:receiveNotice:remove')")
    @Log(title = "入库通知", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(wmsReceiveNoticeService.deleteWmsReceiveNoticeByIds(ids));
    }

    /**
     * 激活入库通知
     */
    @PreAuthorize("@ss.hasPermi('warehouse:receiveNotice:active')")
    @Log(title = "入库通知", businessType = BusinessType.UPDATE)
    @PutMapping("/active")
    public AjaxResult active(@RequestBody WmsReceiveNotice wmsReceiveNotice) {

        WmsReceiveNotice receiveNotice = wmsReceiveNoticeService.selectWmsReceiveNoticeById(wmsReceiveNotice.getId());
        if (receiveNotice == null) {
            return error("入库通知不存在");
        }
        if (!receiveNotice.getStatus().equals(ReceiveNoticeStatus.WAIT_ACTIVE.getCode())) {
            return error("激活失败, 入库通知状态不是待激活状态");
        }
        wmsReceiveNotice.setUpdateBy(getUserId());
        return toAjax(wmsReceiveNoticeService.active(wmsReceiveNotice));
    }

    /**
     * 反激活
     */
    @PreAuthorize("@ss.hasPermi('warehouse:receiveNotice:inactive')")
    @Log(title = "入库通知", businessType = BusinessType.UPDATE)
    @PutMapping("/inactive")
    public AjaxResult inactive(@RequestBody String[] ids) {
        return wmsReceiveNoticeService.inactive(ids);
    }


    /**
     * 作废入库通知
     */
    @PreAuthorize("@ss.hasPermi('warehouse:receiveNotice:invalid')")
    @Log(title = "入库通知", businessType = BusinessType.UPDATE)
    @PutMapping("/invalid")
    public AjaxResult invalid(@RequestBody WmsReceiveNotice wmsReceiveNotice) throws Exception {
        return wmsReceiveNoticeService.invalid(wmsReceiveNotice);
    }

    /**
     * 收货确认
     */
    @PreAuthorize("@ss.hasPermi('warehouse:receiveNotice:confirm')")
    @Log(title = "收货确认", businessType = BusinessType.UPDATE)
    @PutMapping("/confirm")
    public AjaxResult confirm(@RequestBody String[] ids) throws Exception {
        return wmsReceiveNoticeService.confirm(ids);
    }

}
