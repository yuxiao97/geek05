package com.yuxiao.geek05.week03.netty;

import java.util.Objects;

/**
 * @author yangjunwei
 * @date 2021-08-20 23:01
 */
public class ServerAddress {

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
        return Objects.hashCode(host) ^ Objects.hashCode(port);
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
