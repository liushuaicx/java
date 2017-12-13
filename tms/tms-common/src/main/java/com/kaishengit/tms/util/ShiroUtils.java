package com.kaishengit.tms.util;

import com.kaishengit.tms.entity.Account;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 *shiro的工具类
 * @author liushuai
 */
public class ShiroUtils {
    /**
     * 获取当前登录的对象
     * @return
     */
    public static Account getCurrentUser() {
        return (Account) getSubject().getPrincipal();
    }

    public static Subject getSubject() {

        return SecurityUtils.getSubject();
    }
}
