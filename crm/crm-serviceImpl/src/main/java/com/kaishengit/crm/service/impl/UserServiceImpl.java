package com.kaishengit.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.*;
import com.kaishengit.crm.example.CustomerExample;
import com.kaishengit.crm.example.DeptExample;
import com.kaishengit.crm.example.UserDeptExample;
import com.kaishengit.crm.example.UserExample;
import com.kaishengit.crm.exception.AuthenticationException;
import com.kaishengit.crm.exception.ServiceException;
import com.kaishengit.crm.mapper.*;
import com.kaishengit.crm.service.UserService;
import com.kaishengit.weixin.WinXinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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
    private WinXinUtil winXinUtil;
    @Autowired
    private SaleChanceRecordMapper saleChanceRecordMapper;

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

        winXinUtil.createDept(deptName,COMPANY_ID,dept.getId());
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
        winXinUtil.createUser(user.getId(),userName, Arrays.asList(deptIdList),mobile);

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

        winXinUtil.deleteUser(id);

    }

    /**
     * 查找所有账户
     *
     * @return
     */
    @Override
    public List<User> findAllUser() {

        return userMapper.selectByExample(new UserExample());
    }

    /**
     * 查找客户对应的销售进度
     *
     * @param id
     * @return
     */
    @Override
    public List<SaleChanceRecord> findSaleChanceRecordList(Integer id) {
         List<SaleChanceRecord> saleChanceRecordList = saleChanceRecordMapper.findSaleChanceRecordList(id);
         return saleChanceRecordList;
    }

    /**
     * 验证登录
     * @param mobile
     * @return
     */
    @Override
    public User findByMobile(String mobile) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andMobileEqualTo(mobile);
        List<User> userList = userMapper.selectByExample(userExample);
        return userList.get(0);
    }

    /**
     * 查询账户对应的部门列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<Dept> findDeptByUserId(Integer userId) {

        return deptMapper.findDeptByUserId(userId);
    }

}
