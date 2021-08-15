package com.yuxiao.geek05.week01;

import com.yuxiao.geek05.week01.CustomClassLoader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 使用自定义加载加载指定路径下的jar、xar(自定义格式)文件
 *
 * @author yangjunwei
 * @date 2021-08-15 20:21
 */
public class ClassLoaderLoadXarTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        CustomClassLoader classLoader = new CustomClassLoader();
        classLoader.loadExtXar("lib");
        Object targetInstance = classLoader.loadClass("Hello").newInstance();
        Method helloMethod = targetInstance.getClass().getMethod("hello");
        helloMethod.invoke(targetInstance);
    }

}
