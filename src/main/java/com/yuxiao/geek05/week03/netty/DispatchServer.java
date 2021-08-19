package com.yuxiao.geek05.week03.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 基于Netty实现请求转发服务
 *
 * @author yangjunwei
 * @date 2021-08-17 22:43
 */
@Slf4j
public class DispatchServer {


    /**
     * 服务监听端口
     */
    private int port;

    public DispatchServer(int port) {
        this.port = port;
    }


    /**
     * 启动服务
     */
    public void start() throws InterruptedException {
        EventLoopGroup boosGroup = new NioEventLoopGroup(2);
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new MyHttpChannelInitializer());
        Channel channel = serverBootstrap.bind(port).sync().channel();
        log.info("Netty Http Server started on {}", "http://localhost:"+port);
        channel.closeFuture().sync();
    }

}
