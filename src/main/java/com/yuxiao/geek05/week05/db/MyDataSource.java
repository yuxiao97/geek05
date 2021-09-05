package com.yuxiao.geek05.week05.db;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author yangjunwei
 * @date 2021-09-05 10:12
 */
@Component
public class MyDataSource {

    private Connection connection;

    @Bean
    public DataSource dataSource(){
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL("jdbc:mysql://localhost:3306/geek05");
        // setUrl需要完整的连接路径
        //mysqlDataSource.setUrl("");
        mysqlDataSource.setPort(3306);
        mysqlDataSource.setDatabaseName("geek05");
        mysqlDataSource.setPassword("root");
        mysqlDataSource.setUser("root");
        return mysqlDataSource;
    }


    @Bean
    public Connection getConnection(DataSource dataSource) throws SQLException {
        if(connection == null) {
            connection = dataSource.getConnection();
            return connection;
        }
        return connection;
    }

}
