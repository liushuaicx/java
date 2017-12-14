package com.kaishengit.tms.system.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.tms.entity.Account;
import com.kaishengit.tms.entity.Role;
import com.kaishengit.tms.exception.ServiceException;

import java.util.List;

/**
 * @author liushuai
 */
public interface AccountService {


    List<Role> findRoleByAccountId(Integer id);

    Account findAccountByMobile(String accountMobile);

    void saveLoginLog(Account account, String ip);

    void saveAccount(Account account,Integer[] roleId) throws ServiceException;

    void deleteAccount(Integer accountId) throws ServiceException;

    Account findAccountById(Integer id);

    void updateAccount(Account account, Integer[] roleIdList);

    PageInfo<Account> findAccountListByPage(Integer pageNo);
}
