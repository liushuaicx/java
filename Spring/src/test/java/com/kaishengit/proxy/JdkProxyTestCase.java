package com.kaishengit.proxy;

import com.kaishengit.proxy.jdk.MyInvocationHandler;
import com.kaishengit.service.UserService;
import com.kaishengit.service.impl.UserServiceImpl;
import org.junit.Test;

import java.lang.reflect.Proxy;

public class JdkProxyTestCase {


    @Test
    public void proxy() {

        Asus asus = new Asus();

        MyInvocationHandler myInvocationHandler = new MyInvocationHandler(asus);

        //产生的代理类需要接口指向代理类
        Sales sales = (Sales) Proxy.newProxyInstance(asus.getClass().getClassLoader(),asus.getClass().getInterfaces(),
                myInvocationHandler);

        sales.salePC();

    }
    @Test
    public void userServiceImpl() {

        UserServiceImpl userService = new UserServiceImpl();

        MyInvocationHandler myInvocationHandler = new MyInvocationHandler(userService);
                                                        //新的代理实例
         UserService userService1 = (UserService) Proxy.newProxyInstance(userService.getClass().getClassLoader(),userService.getClass().getInterfaces()
        ,myInvocationHandler);
         userService1.save();
    }


}
