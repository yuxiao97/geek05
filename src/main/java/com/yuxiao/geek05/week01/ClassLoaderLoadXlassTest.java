package com.yuxiao.geek05.week01;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 使用自定义加载器加载Xlass
 * @author yangjunwei
 * @date 2021-08-15 20:20
 */
public class ClassLoaderLoadXlassTest {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        CustomClassLoader classLoader = CustomClassLoader.getInstance();
        Object targetInstance = classLoader.loadClass("Hello").newInstance();
        Method helloMethod = targetInstance.getClass().getMethod("hello");
        helloMethod.invoke(targetInstance);
    }

}
