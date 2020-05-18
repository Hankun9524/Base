package com.cictec.ibd.base.core;



import com.cictec.ibd.base.cache.InstanceCache;
import com.cictec.ibd.base.http.RequestModel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * CopyRight (c)2019: <北京中航讯科技股份有限公司>
 * <p>
 * 对实例进行初始化，初始化对定义的四个方法进行分类存储
 *
 * @author HanKun
 * @version 1.0
 * @date 2020/3/9
 */
public class MethodReflex {


    /**
     * 初始化接口调用
     *
     * @param imp 接口方法
     */
    static void initInstance(Class<?> imp) {
        //先判断实例的方法是否和接口已经进行了缓存，缓存的命名是应该跟实例以及实现的接口有关系
        if (!InstanceCache.isExit(imp)) {
            List<Method> methods = getMethods(imp);
            if (methods != null) {
                RequestModel requestModel = new RequestModel();
                for (Method method : methods) {
                    Annotation[] annotations = method.getAnnotations();
                    for (Annotation annotation : annotations) {
                        String annotationName = annotation.annotationType().getSimpleName();
                        switch (annotationName) {
                            case "OnRequestStart":
                                //获取接口的参数
                                requestModel.setStartMethod(method);
                                break;
                            case "OnRequestFinish":
                                requestModel.setFinishMethod(method);
                                break;
                            case "OnRequestFailed":
                                requestModel.setFailedMethod(method);
                                break;
                            case "OnRequestSuccess":
                                requestModel.setSuccessMethod(method);
                                break;
                            default:
                                break;
                        }
                    }
                }
                InstanceCache.put(imp, requestModel);
            }
        }
    }


    /**
     * 获取接口中所有的方法
     *
     * @param imp 获取接口的方法
     * @return 接口中所有封装的方法
     */
    public static List<Method> getMethods(Class<?> imp) {
        if (imp.isInterface()) {
            List<Method> methodList = new ArrayList<>(Arrays.asList(imp.getDeclaredMethods()));
            Class<?>[] superImp = imp.getInterfaces();
            if (superImp != null && superImp.length != 0) {
                for (Class<?> aClass : superImp) {
                    methodList.addAll(getMethods(aClass));
                }
            }
            return methodList;
        } else {
            return null;
        }
    }


}
