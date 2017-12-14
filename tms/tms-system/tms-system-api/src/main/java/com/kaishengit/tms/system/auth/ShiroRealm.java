package com.kaishengit.tms.system.auth;

import com.kaishengit.tms.entity.Account;
import com.kaishengit.tms.entity.Role;
import com.kaishengit.tms.system.service.AccountService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * shiro领域
 * @author 刘帅
 */
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private AccountService accountService;

    public void setUserService(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 角色或权限认证使用
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取当前登录的对象
        Account account = (Account) principalCollection.getPrimaryPrincipal();
        //获区当前对象的部门列表
        List<Role> roleList = accountService.findRoleByAccountId(account.getId());
        //获取Role集合中的名称,创建字符串列表
        List<String> roleNameList = new ArrayList<>();
        for (Role role : roleList) {
            roleNameList.add(role.getRoleName());
        }
        //授权信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //将部门名称设为当前用户的角色
        simpleAuthorizationInfo.addRoles(roleNameList);

        return simpleAuthorizationInfo;
    }

    /**
     * 登录认证使用
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String mobile = usernamePasswordToken.getUsername();
        Account account = accountService.findAccountByMobile(mobile);
        if (account != null) {
            return new SimpleAuthenticationInfo(account,account.getAccountPassword(),getName());
        }
        return null;
    }
}
