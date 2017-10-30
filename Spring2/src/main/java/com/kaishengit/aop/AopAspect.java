package com.kaishengit.aop;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AopAspect {

    @Pointcut("execution(* com.kaishengit.service..*.*(..))")
    public void pointcut() {}

    @Before("pointcut()")
    public void before() {
        System.out.println("前置通知");
    }
    @AfterReturning("pointcut()")
    public void returning() {
        System.out.println("后置通知");
    }
    @AfterThrowing("pointcut()")
    public void throwing() {
        System.out.println("异常通知");
    }
    @After("pointcut()")
    public void after() {
        System.out.println("最终通知");
    }
}
