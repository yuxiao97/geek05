package com.yuxiao.geek05.week03.netty;

/**
 * 使用netty实现自定义网关
 *
 * @author yangjunwei
 * @date 2021-08-17 22:41
 */
public class MyNettyGateway {

    public static void main(String[] args) throws InterruptedException {
        DispatchServer dispatchServer = new DispatchServer(8810);
        dispatchServer.start();
    }

}
