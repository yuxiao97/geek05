package com.yuxiao.geek05.week03.netty.router;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 封装相关请求对象
 *
 * @author yangjunwei
 * @date 2021-08-20 23:00
 */
@Data
public class RequestObject {

    private String uri;

    private Map<String, String> urlParams;

    private Map<String, List<String>> headers;

    private Object body;

}
