package com.yuxiao.geek05.week03.netty.router;

import com.yuxiao.geek05.week03.netty.ServerAddress;

import java.util.List;

/**
 * 服务挑选路由
 * @author yangjunwei
 * @date 2021-08-20 23:00
 */
public interface ServerRouter {

    /**
     * 从指定服务列表中获取一个服务
     *
     * @param serverAddressesList
     * @param requestObject
     * @return
     */
    ServerAddress pickOne(List<ServerAddress> serverAddressesList, RequestObject requestObject);

}
