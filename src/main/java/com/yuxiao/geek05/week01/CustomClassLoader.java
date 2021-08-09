package com.yuxiao.geek05.week01;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 自定义的类加载器
 * @author yangjunwei
 * @date 2021-08-06 22:21
 */
public class CustomClassLoader extends ClassLoader{


    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Object hello = new CustomClassLoader().findClass("Hello").newInstance();
        Method hello1 = hello.getClass().getMethod("hello");
        hello1.invoke(hello);
    }



    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> aClass = super.findClass(name);
        if (aClass != null) {
            return aClass;
        }
        String filename = name + ".xlass";
        // /开头表示从当前工程的根目录查找，""开始标识从当前目录查找
        // String path = this.getClass().getResource("/").getPath() + filename;
        try {
            InputStream resourceAsStream = this.getResourceAsStream(filename);
            int length = resourceAsStream.available();
            System.out.println("文件长度:" + length);
            byte[] fileBytes = new byte[length];
            // 将数据读取到字节数组中
            resourceAsStream.read(fileBytes);
            byte[] decodedBytes = getDecodeBytes(fileBytes);
            return defineClass(name, decodedBytes, 0, length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("文件加载出错");
    }


    /**
     * 处理字节码数据
     * @param bytes 原字节码数据
     * @return 解码后的字节码
     */
    private byte[] getDecodeBytes(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (255 - bytes[i]);
        }
        return bytes;
    }
}
