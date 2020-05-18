package com.cictec.ibd.base.config;

import android.text.TextUtils;

/**
 * 渠道编号对应
 *
 * Created by  HanKun on 2018/3/12 0012.
 */

public class ChannelConfig {

    public static final String CHANNEL_KEY = "UMENG_CHANNEL";

    /**
     * 返回渠道对应编号
     *
     * @param key 渠道key
     * @return 返回的提交值
     */
    public static String getChannelCode(String key) {
        if (TextUtils.isEmpty(key)) {
            return "1";
        }
        switch (key) {
            case "guanfang":
                return "1";
            case "xiaomi":
                return "2";
            case "huawei":
                return "3";
            case "tencent":
                return "4";
            case "qh360":
                return "5";
            case "baidu":
                return "6";
            case "meizu":
                return "7";
            case "vivo":
                return "8";
            case "oppo":
                return "9";
            case "samsung":
                return "10";
            case "smartisan":
                return "11";
            default:
                return "1";
        }
    }


}
