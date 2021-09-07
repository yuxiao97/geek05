package com.yuxiao.geek05.week05.db;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 自定义事务管理器
 *
 * @author yangjunwei
 * @date 2021-09-05 21:13
 */
@Configuration
public class MyTransactionManager {

    /*
     * 暂存线程使用的数据库连接
     */
    private final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();

    // 使用HikariCP dataSource数据源和连接池
    /*@Autowired
    private Connection connection;*/

    @Autowired
    private HikariDataSource hikariDataSource;

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        if ((connection = connectionThreadLocal.get()) == null) {
            connection = hikariDataSource.getConnection();
            connectionThreadLocal.set(connection);
        }
        return connection;
    }


    public void releaseConnection(){
        connectionThreadLocal.remove();
    }

}