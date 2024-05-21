package com.ruoyi.project.warehouse.mapper;

import com.ruoyi.project.warehouse.domain.WmsReceiveNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 入库通知Mapper接口
 *
 * @author Rem
 * @date 2023-03-31
 */
public interface WmsReceiveNoticeMapper {
    /**
     * 查询入库通知
     *
     * @param id 入库通知主键
     * @return 入库通知
     */
    public WmsReceiveNotice selectWmsReceiveNoticeById(String id);

    /**
     * 查询入库通知列表
     *
     * @param wmsReceiveNotice 入库通知
     * @return 入库通知集合
     */
    public List<WmsReceiveNotice> selectWmsReceiveNoticeList(WmsReceiveNotice wmsReceiveNotice);

    /**
     * 新增入库通知
     *
     * @param wmsReceiveNotice 入库通知
     * @return 结果
     */
    public int insertWmsReceiveNotice(WmsReceiveNotice wmsReceiveNotice);

    /**
     * 修改入库通知
     *
     * @param wmsReceiveNotice 入库通知
     * @return 结果
     */
    public int updateWmsReceiveNotice(WmsReceiveNotice wmsReceiveNotice);

    /**
     * 删除入库通知
     *
     * @param id 入库通知主键
     * @return 结果
     */
    public int deleteWmsReceiveNoticeById(String id);

    /**
     * 批量删除入库通知
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsReceiveNoticeByIds(String[] ids);

    /**
     * 查询当天入库通知数量
     *
     * @return int
     */
    int selectWmsReceiveNoticeCountByToday();


    /**
     * 查询某月未完成的入库通知数量
     *
     * @return
     */
    int selectUnfinishedReceiveNotice(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
