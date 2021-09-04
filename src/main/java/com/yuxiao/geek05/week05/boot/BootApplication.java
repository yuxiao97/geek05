package com.yuxiao.geek05.week05.boot;

import com.yuxiao.geek05.week05.pojo.School;
import com.yuxiao.geek05.week05.pojo.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * @author yangjunwei
 * @date 2021-09-04 16:23
 */
@Slf4j
@SpringBootApplication
public class BootApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(BootApplication.class, args);
        Map<String, Student> beansOfType = applicationContext.getBeansOfType(Student.class);
        log.info("{}", beansOfType);

        School school = applicationContext.getBean(School.class);
        log.info("{}", school);
    }

}
