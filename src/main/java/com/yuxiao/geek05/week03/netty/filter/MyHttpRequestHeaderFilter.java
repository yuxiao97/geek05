package com.yuxiao.geek05.week03.netty.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * http请求头过滤器
 *
 * @author yangjunwei
 * @date 2021-08-19 22:05
 */
public class MyHttpRequestHeaderFilter implements MyHttpRequestFilter {

    @Override
    public void filter(ChannelHandlerContext ctx, DefaultHttpRequest request) {
        request.headers().set("magic", "yx_netty_http");
    }
}
