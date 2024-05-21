package com.ruoyi.project.system.mapper;

import com.ruoyi.project.system.domain.WmsItemAttribute;

import java.util.List;

/**
 * 物料仓储属性Mapper接口
 *
 * @author ruoyi
 * @date 2023-04-11
 */
public interface WmsItemAttributeMapper {
    /**
     * 查询物料仓储属性
     *
     * @param id 物料仓储属性主键
     * @return 物料仓储属性
     */
    public WmsItemAttribute selectWmsItemAttributeById(String id);

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
    public int updateWmsItemAttribute(WmsItemAttribute wmsItemAttribute);

    /**
     * 删除物料仓储属性
     *
     * @param id 物料仓储属性主键
     * @return 结果
     */
    public int deleteWmsItemAttributeById(String id);

    /**
     * 批量删除物料仓储属性
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsItemAttributeByIds(String[] ids);

    List<WmsItemAttribute> selectAll();

    int updateLimitedStatus(WmsItemAttribute wmsItemAttribute);
}
