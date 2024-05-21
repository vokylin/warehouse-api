package com.ruoyi.project.warehouse.service.impl;

import com.ruoyi.common.enums.ReceiveNoticeStatus;
import com.ruoyi.common.enums.ReciveItemStatus;
import com.ruoyi.common.enums.ScrapSpecialFlag;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.common.component.DeliveryNoticeManager;
import com.ruoyi.project.common.component.ReceiveNoticeManager;
import com.ruoyi.project.warehouse.domain.ReceiveNoticeInfo;
import com.ruoyi.project.warehouse.domain.WmsReceiveItemDetail;
import com.ruoyi.project.warehouse.domain.WmsReceiveNotice;
import com.ruoyi.project.warehouse.domain.WmsReceiveNoticeDetail;
import com.ruoyi.project.warehouse.mapper.WmsReceiveItemDetailMapper;
import com.ruoyi.project.warehouse.mapper.WmsReceiveNoticeMapper;
import com.ruoyi.project.warehouse.service.IWmsReceiveNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 入库通知Service业务层处理
 *
 * @author Rem
 * @date 2023-03-31
 */
@Service
public class WmsReceiveNoticeServiceImpl implements IWmsReceiveNoticeService {
    @Autowired
    private WmsReceiveNoticeMapper wmsReceiveNoticeMapper;

    @Autowired
    private WmsReceiveItemDetailMapper wmsReceiveItemDetailMapper;

    @Autowired
    private ReceiveNoticeManager receiveNoticeManager;

    @Autowired
    private DeliveryNoticeManager deliveryNoticeManager;


    /**
     * 报废/特采类型
     */
    private final List<Integer> scrapSpecialTypeList = Arrays.asList(ScrapSpecialFlag.SCRAP.getCode(), ScrapSpecialFlag.SPECIAL.getCode());


    /**
     * 查询入库通知
     *
     * @param id 入库通知主键
     * @return 入库通知
     */
    @Override
    public WmsReceiveNotice selectWmsReceiveNoticeById(String id) {
        return wmsReceiveNoticeMapper.selectWmsReceiveNoticeById(id);
    }

    /**
     * 查询入库通知列表
     *
     * @param wmsReceiveNotice 入库通知
     * @return 入库通知
     */
    @Override
    public List<WmsReceiveNotice> selectWmsReceiveNoticeList(WmsReceiveNotice wmsReceiveNotice) {
        return wmsReceiveNoticeMapper.selectWmsReceiveNoticeList(wmsReceiveNotice);
    }

    /**
     * 新增入库通知
     *
     * @param wmsReceiveNotice 入库通知
     * @return 结果
     */
    @Override
    public int insertWmsReceiveNotice(WmsReceiveNotice wmsReceiveNotice) {
        wmsReceiveNotice.setCreateTime(DateUtils.getNowDate());
        return wmsReceiveNoticeMapper.insertWmsReceiveNotice(wmsReceiveNotice);
    }

    /**
     * 修改入库通知
     *
     * @param wmsReceiveNotice 入库通知
     * @return 结果
     */
    @Override
    public int updateWmsReceiveNotice(WmsReceiveNotice wmsReceiveNotice) {
        wmsReceiveNotice.setUpdateTime(DateUtils.getNowDate());
        return wmsReceiveNoticeMapper.updateWmsReceiveNotice(wmsReceiveNotice);
    }

    /**
     * 批量删除入库通知
     *
     * @param ids 需要删除的入库通知主键
     * @return 结果
     */
    @Override
    public int deleteWmsReceiveNoticeByIds(String[] ids) {
        return wmsReceiveNoticeMapper.deleteWmsReceiveNoticeByIds(ids);
    }

    /**
     * 删除入库通知信息
     *
     * @param id 入库通知主键
     * @return 结果
     */
    @Override
    public int deleteWmsReceiveNoticeById(String id) {
        return wmsReceiveNoticeMapper.deleteWmsReceiveNoticeById(id);
    }

    /**
     * 批量激活入库通知
     *
     * @param wmsReceiveNotice wms收到通知
     * @return int
     */
    @Override
    public int active(WmsReceiveNotice wmsReceiveNotice) {
        wmsReceiveNotice.setStatus(ReceiveNoticeStatus.RECEIVING.getCode());
        return wmsReceiveNoticeMapper.updateWmsReceiveNotice(wmsReceiveNotice);
    }

    /**
     * 查询当天通知数量
     */
    @Override
    public int selectWmsReceiveNoticeCountByToday() {
        return wmsReceiveNoticeMapper.selectWmsReceiveNoticeCountByToday();
    }

    /**
     * 作废
     *
     * @param wmsReceiveNotice wms收到通知
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult invalid(WmsReceiveNotice wmsReceiveNotice) throws Exception {

        List<WmsReceiveItemDetail> wmsReceiveItemDetails = wmsReceiveItemDetailMapper.selectWmsReceiveItemDetailByReceiveNoticeId(wmsReceiveNotice.getId());
        if (!wmsReceiveItemDetails.isEmpty()) {
            return AjaxResult.error("该通知已经分配,不能作废");
        }

        wmsReceiveNotice.setStatus(ReceiveNoticeStatus.CANCEL.getCode());
        wmsReceiveNotice.setUpdateTime(DateUtils.getNowDate());
        wmsReceiveNotice.setUpdateBy(SecurityUtils.getUserId());
        wmsReceiveNoticeMapper.updateWmsReceiveNotice(wmsReceiveNotice);
        // 采购报废入库不通知
        if (!ScrapSpecialFlag.SCRAP.getCode().equals(wmsReceiveNotice.getScrapSpecialFlag()) && !ScrapSpecialFlag.SPECIAL.getCode().equals(wmsReceiveNotice.getScrapSpecialFlag())) {
            // 作废通知
            receiveNoticeManager.invalidNotice(wmsReceiveNotice);
        }
        return AjaxResult.success();
    }

    /**
     * 反激活
     *
     * @param wmsReceiveNotice wms收到通知
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult inactive(String[] ids) {

        for (String id : ids) {
            WmsReceiveNotice receiveNotice = wmsReceiveNoticeMapper.selectWmsReceiveNoticeById(id);
            if (receiveNotice == null) {
                return AjaxResult.error("反激活失败, 入库通知不存在");
            }
            if (!receiveNotice.getStatus().equals(ReceiveNoticeStatus.RECEIVING.getCode())) {
                return AjaxResult.error("反激活失败, 入库通知状态不是收货中");
            }
            List<WmsReceiveItemDetail> wmsReceiveItemDetails = wmsReceiveItemDetailMapper.selectWmsReceiveItemDetailByReceiveNoticeId(id);
            if (wmsReceiveItemDetails.size() > 0) {
                return AjaxResult.error("该通知已经分配,不能反激活");
            }
            WmsReceiveNotice wmsReceiveNoticeInfo = new WmsReceiveNotice();
            wmsReceiveNoticeInfo.setId(id);
            wmsReceiveNoticeInfo.setUpdateTime(DateUtils.getNowDate());
            wmsReceiveNoticeInfo.setUpdateBy(SecurityUtils.getUserId());
            wmsReceiveNoticeInfo.setStatus(ReceiveNoticeStatus.WAIT_ACTIVE.getCode());
            wmsReceiveNoticeMapper.updateWmsReceiveNotice(wmsReceiveNoticeInfo);
        }
        return AjaxResult.success();
    }

    /**
     * 收货确认
     *
     * @param ids 入库通知
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult confirm(String[] ids) throws Exception {
        for (String id : ids) {

            WmsReceiveNotice receiveNotice = wmsReceiveNoticeMapper.selectWmsReceiveNoticeById(id);
            if (receiveNotice == null) {
                return AjaxResult.error("确认失败, 入库通知不存在");
            }
            if (!receiveNotice.getStatus().equals(ReceiveNoticeStatus.RECEIVING.getCode())) {
                return AjaxResult.error("确认失败, 入库通知状态不是收货中");
            }

            ReceiveNoticeInfo receiveNoticeInfo = receiveNoticeManager.getReceiveNoticeInfoByNoticeId(id);
            WmsReceiveNotice wmsReceiveNotice = receiveNoticeInfo.getWmsReceiveNotice();
            List<WmsReceiveNoticeDetail> wmsReceiveNoticeDetailList = receiveNoticeInfo.getWmsReceiveNoticeDetailList();

            Integer scrapSpecialFlag = wmsReceiveNotice.getScrapSpecialFlag();

            //TODO: 收货数量存在 由于换算率导致的误差 暂时进行仅判断是否收货
            List<WmsReceiveNoticeDetail> collect = wmsReceiveNoticeDetailList.stream().filter((item) -> item.getReceiveQuantity().compareTo(BigDecimal.ZERO) == 0).collect(Collectors.toList());
            if (!collect.isEmpty()) {
                return AjaxResult.error("还未收货完成，不能确认");
            }
            List<WmsReceiveItemDetail> wmsReceiveItemDetailList = receiveNoticeInfo.getWmsReceiveItemDetailList();
            for (WmsReceiveItemDetail wmsReceiveItemDetail : wmsReceiveItemDetailList) {

                // 判断数量是否一致
                BigDecimal quantity = wmsReceiveItemDetail.getQuantity();
                // 合格数量
                BigDecimal passQuantity = wmsReceiveItemDetail.getPassQuantity();
                // 不合格数量
                BigDecimal failQuantity = wmsReceiveItemDetail.getFailQuantity();
                // 退货数量
                BigDecimal refundQuantity = wmsReceiveItemDetail.getRefundQuantity();
                // 上架数量
                BigDecimal depositQuantity = wmsReceiveItemDetail.getDepositQuantity();

                // 判断是否是 采购报废/特采 判断失败数量是否等于上架数量
                if (scrapSpecialTypeList.contains(scrapSpecialFlag)) {
                    if (failQuantity.compareTo(depositQuantity) != 0) {
                        return AjaxResult.error("存在还未上架的物料");
                    }
                } else {
                    // 进料/进货/其他类型校验数量
                    if (wmsReceiveItemDetail.getStatus().equals(ReciveItemStatus.WAITING_FOR_INSPECTION.getCode()) || wmsReceiveItemDetail.getStatus().equals(ReciveItemStatus.INSPECTION_IN_PROGRESS.getCode())) {
                        return AjaxResult.error("存在物料还未质检完成");
                    }
                    if (passQuantity.compareTo(BigDecimal.ZERO) != 0 && passQuantity.compareTo(depositQuantity) != 0) {
                        return AjaxResult.error("存在还未上架的物料");
                    }
                    if (passQuantity.add(refundQuantity).compareTo(quantity) != 0) {
                        return AjaxResult.error("存在还未退货的物料");
                    }
                }
            }
            wmsReceiveNotice.setStatus(ReceiveNoticeStatus.COMPLETE.getCode());
            wmsReceiveNotice.setEndTime(DateUtils.getNowDate());
            wmsReceiveNotice.setUpdateTime(DateUtils.getNowDate());
            wmsReceiveNotice.setUpdateBy(SecurityUtils.getUserId());
            wmsReceiveNoticeMapper.updateWmsReceiveNotice(wmsReceiveNotice);

            // 生成出库通知
            deliveryNoticeManager.createDeliveryNotice(wmsReceiveNotice);

            // 通知收货完成
            receiveNoticeManager.receiveNoticeFinish(receiveNoticeInfo);


        }

        return AjaxResult.success();
    }

}
