package com.yuxiao.geek05.week04.atomic;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

/**
 * 原子类测试
 * 线程安全原理：
 * <li>原子类内部的值对象通过volatile修饰，保证了可见性</li>
 * <li>使用CAS(Compare And Swap)指令，作为乐观锁，通过自旋保证了写操作的原子性</li>
 *
 * @author yangjunwei
 * @date 2021-08-24 22:05
 */
public class AtomicDemo {


    private ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);


    public static void main(String[] args) throws InterruptedException {
        AtomicDemo atomicTest = new AtomicDemo();
        int count = 100;
        atomicTest.testAtomicInteger(count);
        atomicTest.testAtomicLong(count);
        atomicTest.testLongAdder(count);

        System.out.println(atomicTest.testAtomicReference());


        atomicTest.testAtomicReferenceArray();

        atomicTest.testLongAccumulator();

        atomicTest.executorService.shutdown();
    }



    private void testLongAccumulator() throws InterruptedException {
        long start = System.currentTimeMillis();
        LongAccumulator longAccumulator = new LongAccumulator(Long::sum, 0L);
        int count = 100;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 1; i <=count; i++) {
            int finalI = i;
            executorService.execute(() -> {
                longAccumulator.accumulate(finalI);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println("testLongAccumulator "+longAccumulator.longValue()+" take:" + (System.currentTimeMillis() - start) + "ms");
    }


    private void testAtomicReferenceArray() throws InterruptedException {
        long start = System.currentTimeMillis();
        AtomicReferenceArray<Integer> atomicReferenceArray = new AtomicReferenceArray<>(1);
        atomicReferenceArray.set(0, 0);
        int count = 10;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for(int i=1; i<=count; i++) {
            int finalI = i;
            executorService.execute(() -> {
//                atomicReferenceArray.updateAndGet(0, (x) -> {
//                    return x + finalI;
//                });
                while (!atomicReferenceArray.compareAndSet(0, atomicReferenceArray.get(0), atomicReferenceArray.get(0) + finalI)) {
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println("testAtomicReferenceArray "+atomicReferenceArray.get(0)+" take:" + (System.currentTimeMillis() - start) + "ms");
    }


    private String testAtomicReference() throws InterruptedException {
        AtomicReference<String> atomicReference = new AtomicReference<>("哇哈哈");
        int count = 10;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for(int i=0; i<count; i++) {
            executorService.execute(() -> {
                while (!atomicReference.compareAndSet(atomicReference.get(), atomicReference.get() + Thread.currentThread().getId() + "-")) {
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        return atomicReference.get();
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
