package com.ruoyi.project.warehouse.mapper;

import com.ruoyi.project.warehouse.domain.BreakageDocPrintInfo;
import com.ruoyi.project.warehouse.domain.BreakageDocVO;
import com.ruoyi.project.warehouse.domain.WmsBreakageDoc;

import java.util.List;

/**
 * 报损单Mapper接口
 *
 * @author Rem
 * @date 2023-05-20
 */
public interface WmsBreakageDocMapper {
    /**
     * 查询报损单
     *
     * @param id 报损单主键
     * @return 报损单
     */
    public WmsBreakageDoc selectWmsBreakageDocById(String id);

    /**
     * 查询报损单列表
     *
     * @param wmsBreakageDoc 报损单
     * @return 报损单集合
     */
    public List<WmsBreakageDoc> selectWmsBreakageDocList(WmsBreakageDoc wmsBreakageDoc);

    /**
     * 新增报损单
     *
     * @param wmsBreakageDoc 报损单
     * @return 结果
     */
    public int insertWmsBreakageDoc(WmsBreakageDoc wmsBreakageDoc);

    /**
     * 修改报损单
     *
     * @param wmsBreakageDoc 报损单
     * @return 结果
     */
    public int updateWmsBreakageDoc(WmsBreakageDoc wmsBreakageDoc);

    /**
     * 删除报损单
     *
     * @param id 报损单主键
     * @return 结果
     */
    public int deleteWmsBreakageDocById(String id);

    /**
     * 批量删除报损单
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWmsBreakageDocByIds(String[] ids);

    /**
     * 查询今日报损单数量
     *
     * @return int
     */
    int selectCountByToday();

    BreakageDocVO selectById(String id);

    List<BreakageDocPrintInfo> getBreakageDocPrintInfo(String noticeId);
}
