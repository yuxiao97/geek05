package com.yuxiao.geek05.week02;


import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 单线程的BioServer
 * @author yangjunwei
 * @date 2021-08-10 14:17
 */
@Slf4j
public class BioServerSingleThread {

    private static final int PORT = 8801;

    private static final int BACKLOG = 100;

    public static void main(String[] args) throws IOException {
        // 创建一个ServerSocket，绑定端口port，设置运行等待连接的队列大小(默认50)，超出队列大小后，其他的则拒绝服务
        ServerSocket serverSocket = new ServerSocket(PORT, BACKLOG);
        log.info("服务已启动，等待客户端连接...");
        while (true) {
            try {
                service(serverSocket.accept());
            } catch (IOException e) {
                // 捕获异常，避免影响其他客户端
                e.printStackTrace();
            }
        }
    }


    /**
     * 处理Socket连接
     * @param socket 客户端socket
     * @throws IOException
     */
    private static void service(Socket socket) throws IOException {
        // 响应数据
        String msgBody = "你好，" + socket.getInetAddress() + ">>" + socket.getPort() + "，欢迎加入Single BIO Club~~~";
        PrintStream printStream = new PrintStream(socket.getOutputStream(), true);
        printStream.println("HTTP/1.0 200 OK");
        printStream.println("Content-Type:application/json; charset=utf-8");
        printStream.println("Content-Length:" + msgBody.getBytes().length);
        // 输出空行，区分报文头和报文体
        printStream.println();
        printStream.println(msgBody);
        log.info("给客户端返回数据：{}", msgBody);
        printStream.close();
        socket.close();
    }
}
