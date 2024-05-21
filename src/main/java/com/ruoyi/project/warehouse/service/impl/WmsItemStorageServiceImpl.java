package com.ruoyi.project.warehouse.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.warehouse.domain.ExportItemStorage;
import com.ruoyi.project.warehouse.domain.ItemStorageIndexCountVO;
import com.ruoyi.project.warehouse.domain.WmsItemStorage;
import com.ruoyi.project.warehouse.mapper.WmsItemStorageMapper;
import com.ruoyi.project.warehouse.service.IWmsItemStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 库存汇总Service业务层处理
 *
 * @author Rem
 * @date 2023-04-08
 */
@Service
public class WmsItemStorageServiceImpl implements IWmsItemStorageService {
    @Autowired
    private WmsItemStorageMapper wmsItemStorageMapper;


    /**
     * 查询库存汇总
     *
     * @param id 库存汇总主键
     * @return 库存汇总
     */
    @Override
    public WmsItemStorage selectWmsItemStorageById(String id) {
        return wmsItemStorageMapper.selectWmsItemStorageById(id);
    }

    /**
     * 查询库存汇总列表
     *
     * @param wmsItemStorage 库存汇总
     * @return 库存汇总
     */
    @Override
    public List<WmsItemStorage> selectWmsItemStorageList(WmsItemStorage wmsItemStorage) {
        return wmsItemStorageMapper.selectWmsItemStorageList(wmsItemStorage);
    }

    /**
     * 查询库存汇总列表
     *
     * @param wmsItemStorage wms项存储
     * @return {@link List}<{@link ExportItemStorage}>
     */
    @Override
    public List<ExportItemStorage> selectExportList(WmsItemStorage wmsItemStorage) {
        return wmsItemStorageMapper.selectExportList(wmsItemStorage);
    }

    /**
     * 新增库存汇总
     *
     * @param wmsItemStorage 库存汇总
     * @return 结果
     */
    @Override
    public int insertWmsItemStorage(WmsItemStorage wmsItemStorage) {
        wmsItemStorage.setCreateTime(DateUtils.getNowDate());
        return wmsItemStorageMapper.insertWmsItemStorage(wmsItemStorage);
    }

    /**
     * 修改库存汇总
     *
     * @param wmsItemStorage 库存汇总
     * @return 结果
     */
    @Override
    public int updateWmsItemStorage(WmsItemStorage wmsItemStorage) {
        wmsItemStorage.setUpdateTime(DateUtils.getNowDate());
        return wmsItemStorageMapper.updateWmsItemStorage(wmsItemStorage);
    }

    @Override
    public int updateWmsItemStoragePrice(WmsItemStorage wmsItemStorage) {
        return wmsItemStorageMapper.updateWmsItemStoragePrice(wmsItemStorage);
    }

    @Override
    public int updateWmsItemStorageAllPrice(WmsItemStorage wmsItemStorage) {
        return wmsItemStorageMapper.updateWmsItemStorageAllPrice(wmsItemStorage);
    }

    /**
     * 批量删除库存汇总
     *
     * @param ids 需要删除的库存汇总主键
     * @return 结果
     */
    @Override
    public int deleteWmsItemStorageByIds(String[] ids) {
        return wmsItemStorageMapper.deleteWmsItemStorageByIds(ids);
    }

    /**
     * 删除库存汇总信息
     *
     * @param id 库存汇总主键
     * @return 结果
     */
    @Override
    public int deleteWmsItemStorageById(String id) {
        return wmsItemStorageMapper.deleteWmsItemStorageById(id);
    }

    /**
     * 根据物料编码查询库存汇总
     *
     * @param code 代码
     * @return {@link WmsItemStorage}
     */
    @Override
    public WmsItemStorage selectWmsItemStorageByItemCode(String code) {
        WmsItemStorage wmsItemStorage = new WmsItemStorage();
        wmsItemStorage.setItemCode(code);
        return wmsItemStorageMapper.selectWmsItemStorage(wmsItemStorage);
    }

    @Override
    public WmsItemStorage selectWmsItemStorage(WmsItemStorage wmsItemStorage) {
        return wmsItemStorageMapper.selectWmsItemStorage(wmsItemStorage);
    }

    @Override
    public List<WmsItemStorage> selectWmsItemStorageByCompany(WmsItemStorage wmsItemStorage) {
        return wmsItemStorageMapper.selectWmsItemStorageByCompany(wmsItemStorage);
    }

    @Override
    public List<ItemStorageIndexCountVO> selectStorageIndexCount(String itemCode) {
        return wmsItemStorageMapper.selectStorageIndexCount(itemCode);
    }

    @Override
    public BigDecimal getItemStorageBatchNoQuantity(WmsItemStorage itemInfo) {
        return wmsItemStorageMapper.getItemStorageBatchNoQuantity(itemInfo);
    }

}
