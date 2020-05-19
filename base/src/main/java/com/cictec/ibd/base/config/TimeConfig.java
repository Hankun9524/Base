package com.cictec.ibd.base.config;


import android.text.TextUtils;

import com.cictec.ibd.base.utils.LogUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * CopyRight (c)2019: <北京中航讯科技股份有限公司>
 * <p>
 * 服务器的时间矫正
 *
 * @author HanKun
 * @version 1.0
 * @date 2019/11/27
 */
public class TimeConfig {

    private static long timeDiff = 0;


    public static void computerTime(String time) {
        if (!TextUtils.isEmpty(time)) {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH);
            TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
            try {
                Date serverDate = simpleDateFormat.parse(time);
                LogUtil.i(serverDate.toString());
                timeDiff = serverDate.getTime() - System.currentTimeMillis();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 获取服务器时间
     *
     * @return long  与服务器校正后的时间戳
     */
    public static long getTimeFromServer() {
        return System.currentTimeMillis() + timeDiff;
    }


}
