package com.kaishengit.tms.system.auth;

import com.kaishengit.tms.entity.Account;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @author liushuai
 */
public class ShiroUtil {

    public static Account getCurrentAccount() {

        return (Account)getSubject().getPrincipal();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }
}
