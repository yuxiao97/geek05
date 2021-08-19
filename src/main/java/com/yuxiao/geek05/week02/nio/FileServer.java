package com.yuxiao.geek05.week02.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 基于Nio实现http的文件服务器
 *
 * @author yangjunwei
 * @date 2021-08-16 22:31
 */
public class FileServer {

    private static final int DEFAULT_PORT = 8848;



    private static final String CRLF = "\r\n";

    private int port;

    private volatile boolean started = false;

    private ThreadPoolExecutor executor = ThreadPoolUtil.getThreadPoolExecutor();

    private FileServerRequestHandler requestHandler = new FileServerRequestHandler();

    private ServerSocketChannel ssc;

    private Selector selector;

    public FileServer() {
        this(DEFAULT_PORT);
    }

    public FileServer(int port) {
        this.port = port;
    }


    public synchronized void start() throws IOException {
        if(started){
            return;
        }
        started = true;
        selector = Selector.open();
        ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(port));
        // 绑定accept(连接)事件
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        startListener();
    }


    /**
     * 启动选择器监听事件
     */
    private void startListener() {
        executor.execute(() -> {
            while (true) {
                // 处理当前客户端异常，保证其他客户端不受影响
                try {
                    // 在选择器上进行选择，阻塞直到事件发生
                    selector.select();
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        iterator.remove();
                        if(selectionKey.isAcceptable()) {
                            // 获取当前客户端连接
                            SocketChannel socketChannel = ssc.accept();
                            // 设置当前连接为非阻塞
                            socketChannel.configureBlocking(false);
                            // 将当前客户端的读事件注册到选择器上，由选择器监听
                            socketChannel.register(selector, SelectionKey.OP_READ);
                        } else if(selectionKey.isReadable()) {
                            // 处理读请求
                            requestHandler.handle((SocketChannel)selectionKey.channel());
                            selectionKey.interestOps(SelectionKey.OP_WRITE);
                        } else if(selectionKey.isWritable()) {

                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
