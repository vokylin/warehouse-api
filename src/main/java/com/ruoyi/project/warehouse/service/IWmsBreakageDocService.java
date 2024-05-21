package com.ruoyi.project.warehouse.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.warehouse.domain.BreakageDocPrintInfo;
import com.ruoyi.project.warehouse.domain.BreakageDocVO;
import com.ruoyi.project.warehouse.domain.WmsBreakageDoc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 报损单Service接口
 *
 * @author Rem
 * @date 2023-05-20
 */
public interface IWmsBreakageDocService {
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
     * 批量删除报损单
     *
     * @param ids 需要删除的报损单主键集合
     * @return 结果
     */
    public AjaxResult deleteWmsBreakageDocByIds(String[] ids) throws Exception;

    BreakageDocVO selectById(String id);

    /**
     * 查询今日报损单数量
     *
     * @return int
     */
    int selectCountByToday();

    /**
     * 提交报损单
     *
     * @param id id
     * @return {@link AjaxResult}
     */
    AjaxResult submitWmsBreakageDocById(String id) throws Exception;

    @Transactional(rollbackFor = Exception.class)
    void approved(String id);

    @Transactional(rollbackFor = Exception.class)
    void rejected(String id) throws Exception;

    @Transactional(rollbackFor = Exception.class)
    void cancel(String businessId) throws Exception;

    List<BreakageDocPrintInfo> getBreakageDocPrintInfo(String noticeId);

}
