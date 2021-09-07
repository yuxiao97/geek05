package com.yuxiao.geek05.week05.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 自定义JDBC操作模板
 *
 * @author yangjunwei
 * @date 2021-09-07
 */
@Configuration
public class MyJdbcTemplate {

    @Autowired
    private MyTransactionManager transactionManager;

    public ResultSet executeQuery(String sql, Object... params) throws SQLException {
        Connection connection = transactionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement.executeQuery();
    }


    public boolean execute(String sql, Object... params) throws SQLException {
        Connection connection = transactionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement.execute();
    }

}


