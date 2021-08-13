package com.yuxiao.geek05.week02;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Bio服务
 * @author yangjunwei
 * @date 2021-08-10 14:17
 */
public class BioServer {

    private static final int PORT = 8801;

    private static final int BACKLOG = 100;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT, BACKLOG);
        byte[] buffer = new byte[1024];
        while (true) {
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            int available = inputStream.available();
            if(available > 0) {
                inputStream.read(buffer, 0, available);
                System.out.println(new String(buffer, 0, available));
            }
            String msgBody = "你好，" + socket.getInetAddress() + ">>" + socket.getPort() + "，欢迎加入BIO Club~~~";
            PrintStream printStream = new PrintStream(socket.getOutputStream());
            printStream.println("HTTP/1.0 200 OK");
            printStream.println("Content-Type: application/json;charset=utf-8");
            printStream.println("Content-Length: " + msgBody.getBytes().length);
            printStream.println();
            printStream.println(msgBody);
            printStream.flush();
        }
    }
}
