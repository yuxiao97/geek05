package com.yuxiao.geek05.week04.sync;

import com.yuxiao.geek05.week02.nio.ThreadPoolUtil;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * CyclicBarrier测试类
 *
 * @author yangjunwei
 * @date 2021-08-29 14:08
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
            System.out.println("准备Party~~~");
        });

        ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtil.getThreadPoolExecutor();

        threadPoolExecutor.execute(() -> {
            try {
                Thread.sleep(1000);
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName() + "集合完毕");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        threadPoolExecutor.execute(() -> {
            try {
                Thread.sleep(500);
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName() + "集合完毕");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
//        cyclicBarrier.await();
//
//        cyclicBarrier.reset();
//        threadPoolExecutor.execute(() -> {
//            try {
//                Thread.sleep(500);
//                System.out.println(Thread.currentThread().getName() + "喝醉了");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//
//
//        threadPoolExecutor.execute(() -> {
//            try {
//                Thread.sleep(500);
//                System.out.println(Thread.currentThread().getName() + "喝醉了");
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//
//        cyclicBarrier.await();

        //threadPoolExecutor.shutdown();
    }
}
