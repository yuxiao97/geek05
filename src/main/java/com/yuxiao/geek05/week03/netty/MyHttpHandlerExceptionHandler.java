package com.yuxiao.geek05.week03.netty;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

/**
 * handler异常处理Handler
 *
 * @author yangjunwei
 * @date 2021-08-19 20:52
 */
@Slf4j
public class MyHttpHandlerExceptionHandler extends ChannelDuplexHandler {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ctx.write(msg, promise.addListener(future -> {
            if (!future.isSuccess()) {
                log.warn("send data to client exception occur: ", future.cause());
            }
        }));
    }
}
