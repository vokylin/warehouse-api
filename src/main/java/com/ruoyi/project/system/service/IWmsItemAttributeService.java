package com.ruoyi.project.system.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.system.domain.WmsItemAttribute;

import java.util.List;

/**
 * 物料仓储属性Service接口
 *
 * @author ruoyi
 * @date 2023-04-11
 */
public interface IWmsItemAttributeService {
    /**
     * 查询物料仓储属性
     *
     * @param itemCode 物料编码
     * @return 物料仓储属性
     */
    public WmsItemAttribute selectWmsItemAttributeByCode(String itemCode);

    /**
     * 根据物料编码查询物料仓储属性
     *
     * @param itemCode 项目代码
     * @return {@link WmsItemAttribute}
     */
    WmsItemAttribute selectByItemCode(String itemCode);

    /**
     * 查询物料仓储属性列表
     *
     * @param wmsItemAttribute 物料仓储属性
     * @return 物料仓储属性集合
     */
    public List<WmsItemAttribute> selectWmsItemAttributeList(WmsItemAttribute wmsItemAttribute);

    /**
     * 新增物料仓储属性
     *
     * @param wmsItemAttribute 物料仓储属性
     * @return 结果
     */
    public int insertWmsItemAttribute(WmsItemAttribute wmsItemAttribute);

    /**
     * 修改物料仓储属性
     *
     * @param wmsItemAttribute 物料仓储属性
     * @return 结果
     */
    public AjaxResult updateWmsItemAttribute(WmsItemAttribute wmsItemAttribute);

    /**
     * 批量删除物料仓储属性
     *
     * @param ids 需要删除的物料仓储属性主键集合
     * @return 结果
     */
    public int deleteWmsItemAttributeByIds(String[] ids);

    /**
     * 删除物料仓储属性信息
     *
     * @param id 物料仓储属性主键
     * @return 结果
     */
    public int deleteWmsItemAttributeById(String id);

    List<WmsItemAttribute> selectAll();
}
