package com.kaishengit.tms.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class AccountRoleKey implements Serializable {
    private Integer accountId;

    private Integer roleId;

    private static final long serialVersionUID = 1L;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}