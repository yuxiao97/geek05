package com.yuxiao.geek05.week03;

import java.util.Iterator;

/**
 * @author yangjunwei
 * @date 2021-08-17 22:25
 */
public class MyArrayListTest {

    public static void main(String[] args) {
        MyArrayList<Integer> arrayList = new MyArrayList<>(10);
        for (int i=1; i<=31; i++) {
            arrayList.add(i);
        }
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println(arrayList.get(i));
        }

        System.out.println("使用自定义迭代器迭代器输出");
        Iterator iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }

}
