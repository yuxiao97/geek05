package com.yuxiao.geek05.week05.db;

import org.springframework.context.annotation.Import;

/**
 * MyDataSource自动装配
 *
 * @author yangjunwei
 * @date 2021-09-05 10:15
 */
@Import(MyDataSource.class)
public class MyDataSourceAutoConfiguration {
}
