package com.ruoyi.project.warehouse.service.impl;

import com.ruoyi.common.enums.ExamineWorkNoticeStatus;
import com.ruoyi.common.enums.ReciveItemStatus;
import com.ruoyi.common.enums.RefundWorkNoticeStatus;
import com.ruoyi.common.enums.WorkType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.component.CodeService;
import com.ruoyi.project.common.component.ReceiveNoticeManager;
import com.ruoyi.project.common.entity.ExamineTaskDTO;
import com.ruoyi.project.warehouse.domain.*;
import com.ruoyi.project.warehouse.mapper.WmsExamineWorkMapper;
import com.ruoyi.project.warehouse.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 质检作业Service业务层处理
 *
 * @author Rem
 * @date 2023-04-07
 */
@Service
public class WmsExamineWorkServiceImpl implements IWmsExamineWorkService {
    @Autowired
    private WmsExamineWorkMapper wmsExamineWorkMapper;

//    @Autowired
//    private UserFeignService userFeignService;

    @Autowired
    private IWmsWorkNoticeService wmsWorkNoticeService;

    @Autowired
    private IWmsWorkNoticeDetailService wmsWorkNoticeDetailService;

    @Autowired
    private IWmsReceiveItemDetailService wmsReceiveItemDetailService;

    @Autowired
    private IWmsReceiveNoticeDetailService wmsReceiveNoticeDetailService;

    @Autowired
    private IWmsReceiveNoticeService wmsReceiveNoticeService;

    @Autowired
    private ReceiveNoticeManager receiveNoticeManager;

    @Autowired
    private CodeService codeService;

    /**
     * 查询质检作业
     *
     * @param id 质检作业主键
     * @return 质检作业
     */
    @Override
    public WmsExamineWork selectWmsExamineWorkById(String id) {
        return wmsExamineWorkMapper.selectWmsExamineWorkById(id);
    }

    /**
     * 查询质检作业列表
     *
     * @param wmsExamineWork 质检作业
     * @return 质检作业
     */
    @Override
    public List<WmsExamineWork> selectWmsExamineWorkList(WmsExamineWork wmsExamineWork) {
        return wmsExamineWorkMapper.selectWmsExamineWorkList(wmsExamineWork);
    }

    /**
     * 新增质检作业
     *
     * @param wmsExamineWork 质检作业
     * @return 结果
     */
    @Override
    public int insertWmsExamineWork(WmsExamineWork wmsExamineWork) {
        wmsExamineWork.setCreateTime(DateUtils.getNowDate());
        return wmsExamineWorkMapper.insertWmsExamineWork(wmsExamineWork);
    }

    /**
     * 修改质检作业
     *
     * @param wmsExamineWork 质检作业
     * @return 结果
     */
    @Override
    public int updateWmsExamineWork(WmsExamineWork wmsExamineWork) {
        wmsExamineWork.setUpdateTime(DateUtils.getNowDate());
        return wmsExamineWorkMapper.updateWmsExamineWork(wmsExamineWork);
    }

    /**
     * 批量删除质检作业
     *
     * @param ids 需要删除的质检作业主键
     * @return 结果
     */
    @Override
    public int deleteWmsExamineWorkByIds(String[] ids) {
        return wmsExamineWorkMapper.deleteWmsExamineWorkByIds(ids);
    }

    /**
     * 删除质检作业信息
     *
     * @param id 质检作业主键
     * @return 结果
     */
    @Override
    public int deleteWmsExamineWorkById(String id) {
        return wmsExamineWorkMapper.deleteWmsExamineWorkById(id);
    }

    /**
     * 通知质检
     *
     * @param wmsExamineWork wms检查工作
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult noticeExamine(WmsExamineWork wmsExamineWork) throws Exception {
        for (String id : wmsExamineWork.getIds()) {
            WmsExamineWork wmsExamineWorkInfo = wmsExamineWorkMapper.selectWmsExamineWorkById(id);

            // 检查状态
            if (wmsExamineWorkInfo.getOperateStatus().equals(ExamineWorkNoticeStatus.NOTIFIED.getCode())) {
                return AjaxResult.error("质检作业已通知");
            }

            wmsExamineWorkInfo.setOperateStatus(ExamineWorkNoticeStatus.NOTIFIED.getCode());
            wmsExamineWorkInfo.setStatus(ReciveItemStatus.INSPECTION_IN_PROGRESS.getCode());
            wmsExamineWorkInfo.setUpdateBy(SecurityUtils.getUserId());
            wmsExamineWorkInfo.setUpdateTime(DateUtils.getNowDate());
            wmsExamineWorkMapper.updateWmsExamineWork(wmsExamineWorkInfo);
            // 通知质检
            this.pushNotifications(wmsExamineWorkInfo);
        }
        return AjaxResult.success();
    }

    /**
     * 统计今日质检作业数量
     *
     * @return int
     */
    @Override
    public int selectWmsExamineWorkCountByToday() {
        return wmsExamineWorkMapper.selectWmsExamineWorkCountByToday();
    }

    /**
     * 退货作业
     *
     * @param wmsExamineWork wms检查工作
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult refundWork(WmsExamineWork wmsExamineWork) {
        // TODO: 退货数量问题
        BigDecimal refundQuantity = wmsExamineWork.getFailQuantity();
        if (!(refundQuantity.compareTo(BigDecimal.ZERO) > 0)) {
            return AjaxResult.error("退货数量有误");
        }

        WmsExamineWork work = wmsExamineWorkMapper.selectWmsExamineWorkById(wmsExamineWork.getId());
        if (work == null) {
            return AjaxResult.error("质检作业不存在");
        }
        if (work.getOperateStatus().equals(ExamineWorkNoticeStatus.RETURNED.getCode())) {
            return AjaxResult.error("质检作业已退货, 请勿重复操作");
        }

        // 更新质检作业状态
        wmsExamineWork.setOperateStatus(ExamineWorkNoticeStatus.RETURNED.getCode());
        wmsExamineWork.setUpdateBy(SecurityUtils.getUserId());
        wmsExamineWork.setUpdateTime(DateUtils.getNowDate());
        wmsExamineWorkMapper.updateWmsExamineWork(wmsExamineWork);


        ReceiveNoticeInfo receiveNoticeInfo = receiveNoticeManager.getReceiveNoticeInfoByItemDetailId(wmsExamineWork.getReceiveItemDetailId());

        WmsReceiveNotice wmsReceiveNotice = receiveNoticeInfo.getWmsReceiveNotice();
        WmsReceiveNoticeDetail wmsReceiveNoticeDetail = receiveNoticeInfo.getWmsReceiveNoticeDetail();

        // 生成退货作业通知
        WmsWorkNotice wmsWorkNotice = new WmsWorkNotice();
        wmsWorkNotice.setId(codeService.generateRefundWorkCode());
        wmsWorkNotice.setNoticeId(wmsExamineWork.getId());
        wmsWorkNotice.setCompanyId(wmsReceiveNotice.getCompanyId());
        wmsWorkNotice.setReceiveSendType(wmsReceiveNotice.getReceiveSendType());
        wmsWorkNotice.setType(WorkType.REFUND.getCode());
        wmsWorkNotice.setStatus(RefundWorkNoticeStatus.WAIT_REFUND.getCode());
        wmsWorkNotice.setCreateBy(SecurityUtils.getUserId());
        wmsWorkNotice.setCreateTime(DateUtils.getNowDate());
        wmsWorkNoticeService.insertWmsWorkNotice(wmsWorkNotice);

        // 退货作业明细
        WmsWorkNoticeDetail workNoticeDetail = new WmsWorkNoticeDetail();
        workNoticeDetail.setId(IdUtils.fastSimpleUUID());
        workNoticeDetail.setWorkNoticeId(wmsWorkNotice.getId());
        workNoticeDetail.setNoticeDetailId(wmsReceiveNoticeDetail.getId());
        workNoticeDetail.setNoticeItemDetailId(wmsExamineWork.getReceiveItemDetailId());
        workNoticeDetail.setItemCode(wmsExamineWork.getItemCode());
        workNoticeDetail.setBatchNo(wmsExamineWork.getBatchNo());
        workNoticeDetail.setSourceLocationId(wmsReceiveNoticeDetail.getLocationId());
        //TODO: 退货数量问题
        workNoticeDetail.setQuantity(wmsExamineWork.getFailQuantity());
        workNoticeDetail.setAllotQuantity(BigDecimal.ZERO);
        workNoticeDetail.setCreateBy(SecurityUtils.getUserId());
        workNoticeDetail.setCreateTime(DateUtils.getNowDate());
        wmsWorkNoticeDetailService.insertWmsWorkNoticeDetail(workNoticeDetail);
        return AjaxResult.success();
    }

    /**
     * 上架作业
     *
     * @param wmsExamineWork wms检查工作
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult shelvesWork(WmsExamineWork wmsExamineWork) {
        wmsExamineWork.setOperateStatus(ExamineWorkNoticeStatus.ON_THE_SHELF.getCode());
        wmsExamineWork.setUpdateBy(SecurityUtils.getUserId());
        wmsExamineWork.setUpdateTime(DateUtils.getNowDate());
        return wmsReceiveItemDetailService.shelves(wmsExamineWork);
    }

    /**
     * 根据收货明细id查询质检作业
     *
     * @param id id
     * @return {@link WmsExamineWork}
     */
    @Override
    public WmsExamineWork selectByReceiveItemId(String id) {
        return wmsExamineWorkMapper.selectWmsExamineWorkByReceiveItemDetailId(id);
    }

    @Override
    public WmsExamineWork selectByReceiveNoticeId(String id) {
        return wmsExamineWorkMapper.selectByReceiveNoticeId(id);
    }

    /**
     * 推送通知
     *
     * @param wmsExamineWorkInfo wms检查工作信息
     */
    public void pushNotifications(WmsExamineWork wmsExamineWorkInfo) throws Exception {
        WmsReceiveItemDetail wmsReceiveItemDetail = wmsReceiveItemDetailService.selectWmsReceiveItemDetailById(wmsExamineWorkInfo.getReceiveItemDetailId());
        WmsReceiveNoticeDetail receiveNoticeDetail = wmsReceiveNoticeDetailService.selectWmsReceiveNoticeDetailById(wmsReceiveItemDetail.getReceiveNoticeDetailId());
        WmsReceiveNotice wmsReceiveNotice = wmsReceiveNoticeService.selectWmsReceiveNoticeById(wmsReceiveItemDetail.getReceiveNoticeId());
        ExamineTaskDTO examineTaskDTO = new ExamineTaskDTO();
        examineTaskDTO.setWmsExamineId(wmsExamineWorkInfo.getId());
        examineTaskDTO.setAllQuantity(wmsExamineWorkInfo.getAllQuantity());
        examineTaskDTO.setCheckAcceptId(receiveNoticeDetail.getCheckAcceptId());
        examineTaskDTO.setPrice(wmsReceiveItemDetail.getPrice());
        examineTaskDTO.setCompanyId(wmsReceiveNotice.getCompanyId());
//        userFeignService.examineWorkNotice(examineTaskDTO);
    }
}
