package com.kaishengit.crm.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.Dept;
import com.kaishengit.crm.entity.User;
import com.kaishengit.crm.exception.AuthenticationException;
import com.kaishengit.crm.exception.ServiceException;


import java.util.List;
import java.util.Map;

/**
 * @author 刘帅
 */
public interface UserService {

    /**
     * 根据用户名密码查询用户
     * @param userName
     * @param password
     * @return
     * @throws AuthenticationException
     */
    User findByNameAndPassword(String userName, String password) throws AuthenticationException;

    /**
     * 查询所有部门
     * @return
     */
    List<Dept> finAllDept();

    /**
     * 添加新部门
     * @param deptName
     * @throws ServiceException 如果部门已存在,抛出异常
     */
    void addDept(String deptName) throws ServiceException;

    /**
     *根据查询参数获取User的分页对象
     * @param queryParam
     * @return
     */
    List<User> pageForUser(Map<String, Object> queryParam);

    /**
     * 根据部门ID获取账号的数量
     * @param deptId
     * @return
     */
    Long userCountByDeptId(Integer deptId);

    /**
     * 添加新账户
     * @param userName
     * @param mobile
     * @param password
     * @param deptId 所属的部门id,可以多选
     */
    void addUser(String userName, String mobile, String password, Integer[] deptId) throws ServiceException;

    /**
     * 根据id删除用户
     * @param id
     */
    void deleteUserById(Integer id);

    /**
     * 根据userId查询客户,并分页
     * @param userId
     * @return
     */
    PageInfo<Customer> findAllCustomer(Integer pageNO , Integer userId);

    /**
     *查询所有客户
     * @param userId
     * @return
     */
    List<Customer> findAll(Integer userId);

    /**
     * 添加客户,如果已经存在抛出异常
     * @param customer
     * @throws ServiceException
     */
    void addCustomer(Integer userId,Customer customer) throws ServiceException;

    /**
     * 根据id查询Customer对象
     * @param id
     * @return
     */
    Customer findCustomerDetailById(Integer id);
}
