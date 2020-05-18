package com.cictec.ibd.base.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast提示
 *
 * CopyRight (c)2018: <北京中航讯科技股份有限公司>
 * @author    HanKun
 * @date      2018-08-21
 * @version   1.0
 */
public final class ToastUtil {

    private static Toast sToast = null;



    public static void showShortToast(Context context, CharSequence msg) {
        if (context == null) {
            return;
        }
        if (sToast == null) {
            sToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(msg);
            sToast.setDuration(Toast.LENGTH_SHORT);
        }
        sToast.show();
    }

    public static void showLongToast(Context context, CharSequence msg) {
        if (context == null) {
            return;
        }
        if (sToast == null) {
            sToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        } else {
            sToast.setText(msg);
            sToast.setDuration(Toast.LENGTH_SHORT);
        }
        sToast.show();
    }

    public static void showShortToast(Context context, int msgId) {
        if (context == null) {
            return;
        }
        if (sToast == null) {
            sToast = Toast.makeText(context, msgId, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(msgId);
            sToast.setDuration(Toast.LENGTH_SHORT);
        }
        sToast.show();
    }

    public static void showLongToast(Context context, int msgId) {
        if (context == null) {
            return;
        }
        if (sToast == null) {
            sToast = Toast.makeText(context, msgId, Toast.LENGTH_LONG);
        } else {
            sToast.setText(msgId);
            sToast.setDuration(Toast.LENGTH_SHORT);
        }
        sToast.show();
    }

    public static void cancelToast() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }
}
