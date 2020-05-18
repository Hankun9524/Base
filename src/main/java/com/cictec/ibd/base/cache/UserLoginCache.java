package com.cictec.ibd.base.cache;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.cictec.ibd.base.app.BaseModelApplication;
import com.cictec.ibd.base.utils.LogUtil;
import com.cictec.ibd.base.utils.OpenActiveUtilsKt;
import com.cictec.ibd.base.utils.PreferencesUtils;
import com.google.gson.Gson;

import java.util.Map;


/**
 * CopyRight (c)2019: <北京中航讯科技股份有限公司>
 * <p>
 * 用户登录信息的缓存
 *
 * @author HanKun
 * @version 1.0
 * @date 2020/3/6
 */
public class UserLoginCache {


    /**
     * 用户是否已经登录
     */
    private static boolean loginSuccess = false;


    /**
     * 存储用户的信息
     */
    private static final String USER_KEY = "UserLoginInfo";


    /**
     * 用户是否登录成功
     *
     * @return 登录状态  true:登录成功  false：登录失败
     */
    public static boolean isLoginSuccess() {
        return loginSuccess;
    }


    private static String LOGIN_PAGE_PATH;


    public static void setLoginPagePath(String loginPagePath) {
        LOGIN_PAGE_PATH = loginPagePath;
    }

    /**
     * 设置用户属性和登录状态。当设置的用户状态不为空时，会将登录状态设置为已登录- true，反之如果设置为 null时，登录状态会被置为 未登录-false
     *
     * @param loginInfo 用户属性
     * @param <T>       用户类型
     */
    public static <T> void setUserLoginInfo(T loginInfo) {
        if (loginInfo == null) {
            loginSuccess = false;
            PreferencesUtils.putString(BaseModelApplication.getContext(), USER_KEY, "");
        } else {
            loginSuccess = true;
            PreferencesUtils.putString(BaseModelApplication.getContext(), USER_KEY, new Gson().toJson(loginInfo));
        }
    }


    /**
     * 获取存储的用户信息
     *
     * @param cls 用户实际对象
     * @param <T> 用户类型
     * @return 返回用户存储的信息
     */
    public static <T> T getUserLoginInfo(Class<T> cls) {
        String userInfo = PreferencesUtils.getString(BaseModelApplication.getContext(), USER_KEY);
        if (!isLoginSuccess() || TextUtils.isEmpty(userInfo)) {
            return null;
        } else {
            return new Gson().fromJson(userInfo, cls);
        }
    }


    /**
     * 获取某个字段的值
     *
     * @param filedName 字段名字
     * @return 返回字段的值  如果为空或者异常时返回null
     */
    public static Object getUserParam(String filedName) {
        try {
            Object object = getUserLoginInfo(Object.class);
            if (object != null) {
                Map<String, Object> map = (Map<String, Object>) object;
                return map.get(filedName);
            }
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
        }
        return null;
    }


    /**
     * 退出登录，清空所有缓存的数据
     */
    public static void exitLogin() {
        setUserLoginInfo(null);
    }


    /**
     * 开启登录页面
     *
     * @param context 上下文
     */
    public static void openLogin(Context context) {
        OpenActiveUtilsKt.openFragment(context, LOGIN_PAGE_PATH, "登录", new Bundle());
    }



}
