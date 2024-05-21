package com.ruoyi.project.system.mapper;

import com.ruoyi.project.system.domain.Supplier;

import java.util.List;

/**
 * 供应商Mapper接口
 *
 * @author Rem
 * @date 2023-03-28
 */
public interface SupplierMapper {
    /**
     * 查询供应商
     *
     * @param id 供应商主键
     * @return 供应商
     */
    public Supplier selectSupplierById(String id);

    /**
     * 查询供应商列表
     *
     * @param supplier 供应商
     * @return 供应商集合
     */
    public List<Supplier> selectSupplierList(Supplier supplier);

    /**
     * 新增供应商
     *
     * @param supplier 供应商
     * @return 结果
     */
    public int insertSupplier(Supplier supplier);

    /**
     * 修改供应商
     *
     * @param supplier 供应商
     * @return 结果
     */
    public int updateSupplier(Supplier supplier);

    /**
     * 删除供应商
     *
     * @param id 供应商主键
     * @return 结果
     */
    public int deleteSupplierById(String id);

    /**
     * 批量删除供应商
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupplierByIds(String[] ids);
}
