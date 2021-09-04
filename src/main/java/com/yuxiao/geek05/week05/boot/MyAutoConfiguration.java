package com.yuxiao.geek05.week05.boot;

import com.yuxiao.geek05.week05.BeanConfiguration;
import org.springframework.context.annotation.Import;

/**
 * 自定义自动装配器(可以在该自动装配类中装载当前starter要完成的启动任务)
 * <p>
 * 通过Import BeanConfiguration来加载所需的Bean资源
 *
 * @author yangjunwei
 * @date 2021-09-04 16:21
 */
@Import({BeanConfiguration.class})
public class MyAutoConfiguration {

}


