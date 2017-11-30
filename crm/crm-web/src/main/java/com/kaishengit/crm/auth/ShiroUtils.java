package com.kaishengit.crm.auth;

import com.kaishengit.crm.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 *shiro的工具类
 * @author 刘帅
 */
public class ShiroUtils {

    /**
     * 获取当前登录的对象
     * @return
     */
    public static User getCurrentUser() {
        return (User) getSubject().getPrincipal();
    }

    public static Subject getSubject() {

        return SecurityUtils.getSubject();
    }
}
