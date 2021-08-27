package com.yuxiao.geek05.week04.future;

import com.yuxiao.geek05.week02.nio.ThreadPoolUtil;

import java.util.concurrent.*;

/**
 * 异步获取线程执行结果的方式大全
 *
 * @author yangjunwei
 * @date 2021-08-25 22:30
 */
public class FutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureDemo futureDemo = new FutureDemo();
        // 1：使用FutureTask执行带有返回值的任务
        String futureResult = futureDemo.getExecuteResultFromFutureTask();
        System.out.println(futureResult);

        // 2：使用ThreadPoolExecutors的submit执行带有返回值的任务
        futureResult = futureDemo.getExecuteResultFromThreadPool();
        System.out.println(futureResult);

        // 3：使用ThreadPoolExecutors执行自定义的带有返回值的任务
        System.out.println((String) futureDemo.getExecuteResultFromMyCallable());

        // 4：使用CompletableFuture执行带有返回结果的任务，使用内建的执行器执行任务
        futureResult = CompletableFuture.supplyAsync(() ->
                        futureDemo.sayHello("CompletableFuture#supplyAsync by default pool."))
                .join();
        System.out.println(futureResult);

        // 5：使用CompletableFuture.supplyAsync通过自定义提供的线程池执行任务
        futureResult = CompletableFuture.supplyAsync(() -> {
            return futureDemo.sayHello("CompletableFuture#supplyAsync by MyThreadPoolExecutors.");
        }, ThreadPoolUtil.getThreadPoolExecutor()).join();
        System.out.println(futureResult);


        ThreadPoolUtil.getThreadPoolExecutor().shutdown();
    }


    /**
     * 通过线程池执行自定义的MyCallable
     *
     * @param <T>
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private <T> T getExecuteResultFromMyCallable() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtil.getThreadPoolExecutor();
        Future<String> future = threadPoolExecutor.submit(new MyCallable<>());
        return (T) future.get();
    }


    private <T> T getExecuteResultFromThreadPool() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtil.getThreadPoolExecutor();
        Future<String> future = threadPoolExecutor.submit(() -> {
            return sayHello("ThreadPoolExecutor#submit");
        });
        return (T) future.get();
    }


    /**
     * 通过FutureTask获取异步执行的结果
     *
     * @param <T>
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private <T> T getExecuteResultFromFutureTask() throws ExecutionException, InterruptedException {
        FutureTask<T> integerFutureTask = new FutureTask<T>(() -> {
            TimeUnit.SECONDS.sleep(1);
            return (T) sayHello("FutureTask");
        });
        // 执行任务
        integerFutureTask.run();
        // 获取任务结果并返回
        return integerFutureTask.get();
    }


    /**
     * 有返回结果的普通方法
     *
     * @param from 调用方名称
     * @return
     */
    private String sayHello(String from) {
        return "Hello return value from " + from;
    }


    /**
     * 自定带有返回值的任务类
     *
     * @param <T>
     */
    class MyCallable<T> implements Callable<T> {
        @Override
        public T call() throws Exception {
            return (T) sayHello("MyCallable");
        }
    }

}
