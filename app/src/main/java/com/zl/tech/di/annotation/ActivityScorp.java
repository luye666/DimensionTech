package com.zl.tech.di.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Administrator on 2019/5/29
 *
 * 限定Activity对象 的作用域
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScorp {
}
