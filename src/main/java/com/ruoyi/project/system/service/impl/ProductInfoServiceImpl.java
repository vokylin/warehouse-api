package com.ruoyi.project.system.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.system.domain.ProductInfo;
import com.ruoyi.project.system.mapper.ProductInfoMapper;
import com.ruoyi.project.system.service.IProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 物料主档Service业务层处理
 *
 * @author Rem
 * @date 2023-03-28
 */
@Service
public class ProductInfoServiceImpl implements IProductInfoService {
    @Autowired
    private ProductInfoMapper productInfoMapper;

    /**
     * 查询物料主档
     *
     * @param id 物料主档主键
     * @return 物料主档
     */
    @Override
    public ProductInfo selectProductInfoById(Long id) {
        return productInfoMapper.selectProductInfoById(id);
    }

    /**
     * 查询物料主档列表
     *
     * @param productInfo 物料主档
     * @return 物料主档
     */
    @Override
    public List<ProductInfo> selectProductInfoList(ProductInfo productInfo) {
        return productInfoMapper.selectProductInfoList(productInfo);
    }

    /**
     * 根据物料编码查询物料主档
     *
     * @param itemCode 项目代码
     * @return {@link ProductInfo}
     */
    @Override
    public ProductInfo findByItemCode(String itemCode) {
        return productInfoMapper.findByItemCode(itemCode);
    }

    /**
     * 新增物料主档
     *
     * @param productInfo 物料主档
     * @return 结果
     */
    @Override
    public int insertProductInfo(ProductInfo productInfo) {
        productInfo.setCreateTime(DateUtils.getNowDate());
        return productInfoMapper.insertProductInfo(productInfo);
    }

    /**
     * 修改物料主档
     *
     * @param productInfo 物料主档
     * @return 结果
     */
    @Override
    public int updateProductInfo(ProductInfo productInfo) {
        productInfo.setUpdateTime(DateUtils.getNowDate());
        return productInfoMapper.updateProductInfo(productInfo);
    }

    /**
     * 批量删除物料主档
     *
     * @param ids 需要删除的物料主档主键
     * @return 结果
     */
    @Override
    public int deleteProductInfoByIds(Long[] ids) {
        return productInfoMapper.deleteProductInfoByIds(ids);
    }

    /**
     * 删除物料主档信息
     *
     * @param id 物料主档主键
     * @return 结果
     */
    @Override
    public int deleteProductInfoById(Long id) {
        return productInfoMapper.deleteProductInfoById(id);
    }

    @Override
    public ProductInfo selectByCode(String code) {
        return productInfoMapper.findByItemCode(code);
    }
}
