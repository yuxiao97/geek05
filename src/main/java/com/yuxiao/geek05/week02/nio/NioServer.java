package com.yuxiao.geek05.week02.nio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author yangjunwei
 * @date 2021-08-16 16:42
 */
public class NioServer {

    private volatile boolean started = false;

    private int port = 8808;

    private NioAcceptHandler nioAcceptHandler = new NioAcceptHandler();

    private ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtil.getThreadPoolExecutor();

    private ServerSocketChannel serverSocketChannel;

    private Selector selector;

    public NioServer(int port) {
        this.port = port;
    }

    public NioServer() {
    }


    public synchronized void start() throws IOException {
        if (started) {
            return;
        }
        started = true;
        serverSocketChannel = ServerSocketChannel.open();
        selector = Selector.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        // 设置非阻塞状态
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("NioServer start success.");
        startListenEvents();
    }

    private void startListenEvents() {
        threadPoolExecutor.execute(() -> {
            while (true) {
                try {
                    // 在选择器上进行选择，阻塞直到事件发生
                    selector.select();
                    // 从选择器上获取发生的事件类型
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
                    while (selectionKeyIterator.hasNext()) {
                        SelectionKey selectionKey = selectionKeyIterator.next();
                        // 连接事件
                        if (selectionKey.isAcceptable()) {
                            // 获取客户端连接
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            // 设置SocketChannel为非阻塞的
                            socketChannel.configureBlocking(false);
                            // 将客户端Channel上的可读事件注册到Selector，进行监听
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            // 向建立好的客户端连接响应数据
                            nioAcceptHandler.handle(socketChannel);
                        } else if (selectionKey.isReadable()) {
                            SocketChannel channel = (SocketChannel) selectionKey.channel();
                            // 使用线程池处理网络数据
                            threadPoolExecutor.execute(() -> {
                                ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                                int readLen = 0;
                                try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
                                    while ((readLen = channel.read(byteBuffer)) != -1) {
                                        baos.write(byteBuffer.array(), 0 ,readLen);
                                    }
                                    channel.write(new ByteBuffer[]{ByteBuffer.wrap("服务端已收到你的消息：".getBytes(StandardCharsets.UTF_8)),
                                            ByteBuffer.wrap(baos.toByteArray())});
                                    channel.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        } else {
                            System.out.println("读事件已就绪");
                            selectionKey.cancel();
                        }
                    }
                    selectionKeys.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
