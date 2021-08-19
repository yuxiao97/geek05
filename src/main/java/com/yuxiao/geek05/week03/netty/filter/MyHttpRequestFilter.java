package com.yuxiao.geek05.week03.netty.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * http请求过滤器接口
 *
 * @author yangjunwei
 * @date 2021-08-19 22:02
 */
public interface MyHttpRequestFilter {


    /**
     * 在request中和ChannelHandler中做自定义处理
     *
     * @param request 请求对象
     * @param ctx     通道处理上下文
     */
    void filter(ChannelHandlerContext ctx, DefaultHttpRequest request);

}
