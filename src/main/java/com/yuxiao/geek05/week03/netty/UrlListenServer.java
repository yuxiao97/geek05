package com.yuxiao.geek05.week03.netty;

import lombok.EqualsAndHashCode;
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
        return removeUnavailableServer( urlServerMapping.get(url));
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

@EqualsAndHashCode
class ServerAddress {

    private String host;

    private int port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ServerAddress) {
            ServerAddress other = (ServerAddress) obj;
            if (this.host.equals(other.getHost()) && this.port == other.getPort()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "{" +
                "host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
