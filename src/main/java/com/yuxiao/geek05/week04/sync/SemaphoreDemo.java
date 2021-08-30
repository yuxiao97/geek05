package com.yuxiao.geek05.week04.sync;

import com.yuxiao.geek05.week02.nio.ThreadPoolUtil;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Semaphore测试类，同时可执行的任务数量，类似滑动窗口
 *
 * @author yangjunwei
 * @date 2021-08-29 14:47
 */
public class SemaphoreDemo {

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(3, false);

        ThreadPoolExecutor executor = ThreadPoolUtil.getThreadPoolExecutor();

        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                try {
                    // 获取执行许可
                    semaphore.acquire();
                    if (semaphore.tryAcquire()) {

                    }
                    // do biz logic
                    System.out.println(Thread.currentThread().getName() + "获取到执行许可");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 执行完毕后释放占用的执行许可
                    semaphore.release();
                }
            });
        }
        executor.shutdown();
    }

}
