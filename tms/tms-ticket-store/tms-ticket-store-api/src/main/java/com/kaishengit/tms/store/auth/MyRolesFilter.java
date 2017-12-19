package com.kaishengit.tms.store.auth;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 自定义Roles过滤器,满足多个角色的关系
 * @author 刘帅
 */
public class MyRolesFilter extends RolesAuthorizationFilter {


    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {

        //获取当前登录对象
        Subject subject = getSubject(request,response);
        //获取当前登录对象的配置项
        String[] roleNames = (String[]) mappedValue;
        if (roleNames == null && roleNames.length == 0) {
            return true;
        }
        for (String roleName : roleNames) {
            if (subject.hasRole(roleName)) {
                return true;
            }
        }

        return false;
    }
}
