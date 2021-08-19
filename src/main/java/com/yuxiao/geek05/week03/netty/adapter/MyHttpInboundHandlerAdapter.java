package com.yuxiao.geek05.week03.netty.adapter;

import com.yuxiao.geek05.week03.netty.filter.MyHttpRequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author yangjunwei
 * @date 2021-08-19 22:17
 */
@Slf4j
public class MyHttpInboundHandlerAdapter extends ChannelInboundHandlerAdapter {

    /**
     * 请求过滤器
     */
    private List<MyHttpRequestFilter> requestFilters;


    protected MyHttpInboundHandlerAdapter(List<MyHttpRequestFilter> requestFilters) {
        this.requestFilters = requestFilters;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            DefaultHttpRequest httpRequest = (DefaultHttpRequest) msg;
            String uri = httpRequest.uri();
            log.info("收到请求：{}", uri);
            if ("/favicon.ico".equals(uri)) {
                log.warn("Ignore request /favicon.ico");
                return;
            }
            if (requestFilters != null && !requestFilters.isEmpty()) {
                requestFilters.forEach(filter -> filter.filter(ctx, httpRequest));
            }
            this.handlerRead(ctx, msg);
        }
    }

    public void handlerRead(ChannelHandlerContext ctx, Object msg) throws Exception{

    }
}
