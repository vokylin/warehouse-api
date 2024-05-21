package com.ruoyi.project.warehouse.mapper;

import com.ruoyi.project.warehouse.domain.WmsExamineWork;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 质检作业Mapper接口
 *
 * @author Rem
 * @date 2023-04-07
 */
public interface WmsExamineWorkMapper {
    /**
     * 查询质检作业
     *
     * @param id 质检作业主键
     * @return 质检作业
     */
    public WmsExamineWork selectWmsExamineWorkById(String id);

    /**
     * 查询质检作业列表
     *
     * @param wmsExamineWork 质检作业
     * @return 质检作业集合
     */
    public List<WmsExamineWork> selectWmsExamineWorkList(WmsExamineWork wmsExamineWork);

    /**
     * 新增质检作业
     *
     * @param wmsExamineWork 质检作业
     * @return 结果
     */
    public int insertWmsExamineWork(WmsExamineWork wmsExamineWork);

    /**
     * 修改质检作业
     *
     * @param wmsExamineWork 质检作业
     * @return 结果
     */
    public int updateWmsExamineWork(WmsExamineWork wmsExamineWork);

    /**
     * 删除质检作业
     *
     * @param id 质检作业主键
     * @return 结果
     */
    public int deleteWmsExamineWorkById(String id);

    /**
     * 批量删除质检作业
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsExamineWorkByIds(String[] ids);

    /**
     * 查询今日质检作业数量
     *
     * @return int
     */
    int selectWmsExamineWorkCountByToday();

    /**
     * 根据收货明细id查询质检作业
     *
     * @param receiveItemDetailId id
     * @return {@link WmsExamineWork}
     */
    WmsExamineWork selectWmsExamineWorkByReceiveItemDetailId(@Param("receiveItemDetailId") String receiveItemDetailId);

    WmsExamineWork selectByReceiveNoticeId(String id);
}
