package com.yuxiao.geek05.week03.netty.router;

import com.yuxiao.geek05.week03.netty.ServerAddress;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

/**
 * 实现自定义的随机路由策略
 *
 * @author yangjunwei
 * @date 2021-08-20 23:00
 */
public class MyRandomServerRouter implements ServerRouter {


    /**
     * 从给定服务列表中随机获取一个可用服务，没有可用服务时返回null
     *
     * @param serverAddressesList
     * @return
     */
    @Override
    public ServerAddress pickOne(List<ServerAddress> serverAddressesList, RequestObject requestObject) {
        if (serverAddressesList == null || serverAddressesList.isEmpty()) {
            return null;
        }
        int size = serverAddressesList.size();
        SecureRandom random = new SecureRandom();
        int i = random.nextInt(size);
        return serverAddressesList.get(i);
    }


}
