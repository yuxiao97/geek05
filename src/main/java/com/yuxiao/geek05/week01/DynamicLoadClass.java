package com.yuxiao.geek05.week01;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 动态加载class或jar文件
 *
 * @author yangjunwei
 * @date 2021-08-15 21:11
 */
public class DynamicLoadClass {


    private static CustomClassLoader classLoader = CustomClassLoader.getInstance();

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InterruptedException {
        Object classInstance = Class.forName("Hello", true, classLoader).newInstance();
        Method hello = classInstance.getClass().getMethod("hello");
        hello.invoke(classInstance);
        classInstance = null;
        System.gc();
        TimeUnit.MINUTES.sleep(5);
    }

}
