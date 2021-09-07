package com.yuxiao.geek05.week05.db;

import com.yuxiao.geek05.week05.db.annotation.MyTransactional;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;

/**
 * 自定义事务AOP
 *
 * @author yangjunwei
 * @date 2021-09-07 15:33
 */
@Aspect
@Configuration
public class MyTransactionAop {

    @Autowired
    private MyTransactionManager transactionManager;


    @Around("@annotation(com.yuxiao.geek05.week05.db.annotation.MyTransactional)")
    public void transactionAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Connection connection = transactionManager.getConnection();
        try {
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
        connection.setAutoCommit(true);
        // 释放当前连接
        transactionManager.releaseConnection();
    }

}
