package com.yuxiao.geek05.week05.db;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author yangjunwei
 * @date 2021-09-05 22:26
 */
@Configuration
public class HikariCPConfig {

    @Bean
    public HikariDataSource hikariDataSource(DataSource dataSource){
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDataSource(dataSource);
        hikariDataSource.setMaximumPoolSize(5);
        hikariDataSource.setMinimumIdle(2);
        return hikariDataSource;
    }

}
