package com.yuxiao.geek05.week05.db;

import com.yuxiao.geek05.week05.db.annotation.MyTransactional;
import com.zaxxer.hikari.HikariDataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;

/**
 * @author yangjunwei
 * @date 2021-09-05 21:13
 */
@Aspect
@Configuration
public class TransactionConfig {

    // 使用HikariCP dataSource数据源和连接池
    /*@Autowired
    private Connection connection;*/

    @Autowired
    private HikariDataSource hikariDataSource;

    private final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();

    @Around("@annotation(com.yuxiao.geek05.week05.db.annotation.MyTransactional)")
    public void transactionAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Connection connection = hikariDataSource.getConnection();
        connectionThreadLocal.set(connection);
        try {
            Object[] args = new Object[]{connectionThreadLocal};
            connection.setAutoCommit(false);
            joinPoint.proceed();
        } catch (Throwable e) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            MyTransactional annotation = signature.getMethod().getAnnotation(MyTransactional.class);
            Class[] rollback = annotation.rollback();
            for (Class aClass : rollback) {
                if (aClass.isInstance(e)) {
                    connection.rollback();
                    throw e;
                }
            }
            e.printStackTrace();
        }
        connection.commit();
        connectionThreadLocal.remove();
        connection.setAutoCommit(true);
    }

    public ThreadLocal<Connection> getConnectionThreadLocal() {
        return connectionThreadLocal;
    }

}