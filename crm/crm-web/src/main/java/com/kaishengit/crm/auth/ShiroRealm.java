package com.kaishengit.crm.auth;

import com.kaishengit.crm.entity.Dept;
import com.kaishengit.crm.entity.User;
import com.kaishengit.crm.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * shiro领域
 * @author 刘帅
 */
public class ShiroRealm extends AuthorizingRealm {

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 角色或权限认证使用
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取当前登录的对象
        User user = (User) principalCollection.getPrimaryPrincipal();
        //获区当前对象的部门列表
        List<Dept> deptList = userService.findDeptByUserId(user.getId());
        //获取Dept集合中的名称,创建字符串列表
        List<String> deptNameList = new ArrayList<>();
        for (Dept dept:deptList) {
            deptNameList.add(dept.getDeptName());
        }
        //授权信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //将部门名称设为当前用户的角色
        simpleAuthorizationInfo.addRoles(deptNameList);

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
        String userName = usernamePasswordToken.getUsername();
        User user = userService.findByMobile(userName);
        if (user != null) {
            return new SimpleAuthenticationInfo(user,user.getPassword(),getName());
        }
        return null;
    }
}
