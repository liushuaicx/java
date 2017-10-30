package com.kaishengit;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
//组件自动扫描
@ComponentScan
//基于注解的Aop支持(开启Aspect自动动态代理)
@EnableAspectJAutoProxy
public class ApplicationContext {


}
