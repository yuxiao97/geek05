package com.yuxiao.geek05.week03.netty;

import com.yuxiao.geek05.week03.netty.router.MyRandomServerRouter;
import com.yuxiao.geek05.week03.netty.router.ServerRouter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 服务地址动态监听服务
 *
 * @author yangjunwei
 * @date 2021-08-17 23:00
 */
@Slf4j
public class UrlListenServer {

    public UrlListenServer(){
        init();
    }

    /**
     * url到服务的映射缓存
     */
    private ConcurrentHashMap<String, List<ServerAddress>> urlServerMapping = new ConcurrentHashMap<>();

    /**
     * 服务到URL的映射缓存
     */
    private Set<ServerAddress> availableServers = Collections.synchronizedSet(new HashSet<>());


    /**
     * 初始化一个默认的后端服务
     */
    public void init() {
        ServerAddress serverAddress = new ServerAddress();
        serverAddress.setHost("localhost");
        serverAddress.setPort(8801);
        urlServerMapping.put("/", Collections.singletonList(serverAddress));
        availableServers.add(serverAddress);
        log.info("服务映射初始化完毕");
    }


    public boolean addMapping(String uri, ServerAddress serverAddress) {
        List<ServerAddress> serverAddresses = new ArrayList<>();
        if (urlServerMapping.contains(uri)) {
            serverAddresses = urlServerMapping.get(uri);
            serverAddresses.add(serverAddress);
        }
        urlServerMapping.put(uri, serverAddresses);
        availableServers.add(serverAddress);
        return true;
    }


    /**
     * 获取当前请求地址可用的后端服务列表
     *
     * @param url 请求路径
     * @return
     */
    public List<ServerAddress> getAvailableServer(String url) {
        // 延迟删除不可用服务器
        return removeUnavailableServer(urlServerMapping.get(url));
    }


    /**
     * 按指定路由策略获取一个可用的后端服务地址
     *
     * @param url          请求地址
     * @param serverRouter 路由策略
     * @return
     */
    public ServerAddress getAvailableServer(String url, ServerRouter serverRouter) {
        List<ServerAddress> serverAddresses = removeUnavailableServer(urlServerMapping.get(url));
        if (serverRouter != null) {
            return serverRouter.pickOne(serverAddresses, null);
        }
        // 默认使用随机策略
        return new MyRandomServerRouter().pickOne(serverAddresses, null);
    }


    /**
     * 过滤不可用服务器，返回当前可用的服务器
     *
     * @param serverAddresses
     * @return
     */
    private List<ServerAddress> removeUnavailableServer(List<ServerAddress> serverAddresses) {
        if (serverAddresses == null || serverAddresses.isEmpty()) {
            return new ArrayList<>();
        }
        return serverAddresses.stream().filter(address -> {
            if (!availableServers.contains(address)) {
                availableServers.remove(address);
                return false;
            }
            return true;
        }).collect(Collectors.toList());
    }
}


