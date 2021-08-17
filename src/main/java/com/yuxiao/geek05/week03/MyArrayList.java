package com.yuxiao.geek05.week03;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

/**
 * 自定义ArrayList实现
 * @author yangjunwei
 * @date 2021-08-17 22:25
 */
public class MyArrayList<T> implements Iterable {

    /**
     * 默认大小
     */
    private static int DEFAULT_SIZE = 10;

    /**
     * 元素数组
     */
    private volatile Object[] values;


    /**
     * 当前数组大小
     */
    private volatile int capacity;

    /**
     * 元素个数
     */
    private volatile int size;


    public MyArrayList(){
        this(DEFAULT_SIZE);
    }

    public MyArrayList(int initCapacity) {
        size = 0;
        this.capacity = initCapacity;
        values = new Object[initCapacity];
    }

    /**
     * 添加元素
     * @param value
     */
    public void add(T value) {
        // 检查容量是否足够
        ensure();
        values[size++] = value;
    }

    public T get(int idx) {
        return (T) values[idx];
    }



    public int size(){
        return size;
    }


    /**
     * 数组扩容
     */
    private void ensure(){
        if (size == capacity) {
            capacity *= 2;
            Object[] newValues = new Object[capacity];
            System.arraycopy(values, 0, newValues, 0, size);
            values = newValues;
            System.out.println("进行了一次数组扩容，扩容后容量为："+ capacity);
        }
    }


    @NotNull
    @Override
    public Iterator iterator() {
        return new Itr();
    }

    class Itr implements Iterator<T>{

        private Itr(){}

        /**
         * 当前迭代的位置
         */
        private int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public T next() {
            return (T) values[cursor++];
        }
    }
}
