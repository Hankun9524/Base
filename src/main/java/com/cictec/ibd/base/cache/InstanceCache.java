package com.cictec.ibd.base.cache;

import com.cictec.ibd.base.core.MethodType;
import com.cictec.ibd.base.http.RequestModel;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * CopyRight (c)2019: <北京中航讯科技股份有限公司>
 * <p>
 * 实例的缓存
 *
 * @author HanKun
 * @version 1.0
 * @date 2020/3/9
 */
public class InstanceCache {

    private static Map<String, RequestModel> instanceCache = new HashMap<>();


    public static void put(Class<?> imp, RequestModel method) {
        instanceCache.put(getKey(imp), method);
    }


    public static Method get(Class<?> imp, MethodType methodType) {
        if (isExit(imp)) {
            switch (methodType) {
                case ON_START:
                    return instanceCache.get(getKey(imp)).getStartMethod();
                case ON_FINISH:
                    return instanceCache.get(getKey(imp)).getFinishMethod();
                case ON_FAILED:
                    return instanceCache.get(getKey(imp)).getFailedMethod();
                case ON_SUCCESS:
                    return instanceCache.get(getKey(imp)).getSuccessMethod();
                default:
                    return null;
            }
        } else {
            return null;
        }
    }


    /**
     * 返回是否已经进行了方法和实现的缓存
     *
     * @param imp 接口定义
     * @return 返回是否存在初始化的信息
     */
    public static boolean isExit(Class<?> imp) {
        return instanceCache.containsKey(getKey(imp));
    }


    /**
     * 存储的名字是以实例名和接口名进行拼接以后实现的
     *
     * @param imp 接口定义
     * @return 返回存储的名字
     */
    public static String getKey(Class<?> imp) {
        return imp.getName();
    }


}
