package com.kaishengit;

import com.kaishengit.proxy.cglib.MyMethodIntercepter;
import com.kaishengit.service.impl.UserServiceImpl;
import net.sf.cglib.proxy.Enhancer;
import org.junit.Test;

public class CglibTestCase {

    @Test
    public void test() {

        //用来产生目标
        Enhancer enhancer = new Enhancer();
        //设置目标对象
        enhancer.setSuperclass(UserServiceImpl.class);
        //设置methodIntercepter接口的实现类(方法拦截器)
        enhancer.setCallback(new MyMethodIntercepter());
        //创建子类
        UserServiceImpl userService = (UserServiceImpl) enhancer.create();
        userService.save();
    }
}
