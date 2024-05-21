package com.ruoyi.project.system.service.impl;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.system.domain.WmsItemAttribute;
import com.ruoyi.project.system.mapper.WmsItemAttributeMapper;
import com.ruoyi.project.system.service.IWmsItemAttributeService;
import com.ruoyi.project.warehouse.domain.ItemStorageIndexCountVO;
import com.ruoyi.project.warehouse.domain.WmsItemStorageDetail;
import com.ruoyi.project.warehouse.service.IWmsItemStorageDetailService;
import com.ruoyi.project.warehouse.service.IWmsItemStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 物料仓储属性Service业务层处理
 *
 * @author ruoyi
 * @date 2023-04-11
 */
@Service
public class WmsItemAttributeServiceImpl implements IWmsItemAttributeService {

    @Autowired
    private WmsItemAttributeMapper wmsItemAttributeMapper;

    @Autowired
    private IWmsItemStorageService wmsItemStorageService;

    @Autowired
    private IWmsItemStorageDetailService wmsItemStorageDetailService;

    /**
     * 查询物料仓储属性
     *
     * @param itemCode 物料编码
     * @return 物料仓储属性
     */
    @Override
    public WmsItemAttribute selectWmsItemAttributeByCode(String itemCode) {
        return wmsItemAttributeMapper.selectByItemCode(itemCode);
    }

    /**
     * 根据物料编码查询物料仓储属性
     *
     * @param itemCode 项目代码
     * @return {@link WmsItemAttribute}
     */
    @Override
    public WmsItemAttribute selectByItemCode(String itemCode) {
        return wmsItemAttributeMapper.selectByItemCode(itemCode);
    }

    /**
     * 查询物料仓储属性列表
     *
     * @param wmsItemAttribute 物料仓储属性
     * @return 物料仓储属性
     */
    @Override
    public List<WmsItemAttribute> selectWmsItemAttributeList(WmsItemAttribute wmsItemAttribute) {
        return wmsItemAttributeMapper.selectWmsItemAttributeList(wmsItemAttribute);
    }

    /**
     * 新增物料仓储属性
     *
     * @param wmsItemAttribute 物料仓储属性
     * @return 结果
     */
    @Override
    public int insertWmsItemAttribute(WmsItemAttribute wmsItemAttribute) {
        wmsItemAttribute.setId(IdUtils.fastSimpleUUID());
        wmsItemAttribute.setCreateTime(DateUtils.getNowDate());
        wmsItemAttributeMapper.insertWmsItemAttribute(wmsItemAttribute);
        this.inventoryCheck(wmsItemAttribute.getItemCode());
        if (null != wmsItemAttribute.getRemindDay()) {
            this.checkShelfLifeStatus(wmsItemAttribute.getItemCode());
        }
        return Constants.SUCCESS_CODE;
    }

    /**
     * 修改物料仓储属性
     *
     * @param wmsItemAttribute 物料仓储属性
     * @return 结果
     */
    @Override
    public AjaxResult updateWmsItemAttribute(WmsItemAttribute wmsItemAttribute) {

        WmsItemAttribute itemAttribute = wmsItemAttributeMapper.selectWmsItemAttributeById(wmsItemAttribute.getId());
        if (null == itemAttribute) {
            return AjaxResult.error("该物料仓储属性不存在");
        }

        wmsItemAttribute.setUpdateTime(DateUtils.getNowDate());
        wmsItemAttribute.setUpdateBy(Constants.SYSTEM_USER_ID);
        wmsItemAttributeMapper.updateWmsItemAttribute(wmsItemAttribute);

        // 库存上下限检查
        this.inventoryCheck(wmsItemAttribute.getItemCode());
        // 修改了预警天数
        if (!itemAttribute.getRemindDay().equals(wmsItemAttribute.getRemindDay())) {
            this.checkShelfLifeStatus(itemAttribute.getItemCode());
        }
        return AjaxResult.success();
    }

    /**
     * 批量删除物料仓储属性
     *
     * @param ids 需要删除的物料仓储属性主键
     * @return 结果
     */
    @Override
    public int deleteWmsItemAttributeByIds(String[] ids) {
        return wmsItemAttributeMapper.deleteWmsItemAttributeByIds(ids);
    }

    /**
     * 删除物料仓储属性信息
     *
     * @param id 物料仓储属性主键
     * @return 结果
     */
    @Override
    public int deleteWmsItemAttributeById(String id) {
        return wmsItemAttributeMapper.deleteWmsItemAttributeById(id);
    }

    @Override
    public List<WmsItemAttribute> selectAll() {
        return wmsItemAttributeMapper.selectAll();
    }

    /**
     * 库存检查
     * 库存上下限检查
     *
     * @param itemCode 项目代码
     */
    private void inventoryCheck(String itemCode) {
        List<ItemStorageIndexCountVO> itemStorageIndexCountVOS = wmsItemStorageService.selectStorageIndexCount(itemCode);
        if (itemStorageIndexCountVOS.size() > 0) {
            for (ItemStorageIndexCountVO itemStorageIndexCountVO : itemStorageIndexCountVOS) {
                WmsItemAttribute wmsItemAttribute = new WmsItemAttribute();
                wmsItemAttribute.setItemCode(itemCode);
                wmsItemAttribute.setLimitedStatus(itemStorageIndexCountVO.getLimitedStatus());
                wmsItemAttributeMapper.updateLimitedStatus(wmsItemAttribute);
            }
        }
    }

    /**
     * 临期状态检查
     *
     * @param itemCode 物料编码
     */
    public void checkShelfLifeStatus(String itemCode) {
        WmsItemStorageDetail itemStorageDetail = new WmsItemStorageDetail();
        itemStorageDetail.setItemCode(itemCode);
        List<WmsItemStorageDetail> wmsItemStorageDetails = wmsItemStorageDetailService.selectWmsItemStorageDetailList(itemStorageDetail);
        if (wmsItemStorageDetails.isEmpty()) {
            return;
        }
        wmsItemStorageDetails.forEach(item -> {
            wmsItemStorageDetailService.setShelfLifeStatus(item);
            wmsItemStorageDetailService.updateWmsItemStorageDetail(item);
        });
    }
}
