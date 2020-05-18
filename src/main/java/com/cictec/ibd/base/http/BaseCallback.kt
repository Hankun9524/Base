package com.cictec.ibd.base.http

import com.cictec.ibd.base.annotation.OnRequestFailed
import com.cictec.ibd.base.annotation.OnRequestFinish
import com.cictec.ibd.base.annotation.OnRequestStart


/**
 * CopyRight (c)2019: <北京中航讯科技股份有限公司>
 *
 *
 *
 * @author    HanKun
 * @date      2020/3/9
 * @version   1.0
 */
interface BaseCallback {

    /**
     * 请求开始
     *
     * @param presenter 当前实例对象
     */
    @OnRequestStart
    fun onRequestBegin(presenter: Any?)

    /**
     * 请求结束
     *
     * @param presenter 当前实例对象
     */
    @OnRequestFinish
    fun onRequestFinish(presenter: Any?)

    /**
     * 请求失败
     *
     * @param presenter 当前实例对象
     * @param msg 失败的消息
     */
    @OnRequestFailed
    fun onFail(presenter: Any?, msg: String?)
}




