package com.ruoyi.project.system.service;

import com.ruoyi.project.system.domain.Customer;

import java.util.List;

/**
 * 客户Service接口
 *
 * @author Rem
 * @date 2023-04-11
 */
public interface ICustomerService {
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
     * 批量删除客户
     *
     * @param ids 需要删除的客户主键集合
     * @return 结果
     */
    public int deleteCustomerByIds(String[] ids);

    /**
     * 删除客户信息
     *
     * @param id 客户主键
     * @return 结果
     */
    public int deleteCustomerById(String id);

    Customer selectByCode(String toCode);
}
