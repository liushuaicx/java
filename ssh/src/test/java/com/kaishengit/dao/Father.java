package com.kaishengit.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Father<T,PK> {

    public Father () {
        System.out.println("父类对象");
        //获得子类对象 通过子类创建父类对象,this为子类对象
        Class clazz = this.getClass();
        //如果父类是参数化类型, 准确返回直接父类的 实际类型参数,如果参数化类型不存在则创建该类型
        ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
        //返回实际参数类型参数 的数组
        Type[] types =  parameterizedType.getActualTypeArguments();
        //Class是Type的唯一实现类 强制类型转换
        Class clazz1 = (Class) types[0];
        System.out.println(clazz1);

    }

}
