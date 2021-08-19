package com.yuxiao.geek05.week02.nio;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 文件服务器请求处理器
 *
 * @author yangjunwei
 * @date 2021-08-16 22:50
 */
public class FileServerRequestHandler {

    private static final int DEFAULT_BUFFER_SIZE = 4096;

    private ThreadPoolExecutor executor = ThreadPoolUtil.getThreadPoolExecutor();

    /**
     * 处理访问请求
     *
     * @param socketChannel
     */
    public void handle(SocketChannel socketChannel) {
        executor.execute(() -> {
            ByteBuffer byteBuffer = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);
            int readLen = 0;
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                while ((readLen = socketChannel.read(byteBuffer)) != -1) {
                    byteBuffer.flip();
                    baos.write(byteBuffer.array(), 0, readLen);
                    byteBuffer.flip();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
