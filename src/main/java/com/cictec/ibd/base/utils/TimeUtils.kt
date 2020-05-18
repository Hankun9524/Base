package com.cictec.ibd.base.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * CopyRight (c)2018: <北京中航讯科技股份有限公司>
 * @author    HanKun
 * @date      2018-09-12
 * @version   1.0
 */


/**
 * 时间转换，格式 yyyy-MM-dd HH:mm:ss
 */
val dateFormatDefault = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)

/**
 * 时间转换，格式 yyyy-MM-dd
 */
val dateFormatDate = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)

/**
 * 时间转换，格式 yyyy-MM-dd HH:mm
 */
val dateFormatDateAndMinute = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA)


/**
 * 时间转换格式  yyyy.MM.dd
 *
 */
val dateFormatTimePoint = SimpleDateFormat("yyyy.MM.dd", Locale.CHINA)


/**
 *
 * 时间转换， MM-DD
 *
 */
val dateFormatMonthOfDay = SimpleDateFormat("MM-dd", Locale.CHINA)

/**
 * 时间转换，格式 HH:mm
 */
val dateFormatMinute = SimpleDateFormat("HH:mm", Locale.CHINA)


/**
 * 获取时间戳转换的时间
 *
 * @param timeInMillis 时间戳 默认获取当前时间戳
 * @param dateFormat  转换格式 默认使用默认的时间格式
 *
 * @return 返回按照要求的时间转换格式，输出为格式的字符串模式  默认 yyyy-MM-dd HH:mm:ss
 *
 */
fun getTimeToString(
    timeInMillis: Long = System.currentTimeMillis(),
    dateFormat: SimpleDateFormat = dateFormatDefault
): String =
    dateFormat.format(Date(timeInMillis))


/**
 *  将输入的时间字符串转换为时间戳  默认转换的格式 yyyy-MM-dd HH:mm:ss
 *
 *  转换失败时返回 -1
 *
 *  @param timeString 时间字符串
 *  @param dateFormat 与输入字符串相匹配的时间转换格式
 *
 *  @return 返回转换后的时间戳
 */
fun getTimeFromString(timeString: String, dateFormat: SimpleDateFormat = dateFormatDefault): Long {
    return try {
        dateFormat.parse(timeString).time
    } catch (e: Exception) {
        -1
    }
}

/**
 * 判断两个时间是否截至日期大于开始日期
 *
 * @param startDate 开始时间
 * @param endDate 结束时间
 * @param dateFormat 日期格式
 *
 */
fun checkDateIsOverStart(
    startDate: String,
    endDate: String,
    dateFormat: SimpleDateFormat = dateFormatDefault
): Boolean {
    val startLong = getTimeFromString(startDate, dateFormat)
    val endLong = getTimeFromString(endDate, dateFormat)
    return endLong > startLong
}


private val seconds_of_1minute = 60

private val seconds_of_30minutes = 30 * 60

private val seconds_of_1hour = 60 * 60

private val seconds_of_1day = 24 * 60 * 60

private val seconds_of_15days = seconds_of_1day * 15

private val seconds_of_30days = seconds_of_1day * 30

private val seconds_of_6months = seconds_of_30days * 6

private val seconds_of_1year = seconds_of_30days * 12

/**
 * 天数差
 */
fun dateDifferenceValue(
    startDate: String,
    endDate: String,
    dateFormat: SimpleDateFormat = dateFormatDate
): Int {
    try {
        val pre = Calendar.getInstance()
        val predate = dateFormat.parse(endDate)
        pre.time = predate
        val cal = Calendar.getInstance()
        val date = dateFormatDate.parse(startDate)
        cal.time = date
        if (cal.get(Calendar.YEAR) == pre.get(Calendar.YEAR)) {
            return pre.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return -1
}

fun getTimeRange(time: String): String {
//    val sdf = dateFormatDefault
    val currentTime = System.currentTimeMillis()

    /**获取当前时间*/
    var curDate = Date(currentTime)
    val dataStrNew = dateFormatDefault.format(curDate)
    var startTime = Date()
    try {
        /**将时间转化成Date*/
        curDate = dateFormatDefault.parse(dataStrNew)
        startTime = dateFormatDefault.parse(time)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    /**除以1000是为了转换成秒*/
    val between = (curDate.time - startTime.time) / 1000
    val elapsedTime = (between).toInt()
    if (elapsedTime < seconds_of_1minute) {
        return "刚刚"
    }
    if (elapsedTime < seconds_of_1hour) {

        return "${elapsedTime / seconds_of_1minute}分钟前"
    }
//    if (elapsedTime < seconds_of_1hour) {
//
//        return "半小时前"
//    }
    if (elapsedTime < seconds_of_1day) {
        return "${elapsedTime / seconds_of_1hour}小时前"
    }
    val day = dateDifferenceValue(time, getCurrentDate())
    return when {
        day == 1 -> "昨天  ${time.substringAfterLast(" ").substringBeforeLast(":")}"
        day == 2 -> "前天  ${time.substringAfterLast(" ").substringBeforeLast(":")}"
        elapsedTime < seconds_of_30days -> "${day}天前  ${time.substringAfterLast(" ")
            .substringBeforeLast(
                ":"
            )}"
        else -> time.substringBeforeLast(":")
    }
}

fun getNewData(dayNumber: Int): String {
    try {
        val beginDate = Date()
        val date = Calendar.getInstance()
        date.time = beginDate
        date.set(Calendar.DATE, date.get(Calendar.DATE) + (dayNumber - 1))
        val endDate: Date
        endDate = dateFormatDate.parse(dateFormatDate.format(date.time))
        return dateFormatDate.format(endDate)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return ""
}

/**
 * 获取当前时间
 */
fun getCurrentDate(): String {
    return dateFormatDate.format(Date(System.currentTimeMillis()))
}

/**
 * 相对于当前时间是否过期 只验证天
 */
fun isOverdue(date: String, shift: String): Boolean {
    return System.currentTimeMillis() > getTimeFromString("$date $shift", dateFormatDateAndMinute)
}


/**
 * 根据日期获取当天是周几
 * @param datetime 日期
 * @return 周几
 */
fun dateToWeek(datetime: String): String {
    val weekDays = arrayOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
    val cal = Calendar.getInstance()
    val date: Date
    try {
        date = dateFormatDate.parse(datetime)
        cal.time = date
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    val w = cal.get(Calendar.DAY_OF_WEEK) - 1
    return weekDays[w]
}


fun getGapCount(startDate: Date, endDate: Date): Int {
    val fromCalendar = Calendar.getInstance()
    fromCalendar.time = startDate
    fromCalendar.set(Calendar.HOUR_OF_DAY, 0)
    fromCalendar.set(Calendar.MINUTE, 0)
    fromCalendar.set(Calendar.SECOND, 0)
    fromCalendar.set(Calendar.MILLISECOND, 0)

    val toCalendar = Calendar.getInstance()
    toCalendar.time = endDate
    toCalendar.set(Calendar.HOUR_OF_DAY, 0)
    toCalendar.set(Calendar.MINUTE, 0)
    toCalendar.set(Calendar.SECOND, 0)
    toCalendar.set(Calendar.MILLISECOND, 0)

    val diff = toCalendar.time.time - fromCalendar.time.time
    val oneMil = 1000 * 60 * 60 * 24L
    return (diff / oneMil).toInt()
}
