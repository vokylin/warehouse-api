package com.ruoyi.project.system.mapper;

import com.ruoyi.project.system.domain.Customer;

import java.util.List;

/**
 * 客户Mapper接口
 *
 * @author Rem
 * @date 2023-04-11
 */
public interface CustomerMapper {
    /**
     * 查询客户
     *
     * @param id 客户主键
     * @return 客户
     */
    public Customer selectCustomerById(String id);

    /**
     * 查询客户列表
     *
     * @param customer 客户
     * @return 客户集合
     */
    public List<Customer> selectCustomerList(Customer customer);

    /**
     * 新增客户
     *
     * @param customer 客户
     * @return 结果
     */
    public int insertCustomer(Customer customer);

    /**
     * 修改客户
     *
     * @param customer 客户
     * @return 结果
     */
    public int updateCustomer(Customer customer);

    /**
     * 删除客户
     *
     * @param id 客户主键
     * @return 结果
     */
    public int deleteCustomerById(String id);

    /**
     * 批量删除客户
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCustomerByIds(String[] ids);

    Customer selectByCode(String toCode);
}
