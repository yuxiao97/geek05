package com.yuxiao.geek05.week02.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Nio服务Accept处理器
 *
 * @author yangjunwei
 * @date 2021-08-16 18:25
 */
public class NioAcceptHandler {


    private ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtil.getThreadPoolExecutor();


    public void handle(SocketChannel socketChannel) {
        threadPoolExecutor.execute(() -> {
            try {
                System.out.println("客户端" + socketChannel.getRemoteAddress() + "已连接");
                ByteBuffer buf = ByteBuffer.allocate(1024);
                buf.put("HTTP/1.1 200\r\n".getBytes());
                buf.put("Content-Type: text/html; charset=utf-8\r\n".getBytes());
                // 输出换行，分隔报文头和报文体
                buf.put("\r\n".getBytes());
                InetSocketAddress remoteAddress = (InetSocketAddress) socketChannel.getRemoteAddress();
                buf.put(("<html><head><title>Hello</title></head><body><h1>你好，Hello ServerSocketChannel >>> " + remoteAddress.getPort() + "</h1></body></html>").getBytes());
                // 模式切换，由写切换成读
                buf.flip();
                socketChannel.write(buf);
                socketChannel.shutdownOutput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

}
