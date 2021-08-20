package com.yuxiao.geek05.week03.netty.router;

import com.yuxiao.geek05.week03.netty.ServerAddress;

import java.util.List;
import java.util.Map;

/**
 * 根据请求路径和参数计算hash，获取一个可用服务
 *
 * @author yangjunwei
 * @date 2021-08-20 23:00
 */
public class MyHashServerRouter implements ServerRouter {


    /**
     * 请求uri
     */
    private String uri;

    /**
     * url参数
     */
    private Map<String, String> params;

    /**
     * body参数
     */
    private Object body;


    /**
     * 根据请求信息从可用服务列表中挑选一个服务
     *
     * @param serverAddressesList
     * @param requestObject
     * @return
     */
    @Override
    public ServerAddress pickOne(List<ServerAddress> serverAddressesList, RequestObject requestObject) {
        // todo: 需要考虑请求信息该如何封装
        return null;
    }
}
