package com.yuxiao.geek05.week05.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yangjunwei
 * @date 2021-09-04 11:06
 */
@Data
@Component
public class School {

    @Autowired
    private SchoolClass schoolClass;

}
