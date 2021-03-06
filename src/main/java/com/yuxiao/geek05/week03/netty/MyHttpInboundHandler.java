package com.yuxiao.geek05.week03.netty;

import com.yuxiao.geek05.week02.OKHttpClient;
import com.yuxiao.geek05.week03.netty.adapter.MyHttpInboundHandlerAdapter;
import com.yuxiao.geek05.week03.netty.filter.MyHttpRequestFilter;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.Response;

import java.util.List;

/**
 * Http请求入站处理器
 *
 * @author yangjunwei
 * @date 2021-08-19 21:49
 */
@Slf4j
public class MyHttpInboundHandler extends MyHttpInboundHandlerAdapter {


    public MyHttpInboundHandler(List<MyHttpRequestFilter> requestFilters) {
        super(requestFilters);
    }


    /**
     * 向服务端请求的客户端
     */
    private OKHttpClient okHttpClient = new OKHttpClient();


    /**
     * 后端可用服务
     */
    private UrlListenServer urlListenServer = new UrlListenServer();


    /**
     * 将受到的请求转发到httpClient上
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void handlerRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            DefaultHttpRequest httpRequest = (DefaultHttpRequest) msg;
            String uri = httpRequest.uri();
            String filterMagic = httpRequest.headers().get("magic");
            log.info("Header Magic:{}", filterMagic);
            // 处理转发逻辑，获取后端可用的服务
            List<ServerAddress> availableServer = urlListenServer.getAvailableServer(uri);
            if (availableServer.isEmpty()) {
                //throw new RuntimeException("["+uri+"]，请求出错，没有可用服务");
                // todo: 出现异常后，如何做全局处理并返回客户端？bad_gateway
            }
            ServerAddress serverAddress = availableServer.get(0);
            StringBuilder url = new StringBuilder("http://").append(serverAddress.getHost())
                    .append(":").append(serverAddress.getPort()).append("/");
            Response response = okHttpClient.get(url.toString());
            response.headers().newBuilder().set("magic", filterMagic).build();
            ctx.write(response);
        }
    }
}
