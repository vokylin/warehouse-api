package com.ruoyi.project.warehouse.service;

import com.ruoyi.project.warehouse.domain.ExportItemStorage;
import com.ruoyi.project.warehouse.domain.ItemStorageIndexCountVO;
import com.ruoyi.project.warehouse.domain.WmsItemStorage;

import java.math.BigDecimal;
import java.util.List;

/**
 * 库存汇总Service接口
 *
 * @author Rem
 * @date 2023-04-08
 */
public interface IWmsItemStorageService {
    /**
     * 查询库存汇总
     *
     * @param id 库存汇总主键
     * @return 库存汇总
     */
    public WmsItemStorage selectWmsItemStorageById(String id);

    /**
     * 查询库存汇总列表
     *
     * @param wmsItemStorage 库存汇总
     * @return 库存汇总集合
     */
    public List<WmsItemStorage> selectWmsItemStorageList(WmsItemStorage wmsItemStorage);

    /**
     * 查询库存汇总列表
     *
     * @param wmsItemStorage wms项存储
     * @return {@link List}<{@link ExportItemStorage}>
     */
    List<ExportItemStorage> selectExportList(WmsItemStorage wmsItemStorage);

    /**
     * 新增库存汇总
     *
     * @param wmsItemStorage 库存汇总
     * @return 结果
     */
    public int insertWmsItemStorage(WmsItemStorage wmsItemStorage);

    /**
     * 修改库存汇总
     *
     * @param wmsItemStorage 库存汇总
     * @return 结果
     */
    public int updateWmsItemStorage(WmsItemStorage wmsItemStorage);

    /**
     * 修改库存实时单价
     *
     * @param wmsItemStorage 库存汇总
     * @return 结果
     */
    int updateWmsItemStoragePrice(WmsItemStorage wmsItemStorage);


    /**
     * 修改库存实时单价、库存金额
     *
     * @param wmsItemStorage 库存汇总
     * @return 结果
     */
    int updateWmsItemStorageAllPrice(WmsItemStorage wmsItemStorage);

    /**
     * 批量删除库存汇总
     *
     * @param ids 需要删除的库存汇总主键集合
     * @return 结果
     */
    public int deleteWmsItemStorageByIds(String[] ids);

    /**
     * 删除库存汇总信息
     *
     * @param id 库存汇总主键
     * @return 结果
     */
    public int deleteWmsItemStorageById(String id);

    /**
     * 根据物料编码查询库存汇总
     *
     * @param code 代码
     * @return {@link WmsItemStorage}
     */
    WmsItemStorage selectWmsItemStorageByItemCode(String code);


    WmsItemStorage selectWmsItemStorage(WmsItemStorage wmsItemStorage);

    /**
     * 获取公司中某个物料的所有库存汇总
     *
     * @param wmsItemStorage
     * @return
     */
    List<WmsItemStorage> selectWmsItemStorageByCompany(WmsItemStorage wmsItemStorage);

    List<ItemStorageIndexCountVO> selectStorageIndexCount(String itemCode);

    BigDecimal getItemStorageBatchNoQuantity(WmsItemStorage itemInfo);
}
