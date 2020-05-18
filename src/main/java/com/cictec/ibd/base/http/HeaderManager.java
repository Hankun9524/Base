package com.cictec.ibd.base.http;

import com.cictec.ibd.base.app.BaseModelApplication;
import com.cictec.ibd.base.cache.UserLocationCache;
import com.cictec.ibd.base.config.ChannelConfig;
import com.cictec.ibd.base.utils.AppUtilsKt;

import java.util.HashMap;
import java.util.Map;

/**
 * CopyRight (c)2019: <北京中航讯科技股份有限公司>
 * <p>
 * header管理器，如果需要额外的添加header到请求头中，可设置该缓存进入请求头，每次请求头会自动添加该值
 *
 * @author HanKun
 * @version 1.0
 * @date 2020/3/9
 */
public class HeaderManager {

    private static Map<String, String> header = new HashMap<>();


    public static void  init(){
        //系统类型
        header.put("osType", "1");
        //版本名字
        header.put("versionName", AppUtilsKt.getVersionName(BaseModelApplication.getContext()));
        //版本号
        header.put("versionCode", String.valueOf(AppUtilsKt.getVersionCode(BaseModelApplication.getContext())));
        //定位位置
        double[] position = UserLocationCache.getRealPosition();
        header.put("realLatitude", String.valueOf(position[0]));
        header.put("realLongitude", String.valueOf(position[1]));
        //手机厂商
        header.put("deviceBrand", AppUtilsKt.getDeviceBrand());
        //系统版本
        header.put("systemVersion", AppUtilsKt.getSystemVersion());
        //发布渠道
        String chanel = AppUtilsKt.getAppMetaDataValue(BaseModelApplication.getContext(), ChannelConfig.CHANNEL_KEY);
        header.put("channel", ChannelConfig.getChannelCode(chanel));
        //手机型号
        header.put("systemModel", AppUtilsKt.getSystemModel());
        //app定义的id
        header.put("deviceId", BaseModelApplication.getDeviceId());
    }



    /**
     * 添加一个header到请求头中
     *
     * @param key   k
     * @param value v
     */
    public static void addHeader(String key, String value) {
        header.put(key, value);
    }


    /**
     * 对内使用进行header添加，不对外部进行访问
     *
     * @return 返回自定义的header
     */
    protected static Map<String, String> getHeader() {
        return header;
    }


}
