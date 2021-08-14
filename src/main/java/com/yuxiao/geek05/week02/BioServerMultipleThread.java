package com.yuxiao.geek05.week02;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 多线程版本的BioServer
 * @author yangjunwei
 * @date 2021-08-14 14:01
 */
public class BioServerMultipleThread {

    private static final int PORT = 8802;
    private static final int BACKLOG = 100;

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(PORT, BACKLOG);
        System.out.println("服务已启动，等待接客中~~~");
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(() -> {
                try {
                    service(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }


    /**
     * Socket连接服务方法
     * @param socket 客户端连接的Socket
     */
    private static void service(Socket socket) throws IOException {
        StringBuilder msg = new StringBuilder("你好：")
                .append(socket.getRemoteSocketAddress())
                .append("，欢迎加入Multiple Thread BioServer Geek训练营!")
                .append("\r\n")
                .append(Thread.currentThread().getName())
                .append("技师为您服务！");
        PrintStream printStream = new PrintStream(socket.getOutputStream());
        printStream.println("HTTP/1.1 200 OK");
        printStream.println("Content-Type:" + "application/json; charset=utf-8");
        printStream.println("Content-Length:" + msg.toString().getBytes().length);
        // 输出空行，区分报文头和报文体
        printStream.println();
        // 输出报文体
        printStream.println(msg);
        printStream.close();
        socket.close();
        System.out.println("响应"+socket.getRemoteSocketAddress()+"消息："+msg);
    }

}
