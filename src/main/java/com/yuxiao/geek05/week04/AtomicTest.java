package com.yuxiao.geek05.week04;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * 原子类测试
 * 线程安全原理：
 * <li>原子类内部的值对象通过volatile修饰，保证了可见性</li>
 * <li>使用CAS(Compare And Swap)指令，作为乐观锁，通过自旋保证了写操作的原子性</li>
 *
 * @author yangjunwei
 * @date 2021-08-24 22:05
 */
public class AtomicTest {


    private ExecutorService executorService = Executors.newFixedThreadPool(20);


    public static void main(String[] args) throws InterruptedException {
        AtomicTest atomicTest = new AtomicTest();
        int count = 1000;
        atomicTest.testAtomicInteger(count);
        atomicTest.testAtomicLong(count);
        atomicTest.testLongAdder(count);
        atomicTest.executorService.shutdown();
    }


    private Long testLongAdder(int count) throws InterruptedException {
        long start = System.currentTimeMillis();
        LongAdder atomicLong = new LongAdder();
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            executorService.execute(() -> {
                atomicLong.increment();
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        long sum = atomicLong.sum();
        System.out.println("LongAdder execute " + sum + " take:" + (System.currentTimeMillis() - start) + "ms.");
        return sum;
    }


    private Long testAtomicLong(int count) throws InterruptedException {
        long start = System.currentTimeMillis();
        AtomicLong atomicLong = new AtomicLong(0);
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            executorService.execute(() -> {
                atomicLong.incrementAndGet();
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        long l = atomicLong.get();
        System.out.println("AtomicLong execute " + l + " take:" + (System.currentTimeMillis() - start) + "ms.");
        return l;
    }


    private Integer testAtomicInteger(int count) throws InterruptedException {
        long start = System.currentTimeMillis();
        AtomicInteger atomic = new AtomicInteger(0);
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            executorService.execute(() -> {
                atomic.incrementAndGet();
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println("AtomicInteger execute " + count + " take:" + (System.currentTimeMillis() - start) + "ms.");
        return atomic.get();
    }


}
