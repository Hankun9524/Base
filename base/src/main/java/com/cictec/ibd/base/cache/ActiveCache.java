package com.cictec.ibd.base.cache;


import com.cictec.ibd.base.fragment.ErrorInfoFragment;
import com.hankun.libnavannotation.NavInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 需要登录的界面的缓存处理
 * <p>
 * CopyRight (c)2020: <北京中航讯科技股份有限公司>
 *
 * @author HanKun
 * @version 1.0
 * @date 2020-03-01
 */
public class ActiveCache {

    /**
     * 子模块的application注册
     */
    private static List<String> applicationCache = new ArrayList<>();


    private static Map<String, NavInfo> navInfoMap = new HashMap<>();


    public static void setNavInfoMap(NavInfo navInfo) {
        navInfoMap.put(navInfo.getPageUrl(), navInfo);
    }

    public static void setNavInfoMap(Map<String, NavInfo> src) {
        navInfoMap.putAll(src);
    }

    /**
     * 设置需要启动的子模块路径
     *
     * @param applicationPath 启动路径
     */
    public static void setApplicationPath(String applicationPath) {
        applicationCache.add(applicationPath);
    }

    /**
     * 设置多个启动模块
     *
     * @param strings
     */
    public static void setAllApplicationPath(List<String> strings) {
        applicationCache.addAll(strings);
    }


    /**
     * 获取启动的子模块全部的路径
     *
     * @return 子模块的application 路径
     */
    public static List<String> getApplicationCache() {
        return applicationCache;
    }


    /**
     * 设置需要登录的页面的拦截
     *
     * @param clsPath 需要登录的页面
     */
    private static boolean isNeedLogin(String clsPath) {
        for (Map.Entry<String, NavInfo> stringNavInfoEntry : navInfoMap.entrySet()) {
            NavInfo navInfo = stringNavInfoEntry.getValue();
            if (navInfo.getClassName().equals(clsPath) && navInfo.isNeedLogin()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断当前的操作是否需要进行登录
     *
     * @param activeName 活动的名称
     * @return 是否需要进行登录页面的跳转  true 需要跳转登录页面， false 不需要跳转登录页面
     */
    public static boolean needLogin(String activeName) {
        //先判断是否是需要登录的页面
        if (isNeedLogin(activeName)) {
            //需要登录的，判断用户是否已经登录,如果是登录则不需要继续登录，如果是未登录则需要进行登录
            return !UserLoginCache.isLoginSuccess();
        } else {
            //不需要登录，直接跳过
            return false;
        }
    }


    /**
     * 返回路由的名称，如果访问的路由名称不存在，则返回一个错误的页面
     *
     * @param routerName 路由模块的名称
     * @return 返回路由的全路径
     */
    public static String getRouterPath(String routerName) {
        if (navInfoMap.containsKey(routerName)) {
            return navInfoMap.get(routerName).getClassName();
        } else {
            return ErrorInfoFragment.class.getName();
        }
    }


}
