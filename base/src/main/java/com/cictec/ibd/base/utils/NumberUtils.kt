package com.cictec.ibd.base.utils

import java.math.BigDecimal
import java.text.NumberFormat

/**
 * CopyRight (c)2018: <北京中航讯科技股份有限公司>
 * @author    HanKun
 * @date      2018-09-12
 * @version   1.0
 */


/**
 *
 * 字符串转换数字，异常或者不合法 0
 *
 */
fun String.toIntSafely(defaultValue: Int = 0): Int {
    return try {
        this.toIntOrNull() ?: defaultValue
    } catch (e: Exception) {
        defaultValue
    }
}

/**
 *
 * 字符串转换数字，异常或者不合法 0
 *
 */
fun String.toFloatSafely(defaultValue: Float = 0f): Float {
    return try {
        this.toFloatOrNull() ?: defaultValue
    } catch (e: Exception) {
        defaultValue
    }
}

/**
 *
 * 字符串转换数字，异常或者不合法 0
 *
 */
fun String.toDoubleSafely(defaultValue: Double = 0.0): Double {
    return try {
        this.toDoubleOrNull() ?: defaultValue
    } catch (e: Exception) {
        defaultValue
    }
}


/**
 * 获取字符串内容，如果是null 返回 "" 拼接数据的时候防止null文字显示
 */
fun String.getEmptyReplaceNull(): String {
    return if (this.isEmpty()) {
        ""
    } else {
        this
    }
}


fun getStationDistance(distance: String): String {
    val dis = distance.toIntSafely()
    return if (dis < 1000) {
        "${distance}米"
    } else {
        val temp = dis / 100

        "${temp / 10.0}公里"
    }
}

/**
 * 保留两位小数,进行四舍五入
 * @param d
 * @return
 */
fun saveOneBitTwoRound(d: Double): Double {
    val bd = BigDecimal(d)
    return bd.setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
}

/**
 * 把单位为分的钱转换为元返回
 *
 */
fun transferMoneyUnit(pointMoney: String): String {
    return try {
        val temp = saveOneBitTwoRound(pointMoney.toDoubleSafely() / 100).toString()
        if (temp.length < 3) {
            temp
        } else {
            val pointIndex = temp.length - 2
            if (temp.last() == '0' && temp[pointIndex] == '.') {
                temp.substring(0, pointIndex)
            } else {
                temp
            }
        }
    } catch (e: Exception) {
        pointMoney
    }


}


/**
 * 转货币格式
 */
fun doubleToCurrency(price: Double): String {
    try {
        val ddf = NumberFormat.getCurrencyInstance()
        return ddf.format(price)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return "￥0"
}

/**
 * 货币数字
 */
fun currencyToDouble(currency: String): String {
    return currency.replace("￥", "").trim()
}


