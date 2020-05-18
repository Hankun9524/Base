package com.cictec.ibd.base.app;

import android.app.Application;
import android.content.res.Configuration;



import com.cictec.ibd.base.core.ScanClassCore;

import java.util.ArrayList;
import java.util.List;

/**
 * CopyRight (c)2019: <北京中航讯科技股份有限公司>
 * <p>
 * 模板Application,主Application必须继承此activity 并实现
 *
 * @author HanKun
 * @version 1.0
 * @date 2019/4/22
 */
public abstract class BaseApplication extends Application {

    private List<Class<? extends BaseAppLogic>> logicList = new ArrayList<>();

    private List<BaseAppLogic> logicClassList = new ArrayList<>();


    /**
     * 主App调用,在这里对模块的Application进行初始化
     */
    protected abstract void initLogic();


    /**
     * 子模块调用对自身进行注册
     *
     * @param logicClass 子模块的实现Application，进行注册
     */
    protected void registerApplicationLogic(Class<? extends BaseAppLogic> logicClass) {
        logicList.add(logicClass);
    }

    /**
     * 对子模块的Application进行初始化
     */
    private void logicCreate() {
        for (Class<? extends BaseAppLogic> logicClass : logicList) {
            try {
                BaseAppLogic appLogic = logicClass.newInstance();
                logicClassList.add(appLogic);
                appLogic.setAppliction(this);
                appLogic.onCreate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //在这里，启动扫描
        ScanClassCore.init(this);
        initLogic();
        logicCreate();
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        for (BaseAppLogic appLogic : logicClassList) {
            appLogic.onTerminate();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        for (BaseAppLogic appLogic : logicClassList) {
            appLogic.onLowMemory();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        for (BaseAppLogic appLogic : logicClassList) {
            appLogic.onTrimMemory(level);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        for (BaseAppLogic appLogic : logicClassList) {
            appLogic.onConfigurationChanged(newConfig);
        }
    }

}
