package com.ruoyi.project.system.mapper;

import com.ruoyi.project.system.domain.ProductInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 物料主档Mapper接口
 *
 * @author Rem
 * @date 2023-03-28
 */
public interface ProductInfoMapper {
    /**
     * 查询物料主档
     *
     * @param id 物料主档主键
     * @return 物料主档
     */
    public ProductInfo selectProductInfoById(Long id);

    /**
     * 查询物料主档列表
     *
     * @param productInfo 物料主档
     * @return 物料主档集合
     */
    public List<ProductInfo> selectProductInfoList(ProductInfo productInfo);


    /**
     * 根据物料编码查询物料主档
     *
     * @param itemCode 项目代码
     * @return {@link ProductInfo}
     */
    ProductInfo findByItemCode(@Param("itemCode") String itemCode);

    /**
     * 新增物料主档
     *
     * @param productInfo 物料主档
     * @return 结果
     */
    public int insertProductInfo(ProductInfo productInfo);

    /**
     * 修改物料主档
     *
     * @param productInfo 物料主档
     * @return 结果
     */
    public int updateProductInfo(ProductInfo productInfo);

    /**
     * 删除物料主档
     *
     * @param id 物料主档主键
     * @return 结果
     */
    public int deleteProductInfoById(Long id);

    /**
     * 批量删除物料主档
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProductInfoByIds(Long[] ids);

}
