package com.kaishengit.crm.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.Task;
import com.kaishengit.crm.entity.User;
import com.kaishengit.crm.exception.ServiceException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author 刘帅
 */
public interface CustomerService {

    /**
     * 查询所有客户并分页
     * @param pageNO
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

    /**
     * 查找所有的用户行业
     * @return
     */
    List<String> findAllTrade();

    /**
     * 查找所有的用户来源
     * @return
     */
    List<String> findAllSource();

    /**
     * 查询所有公海客户
     * @param pageNO
     * @return
     */
    PageInfo<Customer> findAllCustomerPublic(Integer pageNO);

    /**
     * 删除客户
     * @param id
     */
    void deleteCustomerById(Integer id);

    /**
     * 编辑客户
     * @param customer
     */

    void editCustomer(Customer customer);

    /**
     * 转让客户
     * @param customer
     * @param toUserId
     */
    void changeUser(Customer customer, Integer toUserId);

    /**
     * 将客户放入公海
     * @param customer
     */
    void pushPublic(Customer customer);

    /**
     * 导出Csv文件到输出流
     * @param user
     * @param outputStream
     * @throws IOException
     */
    void exportCsvToOutputStream(User user, OutputStream outputStream) throws IOException;

    /**
     * 导出Csv文件到输出流
     * @param user
     * @param outputStream
     */
    void exportXlsToOutputStream(User user, OutputStream outputStream) throws IOException;

    /**
     * 查询customer星级对象集合
     * @param userId 查询当前对象的客户星级情况
     * @return
     */
    List<Map<String,Object>> findCustomerCountByLever(Integer userId);

    /**
     * 查询每月客户数量
     * @param id
     * @return
     */
    List<Map<String,Object>> findCustomerCountByCreateTime(Integer id);

    /**
     * 查询对应客户id对应的待办事务
     * @param id
     * @return
     */
    List<Task> findTaskList(Integer id);
}
