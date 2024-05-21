package com.ruoyi.project.system.mapper;

import com.ruoyi.project.system.domain.WmsDeliverySureDetail;

import java.util.List;

/**
 * 出库确认记录详情Mapper接口
 *
 * @author Rem
 * @date 2023-04-17
 */
public interface WmsDeliverySureDetailMapper {
    /**
     * 查询出库确认记录详情
     *
     * @param id 出库确认记录详情主键
     * @return 出库确认记录详情
     */
    public WmsDeliverySureDetail selectWmsDeliverySureDetailById(String id);

    /**
     * 查询出库确认记录详情列表
     *
     * @param wmsDeliverySureDetail 出库确认记录详情
     * @return 出库确认记录详情集合
     */
    public List<WmsDeliverySureDetail> selectWmsDeliverySureDetailList(WmsDeliverySureDetail wmsDeliverySureDetail);

    /**
     * 新增出库确认记录详情
     *
     * @param wmsDeliverySureDetail 出库确认记录详情
     * @return 结果
     */
    public int insertWmsDeliverySureDetail(WmsDeliverySureDetail wmsDeliverySureDetail);

    /**
     * 修改出库确认记录详情
     *
     * @param wmsDeliverySureDetail 出库确认记录详情
     * @return 结果
     */
    public int updateWmsDeliverySureDetail(WmsDeliverySureDetail wmsDeliverySureDetail);

    /**
     * 删除出库确认记录详情
     *
     * @param id 出库确认记录详情主键
     * @return 结果
     */
    public int deleteWmsDeliverySureDetailById(String id);

    /**
     * 批量删除出库确认记录详情
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsDeliverySureDetailByIds(String[] ids);

    List<WmsDeliverySureDetail> selectBySureId(String id);
}
