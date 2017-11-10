package com.kaishengit.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.Dept;
import com.kaishengit.crm.entity.User;
import com.kaishengit.crm.entity.UserDeptKey;
import com.kaishengit.crm.example.CustomerExample;
import com.kaishengit.crm.example.DeptExample;
import com.kaishengit.crm.example.UserDeptExample;
import com.kaishengit.crm.example.UserExample;
import com.kaishengit.crm.exception.AuthenticationException;
import com.kaishengit.crm.exception.ServiceException;
import com.kaishengit.crm.mapper.CustomerMapper;
import com.kaishengit.crm.mapper.DeptMapper;
import com.kaishengit.crm.mapper.UserDeptMapper;
import com.kaishengit.crm.mapper.UserMapper;
import com.kaishengit.crm.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 刘帅
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private UserDeptMapper userDeptMapper;
    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 公司ID
     */
    private static final Integer COMPANY_ID = 1;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * 用户登录方法
     *
     * @param userName
     * @param password
     * @return 登录成功返回登录成功的对象, 如果登录异常则抛出AuthenticationException异常
     */
    @Override
    public User findByNameAndPassword(String userName, String password) {

        User user = userMapper.findByNameAndPassword(userName, password);
        if (user != null) {

            return user;
        } else {
            throw new AuthenticationException("账号或密码错误");
        }

    }

    /**
     * 查询部门列表
     *
     * @return
     */
    @Override
    public List<Dept> finAllDept() {

        return deptMapper.selectByExample(new DeptExample());
    }

    /**
     * 添加新部门
     *
     * @param deptName
     * @throws ServiceException 如果部门存在抛出异常
     */
    @Override
    public void addDept(String deptName) throws ServiceException {

        //判断deptName是否存在
        DeptExample deptExample = new DeptExample();
        deptExample.createCriteria().andDeptNameEqualTo(deptName);
        List<Dept> deptList = deptMapper.selectByExample(deptExample);

        Dept dept = null;
        if (deptList != null && !deptList.isEmpty()) {
            throw new ServiceException("该部门已存在");
        }

        //添加新部门,使用公司id作为父ID
        dept = new Dept();
        dept.setDeptName(deptName);
        dept.setpId(COMPANY_ID);
        deptMapper.insertSelective(dept);
        logger.info("添加新部门{}", deptName);

    }

    /**
     * 根据查询参数获取User的分页对象
     *
     * @param queryParam
     * @return
     */
    @Override
    public List<User> pageForUser(Map<String, Object> queryParam) {

        Integer start = (Integer) queryParam.get("start");
        Integer length = (Integer) queryParam.get("length");
        Integer deptId = (Integer) queryParam.get("deptId");
        String userName = (String) queryParam.get("userName");
        System.out.println("**************>"+deptId);
        if (deptId == null || COMPANY_ID.equals(deptId)) {
            deptId = null;
        }

        List<User> userList = userMapper.findByDeptId(userName, deptId, start, length);
        return userList;
    }

    /**
     * 根据部门ID获取账号的数量
     *
     * @param deptId
     * @return
     */
    @Override
    public Long userCountByDeptId(Integer deptId) {

        if (deptId == null || COMPANY_ID.equals(deptId)) {
            deptId = null;
        }
        System.out.println("--------------impl------->"+deptId);
        return userMapper.countByDepyId(deptId);
    }

    /**
     * 添加新账户
     *
     * @param userName
     * @param mobile
     * @param password
     * @param deptIdList 所属的部门id,可以多选
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(String userName, String mobile, String password, Integer[] deptIdList) throws ServiceException {

        //判断手机号是否重复
        UserExample userExample = new UserExample();
        userExample.createCriteria().andMobileEqualTo(mobile);
        List<User> userList = userMapper.selectByExample(userExample);

        if (userList != null && !userList.isEmpty()) {
            throw new ServiceException("该手机号已注册");
        }
        //添加新用户
        User user = new User();
        user.setUserName(userName);
        user.setMobile(mobile);
        user.setPassword(password);
        user.setUpdateTime(new Date());
        userMapper.insertSelective(user);

        //添加关联关系
        for (Integer deptId : deptIdList) {

            UserDeptKey userDeptKey = new UserDeptKey();
            userDeptKey.setDeptId(deptId);
            userDeptKey.setUserId(user.getId());
            userDeptMapper.insert(userDeptKey);

        }
        logger.info("添加新账号{}",userName);
    }

    /**
     * 根据id删除用户
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserById(Integer id) {

        //TODO 判断是否有其他关联关系
        //1.首先删除关联关系表
        UserDeptExample userDeptExample = new UserDeptExample();
        userDeptExample.createCriteria().andUserIdEqualTo(id);
        userDeptMapper.deleteByExample(userDeptExample);
        //删除用户
        userMapper.deleteByPrimaryKey(id);


    }

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


}
