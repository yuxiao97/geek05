package com.yuxiao.geek05.week03.netty;

import com.yuxiao.geek05.week03.netty.filter.MyHttpRequestHeaderFilter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

import java.util.Collections;

/**
 * @author yangjunwei
 * @date 2021-08-19 21:49
 */
public class MyHttpChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new HttpServerCodec());
        /*
         * 注意此处 OutboundHandler 和 InboundHandler的设置顺序
         * 为什么要将InboundHandler添加到最后呢？
         * 因为请求入站的顺序是从Pipeline的head开始的，如果将OutboundHandler添加到最后，那么在请求返回时是无法执行OutboundHandler的，
         * 因为在pipeline的Handler链条上，执行时处理指针或游标会先寻找InboundHandler类，直到最后一个，游标就会停止，
         * 此时当前执行游标根本就没有经过任何OutboundHandler，所以出站的时候无法执行。
         * 反过来，入站时扫描到了OutboundHandle，但不是InboundHandler所以不执行，但此时游标经过了OutboundHandler，此时，出站的时候就可以执行到OutboundHandler了
         */
        pipeline.addLast(new MyHttpOutboundHandler());
        pipeline.addLast(new MyHttpInboundHandler(Collections.singletonList(new MyHttpRequestHeaderFilter())));
        // fixme : 此处需要一个处理全局handler异常的handler
        pipeline.addLast(new MyHttpHandlerExceptionHandler());
    }
}
