package com.yuxiao.geek05.week02;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 使用线程池的多线程实现BioServer
 *
 * @author yangjunwei
 * @date 2021-08-14 14:19
 */
public class BioServerThreadPool {


    private static final int PORT = 8803;

    private static final int BACKLOG = 10000;

    public static void main(String[] args) throws IOException {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("当前服务器可用核心数：" + availableProcessors);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(availableProcessors*10, availableProcessors * 10,
                60, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        ServerSocket serverSocket = new ServerSocket(PORT, BACKLOG);
        System.out.println("服务已启动，等待客户端连接...");
        while (true) {
            Socket socket = serverSocket.accept();
            threadPoolExecutor.execute(() -> {
                try {
                    service(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

    }

    private static void service(Socket socket) throws IOException {
//        StringBuilder msg = new StringBuilder("你好：")
//                .append(socket.getRemoteSocketAddress())
//                .append("，欢迎加入 Thead Pool BioServer Geek训练营!")
//                .append("\r\n")
//                .append(Thread.currentThread().getName())
//                .append("技师为您服务！");
        StringBuilder msg = new StringBuilder("Hello");
        PrintStream printStream = new PrintStream(socket.getOutputStream());
        printStream.println("HTTP/1.1 200 OK");
        printStream.println("Keep-Alive:false");
        printStream.println("Content-Type:" + "application/json; charset=utf-8");
        printStream.println("Content-Length:" + msg.toString().getBytes().length);
        // 输出空行，区分报文头和报文体
        printStream.println();
        // 输出报文体
        printStream.println(msg);
        printStream.close();
        socket.close();
        //System.out.println("响应"+socket.getRemoteSocketAddress()+"消息："+msg);
    }

}
