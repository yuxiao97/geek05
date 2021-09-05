package com.yuxiao.geek05.week05.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yangjunwei
 * @date 2021-09-05 21:26
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyTransactional {

     Class<? extends Throwable>[] rollback() default Exception.class;

}
