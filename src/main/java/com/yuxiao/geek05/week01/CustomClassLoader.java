package com.yuxiao.geek05.week01;

import sun.misc.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 自定义的类加载器（支持jar、xar、class、xlass文件的加载）
 *
 * @author yangjunwei
 * @date 2021-08-06 22:21
 */
public class CustomClassLoader extends ClassLoader {

    /**
     * 加载xlass打包的xar文件
     *
     * @param extLibPath 扩展lib包加载路径
     */
    public void loadExtXar(String extLibPath) throws IOException {
        File extLibFileDir = new File(extLibPath);
        if (extLibFileDir.isDirectory()) {
            File[] files = extLibFileDir.listFiles();
            for (File file : files) {
                JarFile jarFile = new JarFile(file);
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry jarEntry = entries.nextElement();
                    byte[] bytes = IOUtils.readFully(jarFile.getInputStream(jarEntry), (int) jarEntry.getSize(), true);
                    // 解码xar中的xlass文件
                    if (jarFile.getName().endsWith("xar")) {
                        bytes = getDecodeBytes(bytes);
                    }
                    String name = jarEntry.getName();
                    name = name.substring(0, name.lastIndexOf("."));
                    Class<?> loadedClass = findLoadedClass(name);
                    if (loadedClass != null) {
                        System.out.println(name + "已经加载过，无需重复加载");
                        return;
                    }
                    super.defineClass(name, bytes, 0, bytes.length);
                }
            }
        }
    }


    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        // 检查是否已加载过，加载过则直接返回
        Class<?> loadedClass = findLoadedClass(name);
        if (loadedClass != null) {
            return loadedClass;
        }
        String filename = name.substring(name.lastIndexOf(".") + 1) + ".xlass";
        try (InputStream resourceAsStream = this.getResourceAsStream(filename)) {
            // 如果文件为空，则说明不是自定义要加载的类，委托父类进行加载
            if (resourceAsStream == null) {
                return super.findClass(name);
            }
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
     *
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
