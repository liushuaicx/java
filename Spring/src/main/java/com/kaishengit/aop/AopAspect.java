package com.kaishengit.aop;

import org.aspectj.lang.ProceedingJoinPoint;

public class AopAspect {

    public void before() {
        System.out.println("前置通知");
    }
    public void returning() {
        System.out.println("后置通知");
    }
    public void throwing() {
        System.out.println("异常通知");
    }
    public void after() {
        System.out.println("最终通知");
    }
    public Object around(ProceedingJoinPoint point) {

        Object result = null;
        try {
            System.out.println("执行前");
            result = point.proceed();
            System.out.println("执行后");
        } catch (Throwable throwable) {

            System.out.println("异常");
        }finally {
            System.out.println("最终");
        }
        return result;
    }

}
