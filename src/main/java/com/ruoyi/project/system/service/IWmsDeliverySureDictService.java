package com.ruoyi.project.system.service;

import com.ruoyi.project.system.domain.WmsDeliverySureDict;

import java.util.List;

/**
 * 出库确认项Service接口
 *
 * @author Rem
 * @date 2023-04-12
 */
public interface IWmsDeliverySureDictService {
    /**
     * 查询出库确认项
     *
     * @param id 出库确认项主键
     * @return 出库确认项
     */
    public WmsDeliverySureDict selectWmsDeliverySureDictById(String id);

    /**
     * 查询出库确认项列表
     *
     * @param wmsDeliverySureDict 出库确认项
     * @return 出库确认项集合
     */
    public List<WmsDeliverySureDict> selectWmsDeliverySureDictList(WmsDeliverySureDict wmsDeliverySureDict);

    /**
     * 新增出库确认项
     *
     * @param wmsDeliverySureDict 出库确认项
     * @return 结果
     */
    public int insertWmsDeliverySureDict(WmsDeliverySureDict wmsDeliverySureDict);

    /**
     * 修改出库确认项
     *
     * @param wmsDeliverySureDict 出库确认项
     * @return 结果
     */
    public int updateWmsDeliverySureDict(WmsDeliverySureDict wmsDeliverySureDict);

    /**
     * 批量删除出库确认项
     *
     * @param ids 需要删除的出库确认项主键集合
     * @return 结果
     */
    public int deleteWmsDeliverySureDictByIds(String[] ids);

    /**
     * 删除出库确认项信息
     *
     * @param id 出库确认项主键
     * @return 结果
     */
    public int deleteWmsDeliverySureDictById(String id);

    List<WmsDeliverySureDict> getListByBusinessType(WmsDeliverySureDict wmsDeliverySureDict);
}
