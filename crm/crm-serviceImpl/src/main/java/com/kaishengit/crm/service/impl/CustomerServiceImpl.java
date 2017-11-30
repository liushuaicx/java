package com.kaishengit.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.Task;
import com.kaishengit.crm.entity.User;
import com.kaishengit.crm.example.CustomerExample;
import com.kaishengit.crm.example.TaskExample;
import com.kaishengit.crm.exception.ServiceException;
import com.kaishengit.crm.mapper.CustomerMapper;
import com.kaishengit.crm.mapper.TaskMapper;
import com.kaishengit.crm.mapper.UserMapper;
import com.kaishengit.crm.service.CustomerService;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 刘帅
 */
@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TaskMapper taskMapper;
    /**
     * 根据userId查询客户
     *
     * @param pageNO
     * @param userId
     * @return
     */
    @Override
    public PageInfo<Customer> findAllCustomer(Integer pageNO, Integer userId) {

        PageHelper.startPage(pageNO,5);

        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andUserIdEqualTo(userId);
        List<Customer> list = customerMapper.selectByExample(customerExample);
        return new PageInfo<>(list);

    }

    /**
     * 查询所有客户
     *
     * @param userId
     * @return
     */
    @Override
    public List<Customer> findAll(Integer userId) {

        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andUserIdEqualTo(userId);
        return customerMapper.selectByExample(customerExample);
    }

    /**
     * 添加客户,如果已经存在抛出异常
     *
     * @param customer
     * @throws ServiceException
     */
    @Override
    public void addCustomer(Integer userId,Customer customer) throws ServiceException {

        //查询客户是否已经存在
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andMobileEqualTo(customer.getMobile());
        List<Customer> customerList = customerMapper.selectByExample(customerExample);

        if (customerList != null && !customerList.isEmpty()) {
            throw new ServiceException("该客户已存在");
        }
        //添加客户
        Customer cust = new Customer();
        cust.setCustName(customer.getCustName());
        cust.setJobTitle(customer.getJobTitle());
        cust.setMobile(customer.getMobile());
        cust.setAddress(customer.getAddress());
        cust.setTrade(customer.getTrade());
        cust.setLevel(customer.getLevel());
        cust.setSource(customer.getSource());
        cust.setSex(customer.getSex());
        cust.setUpdateTime(customer.getUpdateTime());
        cust.setUserId(userId);
        customerMapper.insertSelective(cust);

    }

    /**
     * 根据id查询Customer对象
     *
     * @param id
     * @return
     */
    @Override
    public Customer findCustomerDetailById(Integer id) {

        return customerMapper.selectByPrimaryKey(id);

    }

    //springEL
    @Value("#{'${customer.trade}'.split(',')}")
    private List<String> tradeList;
    @Value("#{'${customer.source}'.split(',')}")
    private List<String> sourceList;
    /**
     * 查找所有的用户行业
     *
     * @return
     */
    @Override
    public List<String> findAllTrade() {

        return tradeList;
    }

    /**
     * 查找所有的用户来源
     *
     * @return
     */
    @Override
    public List<String> findAllSource() {
        return sourceList;
    }

    /**
     * 查询所有公海客户
     *
     * @return
     */
    @Override
    public PageInfo<Customer> findAllCustomerPublic(Integer pageNO) {

        PageHelper.startPage(pageNO,5);
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andUserIdIsNull();
        List<Customer> customerList = customerMapper.selectByExample(customerExample);
        return new PageInfo<>(customerList);
    }

    /**
     * 删除客户
     *
     * @param id
     */
    @Override
    public void deleteCustomerById(Integer id) {

        customerMapper.deleteByPrimaryKey(id);

    }

    /**
     * 编辑客户
     * @param customer
     */
    @Override
    public void editCustomer(Customer customer) {

        customer.setUpdateTime(new Date());
        customerMapper.updateByPrimaryKeySelective(customer);
    }

    /**
     * 转让客户
     *
     * @param customer
     * @param toUserId
     */
    @Override
    public void changeUser(Customer customer, Integer toUserId) {

        User user = userMapper.selectByPrimaryKey(customer.getUserId());
        customer.setUserId(toUserId);
        customer.setReminder(customer.getReminder()+" "+"从"+user.getUserName()+"转让过来");
        customerMapper.updateByPrimaryKeySelective(customer);

    }

    /**
     * 将客户放入公海
     *
     * @param customer
     */
    @Override
    public void pushPublic(Customer customer) {

        User user = userMapper.selectByPrimaryKey(customer.getUserId());
        customer.setUserId(null);
        customer.setUpdateTime(new Date());
        customer.setReminder(customer.getReminder() +""+ user.getUserName() +"将该客户放入公海");
        customerMapper.updateByPrimaryKey(customer);

    }

    /**
     * 导出Csv文件到输出流
     *
     * @param user
     * @param outputStream
     */
    @Override
    public void exportCsvToOutputStream(User user, OutputStream outputStream) throws IOException {

        //根据user查找对应列表
        List<Customer> customerList = findAll(user.getId());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("姓名").append(",")
                .append("性别").append(",")
                .append("职位").append(",")
                .append("电话").append("\r\n");

        for (Customer customer : customerList) {
            stringBuilder.append(customer.getCustName()).append(",")
                    .append(customer.getSex()).append(",")
                    .append(customer.getJobTitle()).append(",")
                    .append(customer.getMobile()).append("\r\n");
        }
        IOUtils.write(stringBuilder.toString(),outputStream,"GBK");
        outputStream.flush();
        outputStream.close();

    }

    /**
     * 导出Csv文件到输出流
     * @param user
     * @param outputStream
     */
    @Override
    public void exportXlsToOutputStream(User user, OutputStream outputStream) throws IOException {

        //根据user查找对应列表
        List<Customer> customerList = findAll(user.getId());
        //创建工作表 Workbook
        Workbook workbook = new HSSFWorkbook();
        //创建sheet,页签
        Sheet sheet = workbook.createSheet("我的客户");
        //创建行 Row
        Row row = sheet.createRow(0);
        //创建单元格
        row.createCell(0).setCellValue("姓名");
        row.createCell(1).setCellValue("性别");
        row.createCell(2).setCellValue("职位");
        row.createCell(3).setCellValue("电话");

        for (int i = 0; i< customerList.size();i++) {
            //获得循环对象
            Customer customer = customerList.get(i);
            //创建数据行
            Row dataRow = sheet.createRow(i+1);
            dataRow.createCell(0).setCellValue(customer.getCustName());
            dataRow.createCell(1).setCellValue(customer.getSex());
            dataRow.createCell(2).setCellValue(customer.getJobTitle());
            dataRow.createCell(3).setCellValue(customer.getMobile());
        }

        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();

    }

    /**
     * 查询customer星级对象集合
     * @return
     */
    @Override
    public List<Map<String, Object>> findCustomerCountByLever(Integer userId) {

        List<Map<String, Object>> result = customerMapper.findCustomerCountByLever(userId);
        return result;
    }

    /**
     * 查询每月客户数量
     *
     * @param id
     * @return
     */
    @Override
    public List<Map<String, Object>> findCustomerCountByCreateTime(Integer id) {

        List<Map<String, Object>> result = customerMapper.findCustomerCountByCreateTime(id);

        return result;
    }

    /**
     * 查询对应客户id对应的待办事务
     *
     * @param id
     * @return
     */
    @Override
    public List<Task> findTaskList(Integer id) {

        TaskExample taskExample = new TaskExample();
        taskExample.createCriteria().andCustIdEqualTo(id);
        taskExample.createCriteria().andDoneEqualTo((byte)0);
        List<Task> taskList = taskMapper.selectByExample(taskExample);
        return taskList;
    }


}
