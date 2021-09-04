package com.yuxiao.geek05.week05;

import com.yuxiao.geek05.week05.pojo.School;
import com.yuxiao.geek05.week05.pojo.SchoolClass;
import com.yuxiao.geek05.week05.pojo.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义Bean
 *
 * @author yangjunwei
 * @date 2021-09-04 12:47
 */
//@Configuration
public class BeanConfiguration {

    @Bean
    public Student student1(){
        Student student = new Student();
        student.setStudentId("C202101");
        student.setName("C202101");
        return student;
    }

    @Bean(name = "student2")
    public Student student2(){
        Student student = new Student();
        student.setStudentId("C202102");
        student.setName("C202102");
        return student;
    }

    @Bean
    public SchoolClass schoolClass(){
        return new SchoolClass();
    }

    @Bean
    public School school() {
        return new School();
    }

}
