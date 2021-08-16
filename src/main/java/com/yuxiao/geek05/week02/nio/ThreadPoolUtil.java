package com.yuxiao.geek05.week02.nio;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yangjunwei
 * @date 2021-08-16 18:28
 */
public class ThreadPoolUtil {

    /**
     * 禁止外部实例化
     */
    private ThreadPoolUtil(){}

    /**
     * 网络处理线程池
     */
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 20, 0,
            TimeUnit.SECONDS, new LinkedBlockingQueue<>());


    public static ThreadPoolExecutor getThreadPoolExecutor(){
        return executor;
    }

}
