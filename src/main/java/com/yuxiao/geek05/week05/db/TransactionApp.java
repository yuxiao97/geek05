package com.yuxiao.geek05.week05.db;

import com.yuxiao.geek05.week05.pojo.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.sql.SQLException;

/**
 * @author yangjunwei
 * @date 2021-09-05 21:57
 */
@Slf4j
@SpringBootApplication
public class TransactionApp {

    public static void main(String[] args) throws SQLException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(TransactionApp.class, args);
        applicationContext.registerShutdownHook();
        TransactionCrud transactionCrud = applicationContext.getBean(TransactionCrud.class);
        Student student2 = (Student) applicationContext.getBean("student1");
        transactionCrud.insertStudent(student2);
        log.info("{}", transactionCrud.getByStudentId("C202102"));
    }

}
