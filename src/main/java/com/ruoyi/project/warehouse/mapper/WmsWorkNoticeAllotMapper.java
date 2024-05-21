package com.ruoyi.project.warehouse.mapper;

import com.ruoyi.project.warehouse.domain.WmsWorkNoticeAllot;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 作业通知明细分配Mapper接口
 *
 * @author Rem
 * @date 2023-04-07
 */
public interface WmsWorkNoticeAllotMapper {
    /**
     * 查询作业通知明细分配
     *
     * @param id 作业通知明细分配主键
     * @return 作业通知明细分配
     */
    public WmsWorkNoticeAllot selectWmsWorkNoticeAllotById(String id);

    /**
     * 查询作业通知明细分配列表
     *
     * @param wmsWorkNoticeAllot 作业通知明细分配
     * @return 作业通知明细分配集合
     */
    public List<WmsWorkNoticeAllot> selectWmsWorkNoticeAllotList(WmsWorkNoticeAllot wmsWorkNoticeAllot);

    List<WmsWorkNoticeAllot> selectAllotListByDetailId(String id);


    WmsWorkNoticeAllot selectItemDetail(WmsWorkNoticeAllot wmsWorkNoticeAllot);

    /**
     * 新增作业通知明细分配
     *
     * @param wmsWorkNoticeAllot 作业通知明细分配
     * @return 结果
     */
    public int insertWmsWorkNoticeAllot(WmsWorkNoticeAllot wmsWorkNoticeAllot);

    /**
     * 修改作业通知明细分配
     *
     * @param wmsWorkNoticeAllot 作业通知明细分配
     * @return 结果
     */
    public int updateWmsWorkNoticeAllot(WmsWorkNoticeAllot wmsWorkNoticeAllot);

    /**
     * 删除作业通知明细分配
     *
     * @param id 作业通知明细分配主键
     * @return 结果
     */
    public int deleteWmsWorkNoticeAllotById(String id);

    /**
     * 批量删除作业通知明细分配
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsWorkNoticeAllotByIds(String[] ids);

    void batchUpdateStatus(@Param("id") String id, @Param("status") Integer status);
}
