package com.ruoyi.project.warehouse.mapper;

import com.ruoyi.project.warehouse.domain.StorageRecordVo;
import com.ruoyi.project.warehouse.domain.WmsStorageInOutDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 出入库记录详情Mapper接口
 *
 * @author Rem
 * @date 2023-04-08
 */
public interface WmsStorageInOutDetailMapper {
    /**
     * 查询出入库记录详情
     *
     * @param id 出入库记录详情主键
     * @return 出入库记录详情
     */
    public WmsStorageInOutDetail selectWmsStorageInOutDetailById(String id);

    /**
     * 查询出入库记录详情列表
     *
     * @param wmsStorageInOutDetail 出入库记录详情
     * @return 出入库记录详情集合
     */
    public List<WmsStorageInOutDetail> selectWmsStorageInOutDetailList(WmsStorageInOutDetail wmsStorageInOutDetail);

    /**
     * 新增出入库记录详情
     *
     * @param wmsStorageInOutDetail 出入库记录详情
     * @return 结果
     */
    public int insertWmsStorageInOutDetail(WmsStorageInOutDetail wmsStorageInOutDetail);

    /**
     * 修改出入库记录详情
     *
     * @param wmsStorageInOutDetail 出入库记录详情
     * @return 结果
     */
    public int updateWmsStorageInOutDetail(WmsStorageInOutDetail wmsStorageInOutDetail);

    /**
     * 删除出入库记录详情
     *
     * @param id 出入库记录详情主键
     * @return 结果
     */
    public int deleteWmsStorageInOutDetailById(String id);

    /**
     * 批量删除出入库记录详情
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsStorageInOutDetailByIds(String[] ids);

    List<StorageRecordVo> selectByNoticeId(@Param("noticeId") String noticeId, @Param("billType") Integer billType);
}
