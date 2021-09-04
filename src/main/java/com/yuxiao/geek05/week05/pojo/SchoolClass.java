package com.yuxiao.geek05.week05.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author yangjunwei
 * @date 2021-09-04 10:34
 */
@Data
public class SchoolClass {

    @Autowired
    private List<Student> students;

}
