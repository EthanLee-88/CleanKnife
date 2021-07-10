package com.example.cleanknife.CleanKnife;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 点击事件
 */
// 运行时
@Retention(RetentionPolicy.RUNTIME)
// 方法
@Target(ElementType.METHOD)
public @interface OnClick {
    int value();
}
