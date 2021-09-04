package com.yuxiao.geek05.week05;

import com.yuxiao.geek05.week05.pojo.School;
import com.yuxiao.geek05.week05.pojo.SchoolClass;
import com.yuxiao.geek05.week05.pojo.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * 通过Spring的方式装配一个Bean
 *
 * @author yangjunwei
 * @date 2021-09-04 09:46
 */
@Slf4j
public class LoadBean {

    public static void main(String[] args) {

        ApplicationContext applicationContext = loadBeanByXml();
        // 按指定的名称(id)从Spring容器中获取Bean
        Student student1 = (Student) applicationContext.getBean("student1");
        log.info("student1:{}", student1);

        // 按对象类型获取当前容器中所有该类型的Bean
        Map<String, Student> beansOfType = applicationContext.getBeansOfType(Student.class);
        log.info("All students:{}", beansOfType);

        // 按对象类型获取一个注入了其他Bean的Bean
        SchoolClass schoolClass = applicationContext.getBean(SchoolClass.class);
        log.info("School class have students:{}", schoolClass);

        // 获取使用Component注解定义的bean，该bean中通过Autowire注解注入SchoolClass的bean，
        School school = applicationContext.getBean(School.class);
        log.info("Get bean by component, the bean autowire SchoolClass bean, {}", school.getSchoolClass());


        Student studentC2 = (Student) applicationContext.getBean("student2");
        log.info("Load spring bean student2 by configuration:{}", studentC2);

        applicationContext = loadBeanByAnnotation();
        Map<String, Student> beansOfType1 = applicationContext.getBeansOfType(Student.class);
        log.info("Load spring bean by configuration:{}", beansOfType1);

    }

    private static ApplicationContext loadBeanByXml(){
        return new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    }

    private static ApplicationContext loadBeanByAnnotation(){
        return new AnnotationConfigApplicationContext(BeanConfiguration.class);
    }

}
