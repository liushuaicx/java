package com.kaishengit.tms.storage.service;

import com.kaishengit.tms.entity.Account;
import com.kaishengit.tms.entity.Role;

import java.util.List;

/**
 * @author liushuai
 */
public interface AccountService {


    List<Role> findRoleByAccountId(Integer id);

    Account findAccountByMobile(String accountMobile);

    void saveLoginLog(Account account, String ip);
}
