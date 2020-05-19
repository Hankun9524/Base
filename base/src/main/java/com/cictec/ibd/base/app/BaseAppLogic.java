package com.cictec.ibd.base.app;

import android.app.Application;
import android.content.res.Configuration;

/**
 * CopyRight (c)2019: <北京中航讯科技股份有限公司>
 * <p>
 * 用来初始化所有组件模块的Application，子模块可以实现这个方法并进行
 *
 * @author HanKun
 * @version 1.0
 * @date 2019/4/22
 */
public class BaseAppLogic {

    /**
     * Application具体实现类
     */
    protected Application mApplication;


    /**
     * 设置当前模块实现的Application
     *
     * @param application Application具体实现
     */
    public void setAppliction(Application application) {
        this.mApplication = application;
    }


    /**
     * 获取全局的Application
     *
     * @return 当前全局的Application
     */
    public Application getApplication() {
        return mApplication;
    }


    /**
     * 全局创建时调用
     */
    public void onCreate() {

    }


    public void onTerminate() {

    }


    public void onLowMemory() {

    }


    public void onTrimMemory(int level) {

    }


    public void onConfigurationChanged(Configuration newConfig) {

    }


    public void onDestroy() {

    }


}
