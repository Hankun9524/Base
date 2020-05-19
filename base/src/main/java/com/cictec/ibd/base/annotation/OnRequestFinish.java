package com.cictec.ibd.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CopyRight (c)2019: <北京中航讯科技股份有限公司>
 *
 * 请求结束时
 *
 * @author HanKun
 * @version 1.0
 * @date 2020/3/9
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnRequestFinish {
}
