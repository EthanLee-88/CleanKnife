package com.example.cleanknife.CleanKnife;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * View 注入
 */
// RUNTIME-运行时，CLASS-编译时，SOURCE-元注解
@Retention(RetentionPolicy.RUNTIME)
//作用于 属性：FIELD ， 方法：METHOD ， 类：TYPE ， 参数：PARAMETER
// 构造器：CONSTRUCTOR ， 注解：ANNOTATION_TYPE
@Target(ElementType.FIELD)
public @interface FindView {
    // 穿 View的 id
    int value();
}
