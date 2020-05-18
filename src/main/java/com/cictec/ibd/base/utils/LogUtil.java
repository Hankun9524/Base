package com.cictec.ibd.base.utils;

import android.util.Log;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * 日志工具
 *
 * @author HanKun
 */
public final class LogUtil {

    private static boolean DEBUG = true;

    public static void setDebug(boolean debug) {
        if (debug) {
            Logger.init().logLevel(LogLevel.FULL);
            DEBUG = true;
        } else {
            Logger.init().logLevel(LogLevel.NONE);
            DEBUG = false;
        }
    }

    public static void v(Object msg) {
        if (msg != null) {
            Logger.v(msg.toString());
        }
    }


    public static void v(String tag, Object msg) {
        if (msg != null && DEBUG) {
            Log.v(tag, msg.toString());
        }
    }


    public static void d(Object msg) {
        if (msg != null) {
            Logger.d(msg.toString());
        }
    }


    public static void d(String tag, Object msg) {
        if (msg != null && DEBUG) {
            Log.d(tag, msg.toString());
        }
    }


    public static void i(Object msg) {
        if (msg != null) {
            Logger.i(msg.toString());
        }
    }


    public static void i(String tag, Object msg) {
        if (msg != null && DEBUG) {
            Log.i(tag, msg.toString());
        }
    }


    public static void w(Object msg) {
        if (msg != null) {
            Logger.w(msg.toString());
        }
    }


    public static void w(String tag, Object msg) {
        if (msg != null && DEBUG) {
            Log.w(tag, msg.toString());
        }
    }

    public static void e(Object msg) {
        if (msg != null) {
            Logger.e(msg.toString());
        }
    }


    public static void e(String tag, Object msg) {
        if (msg != null && DEBUG) {
            Log.e(tag, msg.toString());
        }
    }


}
