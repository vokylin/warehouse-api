package com.ruoyi.project.warehouse.mapper;

import com.ruoyi.project.warehouse.domain.WmsWorkNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 作业通知Mapper接口
 *
 * @author Rem
 * @date 2023-04-07
 */
public interface WmsWorkNoticeMapper {
    /**
     * 查询作业通知
     *
     * @param id 作业通知主键
     * @return 作业通知
     */
    public WmsWorkNotice selectWmsWorkNoticeById(String id);

    /**
     * 查询作业通知列表
     *
     * @param wmsWorkNotice 作业通知
     * @return 作业通知集合
     */
    public List<WmsWorkNotice> selectWmsWorkNoticeList(WmsWorkNotice wmsWorkNotice);

    /**
     * 新增作业通知
     *
     * @param wmsWorkNotice 作业通知
     * @return 结果
     */
    public int insertWmsWorkNotice(WmsWorkNotice wmsWorkNotice);

    /**
     * 修改作业通知
     *
     * @param wmsWorkNotice 作业通知
     * @return 结果
     */
    public int updateWmsWorkNotice(WmsWorkNotice wmsWorkNotice);

    /**
     * 删除作业通知
     *
     * @param id 作业通知主键
     * @return 结果
     */
    public int deleteWmsWorkNoticeById(String id);

    /**
     * 批量删除作业通知
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsWorkNoticeByIds(String[] ids);

    /**
     * 查询今日作业通知数量
     *
     * @return int
     */
    int selectWmsWorkNoticeCountByToday(@Param("workType") Integer workType);

    List<WmsWorkNotice> selectWmsWorkNoticeByRelateId(String id);

    /**
     * 根据通知ID查询是否完成作业
     */
    int selectIsCompleteById(String id);

    int updatePickWorkNoticeStatus(WmsWorkNotice wmsWorkNotice);
}
