package com.cictec.ibd.base.app;

import android.content.Context;
import android.text.TextUtils;

import com.cictec.ibd.base.http.HeaderManager;
import com.cictec.ibd.base.utils.AppUtilsKt;
import com.cictec.ibd.base.utils.LogUtil;
import com.cictec.ibd.base.utils.PreferencesUtils;
import com.hankun.libnavannotation.ApplicationInject;

/**
 * CopyRight (c)2019: <北京中航讯科技股份有限公司>
 *
 * @author HanKun
 * @version 1.0
 * @date 2019/4/23
 */
@ApplicationInject
public class BaseModelApplication extends BaseAppLogic {


    private static Context context;


    private static String deviceId;

    public static Context getContext() {
        return context;
    }


    public static String getDeviceId() {
        return deviceId;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context = mApplication;
        initDeviceId();
        HeaderManager.init();
        LogUtil.e("子模块启动成功");
    }


    /**
     * 初始化设备id，当用户的设备id不存在时，需要去初始化一个设备id
     */
    private void initDeviceId() {
        deviceId = PreferencesUtils.getString(context, "DEVICE_ID");
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = AppUtilsKt.getUuid();
            PreferencesUtils.putString(context, "DEVICE_ID", deviceId);
        }
    }

}
