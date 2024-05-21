package com.ruoyi.project.system.mapper;

import com.ruoyi.project.system.domain.DeliverySureInfo;
import com.ruoyi.project.system.domain.WmsDeliverySure;

import java.util.List;

/**
 * 出库确认记录Mapper接口
 *
 * @author Rem
 * @date 2023-04-17
 */
public interface WmsDeliverySureMapper {
    /**
     * 查询出库确认记录
     *
     * @param id 出库确认记录主键
     * @return 出库确认记录
     */
    public WmsDeliverySure selectWmsDeliverySureById(String id);

    /**
     * 查询出库确认记录列表
     *
     * @param wmsDeliverySure 出库确认记录
     * @return 出库确认记录集合
     */
    public List<WmsDeliverySure> selectWmsDeliverySureList(WmsDeliverySure wmsDeliverySure);

    /**
     * 新增出库确认记录
     *
     * @param wmsDeliverySure 出库确认记录
     * @return 结果
     */
    public int insertWmsDeliverySure(WmsDeliverySure wmsDeliverySure);

    /**
     * 修改出库确认记录
     *
     * @param wmsDeliverySure 出库确认记录
     * @return 结果
     */
    public int updateWmsDeliverySure(WmsDeliverySure wmsDeliverySure);

    /**
     * 删除出库确认记录
     *
     * @param id 出库确认记录主键
     * @return 结果
     */
    public int deleteWmsDeliverySureById(String id);

    /**
     * 批量删除出库确认记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsDeliverySureByIds(String[] ids);

    DeliverySureInfo selectByNoticeId(String id);
}
