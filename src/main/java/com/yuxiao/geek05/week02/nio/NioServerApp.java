package com.yuxiao.geek05.week02.nio;

import java.io.IOException;

/**
 * @author yangjunwei
 * @date 2021-08-16 17:39
 */
public class NioServerApp {

    public static void main(String[] args) throws IOException {
        NioServer nioServer = new NioServer();
        nioServer.start();
    }

}
