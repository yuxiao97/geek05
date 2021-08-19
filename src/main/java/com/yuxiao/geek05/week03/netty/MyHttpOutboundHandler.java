package com.yuxiao.geek05.week03.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

import java.util.List;
import java.util.Map;

/**
 * Http出站处理器
 *
 * @author yangjunwei
 * @date 2021-08-19 21:49
 */
@Slf4j
public class MyHttpOutboundHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        // 处理业务数据
        Response response = (Response) msg;
        byte[] bizBody = response.body().bytes();
        DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK, Unpooled.wrappedBuffer(bizBody));
        Map<String, List<String>> stringListMap = response.headers().toMultimap();
        httpResponse.headers()
                .set("Content-Type", "application/json; charset=utf-8")
                .set("Content-Length", bizBody.length);
        Map<String, List<String>> headers = response.headers().toMultimap();
        headers.entrySet().forEach(entry -> {
            httpResponse.headers().set(entry.getKey(), entry.getValue());
        });
        ctx.writeAndFlush(httpResponse);
    }
}
