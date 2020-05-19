package com.cictec.ibd.base.http

import com.google.gson.annotations.SerializedName

/**
 * CopyRight (c)2019: <北京中航讯科技股份有限公司>
 *
 * 模板消息解码，通过得模板的消息进行解码获取用户的返回请求
 *
 * @author    HanKun
 * @date      2020/3/9
 * @version   1.0
 */
data class ResultBean<T>(
    @SerializedName(value = "code")
    var code: Int = -1,
    @SerializedName(value = "msg", alternate = ["message"])
    var message: String,
    @SerializedName(value = "data")
    val data: T
)