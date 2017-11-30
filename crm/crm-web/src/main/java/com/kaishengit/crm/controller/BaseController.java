package com.kaishengit.crm.controller;

import com.kaishengit.crm.auth.ShiroUtils;
import com.kaishengit.crm.entity.User;

import javax.servlet.http.HttpSession;

/**
 * @author 刘帅
 */
public abstract class BaseController {

    public User getCurrentUser(HttpSession session) {

        return ShiroUtils.getCurrentUser();
    }
}
