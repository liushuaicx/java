package com.kaishengit.crm.controller.interceptor;

import com.kaishengit.crm.entity.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 判断是否登录的拦截器
 * @author 刘帅
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取用户请求的路径
        String requestUrl = request.getRequestURI();

        //如果时静态路径,放行
        if (requestUrl.startsWith("/static/")) {
            return true;
        }
        //如果是登录页,放行
        if ("".equals(requestUrl) || "/".equals(requestUrl)) {
            return true;
        }

        //判断是否已经登录
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("curr_user");
        if (user != null) {
            return true;
        }
        response.sendRedirect("/");
        return false;

    }
}
