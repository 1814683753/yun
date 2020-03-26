package com.le.yun.annotion;


import java.lang.annotation.*;
/**
 * @author ：hjl
 * @date ：2020/1/10 12:56
 * @description：
 * @modified By：
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Job{
    String name() default "";
}
