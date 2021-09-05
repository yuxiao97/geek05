package com.yuxiao.geek05.week05.db.jdbc;

import com.yuxiao.geek05.week05.pojo.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.sql.SQLException;
import java.util.List;

/**
 * @author yangjunwei
 * @date 2021-09-05 10:41
 */
@Slf4j
@SpringBootApplication
public class DbBootApp {


    public static void main(String[] args) throws SQLException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(DbBootApp.class, args);
        MyCrud crud = applicationContext.getBean(MyCrud.class);
        /*Map<String, Student> beansOfType = applicationContext.getBeansOfType(Student.class);
        for (Map.Entry<String, Student> stringStudentEntry : beansOfType.entrySet()) {
            crud.insertStudent(stringStudentEntry.getValue());
        }*/

        Student student = crud.get("C202101");
        log.info("Query student by studentId:{}", student);

        List<Student> students = crud.listStudent();
        log.info("List student:{}", students);
        String studentId = "C202101";
        int result = crud.delete(studentId);
        log.info("Delete student:{} result:{}", studentId, result);
    }

}
