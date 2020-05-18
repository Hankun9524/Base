package com.cictec.ibd.base.core;



import com.cictec.ibd.base.cache.InstanceCache;
import com.cictec.ibd.base.exception.NoMethodExecuteException;
import com.cictec.ibd.base.exception.UninitializedlException;

import java.lang.reflect.Method;

/**
 * CopyRight (c)2019: <北京中航讯科技股份有限公司>
 * <p>
 * 代理的实现
 *
 * @author HanKun
 * @version 1.0
 * @date 2020/3/9
 */
public class Invoke {


    /**
     * 初始化装载实例
     *
     * @param imp 接口
     */
    public static void initInstance(Class<?> imp) {
        MethodReflex.initInstance(imp);
    }


    /**
     * 方法开始执行时
     *
     * @param instance 实例对象
     * @param args     带入的参数
     */
    public static void invokeOnStart(Object instance, Class<?> imp, Object... args) throws Exception {
        //先获取实例的开始执行方法
        invoke(instance, imp, MethodType.ON_START, args);
    }


    /**
     * 方法执行完成时
     *
     * @param instance 实现类
     * @param args     带入的参数
     */
    public static void invokeOnFinish(Object instance, Class<?> imp, Object... args) throws Exception {
        invoke(instance, imp, MethodType.ON_FINISH, args);
    }


    /**
     * 方法执行失败时
     *
     * @param instance 实例对象
     * @param args     参数
     */
    public static void invokeOnFailed(Object instance, Class<?> imp, Object... args) throws Exception {
        invoke(instance, imp, MethodType.ON_FAILED, args);
    }


    /**
     * 方法执行成功时
     *
     * @param instance 实例对象
     * @param args     参数
     */
    public static void invokeOnSuccess(Object instance, Class<?> imp, Object... args) throws Exception {
        invoke(instance, imp, MethodType.ON_SUCCESS, args);
    }


    private static void invoke(Object instance, Class<?> imp, MethodType type, Object... args) throws Exception {
        if (!InstanceCache.isExit(imp)) {
            throw new UninitializedlException(instance.getClass().getName() + "未进行初始化");
        } else {
            Method method = InstanceCache.get(imp, type);
            if (method == null) {
                throw new NoMethodExecuteException(instance.getClass().getName() + "未注解开的方法" + type.name());
            } else {
                method.invoke(instance, args);
            }
        }
    }

}
