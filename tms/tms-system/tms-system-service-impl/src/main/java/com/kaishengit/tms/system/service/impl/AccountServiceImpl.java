package com.kaishengit.tms.system.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.tms.entity.*;
import com.kaishengit.tms.exception.ServiceException;
import com.kaishengit.tms.mapper.AccountLoginLogMapper;
import com.kaishengit.tms.mapper.AccountMapper;
import com.kaishengit.tms.mapper.AccountRoleMapper;
import com.kaishengit.tms.system.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;

/**
 * @author liushuai
 */
@Service
public class AccountServiceImpl implements AccountService {

    private static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountLoginLogMapper accountLoginLogMapper;
    @Autowired
    private AccountRoleMapper accountRoleMapper;



    @Override
    public List<Role> findRoleByAccountId(Integer id) {

        return accountRoleMapper.findByAccountId(id);
    }

    @Override
    public Account findAccountByMobile(String accountMobile) {

        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andAccountMobileEqualTo(accountMobile);
        List<Account> accountList = accountMapper.selectByExample(accountExample);

        if (accountList != null && !accountList.isEmpty()) {
            return accountList.get(0);
        }
        return null;
    }

    @Override
    public void saveLoginLog(Account account, String ip) {

        AccountLoginLog accountLoginLog = new AccountLoginLog();
        accountLoginLog.setAccountId(account.getId());
        accountLoginLog.setLoginIp(ip);
        accountLoginLog.setLoginTime(new Date());
        accountLoginLogMapper.insert(accountLoginLog);
        logger.info("账户{},IP为{},{}登录",account.getId(),ip,new Date());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveAccount(Account account,Integer[] roleId) throws ServiceException {

        //判断账号是否存在
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andAccountMobileEqualTo(account.getAccountMobile());
        List<Account> accountList = accountMapper.selectByExample(accountExample);

        if (accountList != null && !accountList.isEmpty()) {
            throw new ServiceException("该账号已存在");
        }

        //添加账户
        account.setCreateTime(new Date());
        account.setAccountState("正常");
        accountMapper.insert(account);
        //添加角色
        for (Integer id : roleId) {
            //添加关联关系
            AccountRoleKey accountRoleKey = new AccountRoleKey();
            accountRoleKey.setAccountId(account.getId());
            accountRoleKey.setRoleId(id);
            accountRoleMapper.insert(accountRoleKey);
            logger.info("新增账户{},角色id为{}",account.getAccountName(),id);
        }
        //TODO 添加一条日志到本地
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteAccount(Integer accountId) throws ServiceException {

        //TODO 判断是否有其他关联关系
        //删除角色id对应 的角色关联表
        AccountRoleExample accountRoleExample = new AccountRoleExample();
        accountRoleExample.createCriteria().andAccountIdEqualTo(accountId);
        accountRoleMapper.deleteByExample(accountRoleExample);
        //删除角色
        accountMapper.deleteByPrimaryKey(accountId);
        logger.info("删除账号,id为{},并删除对应关联关系",accountId);
    }

    @Override
    public Account findAccountById(Integer id) {
        return accountMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateAccount(Account account, Integer[] roleIdList) {
        //修改账户
        accountMapper.updateByPrimaryKey(account);
        //TODO 修改账户对应的角色
        logger.info("修改账户{},id为{}",account.getAccountName(),account.getId());
    }

    @Override
    public PageInfo<Account> findAccountListByPage(Integer pageNo) {

        PageHelper.startPage(pageNo,10);
        AccountExample accountExample = new AccountExample();
        List<Account> accountList = accountMapper.selectByExample(accountExample);
        return new PageInfo<>(accountList);
    }


}