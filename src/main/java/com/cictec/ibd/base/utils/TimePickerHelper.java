package com.cictec.ibd.base.utils;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import androidx.annotation.NonNull;

import java.util.Calendar;

/**
 * CopyRight (c)2018: <北京中航讯科技股份有限公司>
 *
 * @author HanKun
 * @version 1.0
 * @date 2018-08-21
 */
public class TimePickerHelper {


    /**
     * 时间的回调
     */
    public interface TimePickerCallback {

        /**
         * 时间回调
         *
         * @param hourOfDay 小时
         * @param minute    分钟
         * @param time      HH:mm
         */
        void callback(int hourOfDay, int minute, String time);

    }


    /**
     * 日期的回调
     */
    public interface DatePickerCallback {

        /**
         * 选择日期的回调
         *
         * @param year       年
         * @param month      月
         * @param dayOfMonth 日
         * @param date       yyyy-MM-dd
         */
        void callback(int year, int month, int dayOfMonth, String date);
    }


    /**
     * 时间选择器
     *
     * @param context  上下文
     * @param callback 选择回调
     */
    public static void showTimePicker(@NonNull Context context, @NonNull TimePickerCallback callback) {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog dialog = new TimePickerDialog(context, (view, hourOfDay, minute) -> {
            String sb = getNumOfTen(hourOfDay) +
                    ':' +
                    getNumOfTen(minute);
            callback.callback(hourOfDay, minute, sb);
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        dialog.show();
    }

    private static String getNumOfTen(int num) {
        if (num < 10) {
            return "0" + num;
        } else {
            return String.valueOf(num);
        }
    }


    /**
     * 日期选择器
     *
     * @param context  上下文
     * @param callback 选择回调
     */
    public static void showDatePicker(@NonNull Context context, @NonNull DatePickerCallback callback) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
            String sb = year +
                    "-" +
                    (getNumOfTen(month + 1)) +
                    "-" +
                    getNumOfTen(dayOfMonth);
            callback.callback(year, month, dayOfMonth, sb);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }


    /**
     * 带限制的日期选择器
     *
     * @param context  上下文
     * @param maxDay   最大的天数
     * @param minDay   最小的天数
     * @param callback 回调
     */
    public static void showDatePicker(@NonNull Context context, long maxDay, long minDay, @NonNull DatePickerCallback callback) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
            String sb = year +
                    "-" +
                    (getNumOfTen(month + 1)) +
                    "-" +
                    getNumOfTen(dayOfMonth);
            callback.callback(year, month, dayOfMonth, sb);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        DatePicker datepicker = dialog.getDatePicker();
        datepicker.setMaxDate(maxDay);
        datepicker.setMinDate(minDay);
        dialog.show();
    }


}
