package com.cictec.ibd.base.service;


import java.lang.reflect.Method;

/**
 * CopyRight (c)2019: <北京中航讯科技股份有限公司>
 * <p>
 * 通过代理去执行方法
 *
 * @author HanKun
 * @version 1.0
 * @date 2020/2/13
 */
public class ProxyHelper {

    private Object proxyObject;


    public Object getProxyObject() {
        return proxyObject;
    }

    private ProxyHelper(String proxyClass) {
        try {
            Class<?> cls = Class.forName(proxyClass);
            proxyObject = cls.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 返回一个代理的对象
     *
     * @param proxyClass 被代理的对象
     * @return 返回代理对象
     */
    public static ProxyHelper newInstance(String proxyClass) {
        return new ProxyHelper(proxyClass);
    }


    /**
     * 注册创建一个跨模块的服务
     *
     * @param methodName 需要代理实现的方法
     * @return 返回执行成功的方法
     */
    public Object invoke(String methodName, Object... args) {
        try {
            Class<?>[] cls = new Class<?>[args.length];
            int index = 0;
            for (Object arg : args) {
                Class<?> argType = arg.getClass();
                cls[index] = argType;
                index++;
            }
            Method method = proxyObject.getClass().getDeclaredMethod(methodName, cls);
            return method.invoke(proxyObject, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 释放代理对象
     */
    public void release() {
        proxyObject = null;
    }


}
