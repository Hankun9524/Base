package com.cictec.ibd.base.http


import com.cictec.ibd.base.utils.LogUtil
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Response

/**
 * 网络请求的执行器
 *
 *
 * CopyRight (c)2018: <北京中航讯科技股份有限公司>
 * @author    HanKun
 * @date      2018-11-14
 * @version   1.0
 */
class NetExecute {


    /**
     * 请求的基础回调
     *
     *
     * CopyRight (c)2018: <北京中航讯科技股份有限公司>
     * @author    HanKun
     * @date      2019-11-27
     * @version   1.0
     */
    interface BaseCallback {
        /**
         * 请求开始
         */
        fun onStart()

        /**
         * 请求结束
         */
        fun onFinish()

        /**
         * 请求出错
         */
        fun onError(e: String)
    }

    /**
     * 网络请求的回调接口，只需要请求结果的回调
     *
     *
     * CopyRight (c)2018: <北京中航讯科技股份有限公司>
     * @author    HanKun
     * @date      2018-11-14
     * @version   1.0
     */
    interface Callback<T> : BaseCallback {

        /**
         * 请求成功
         */
        fun onSuccess(data: T)

    }


    /**
     * 带整个响应的回调
     *
     * CopyRight (c)2018: <北京中航讯科技股份有限公司>
     * @author    HanKun
     * @date      2019-11-27
     * @version   1.0
     */
    interface ResponseCallback<T> : BaseCallback {


        fun onResponseSuccess(call: Call<T>, response: Response<T>)

    }

    /**
     * 执行请求的具体过程, RxJava的执行过程
     *
     * @param observable 请求
     * @param callback 请求回调
     *
     */
    fun <T> execute(observable: Observable<T>, callback: Callback<T>) {
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<T> {
                override fun onComplete() {
                    callback.onFinish()
                }

                override fun onSubscribe(d: Disposable) {
                    callback.onStart()
                }

                override fun onNext(t: T) {
                    callback.onSuccess(t)
                }

                override fun onError(e: Throwable) {
                    LogUtil.d(e.toString())
                    callback.onFinish()
                    callback.onError(getExceptionMsg(e))
                }
            })
    }


    /**
     * 执行网络请求，Retrofit的执行
     *
     *  @param call  retrofit执行器
     *  @param callback 请求回调
     *
     */
    fun <T> execute(call: Call<T>, callback: Callback<T>) {
        callback.onStart()
        call.enqueue(object : retrofit2.Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                callback.onFinish()
                callback.onError(getExceptionMsg(t))
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                callback.onFinish()
                val t = response.body()
                if (response.code() == 200 && t != null) {
                    callback.onSuccess(t)
                } else {
                    callback.onError(UNKNOWN_ERROR)
                }
            }
        })

    }


    /**
     * 执行网络请求，Retrofit的执行，带有response的回调
     *
     *  @param call  retrofit执行器
     *  @param callback 请求回调
     *
     */
    fun <T> executeResponse(call: Call<T>, callback: ResponseCallback<T>) {
        callback.onStart()
        call.enqueue(object : retrofit2.Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                callback.onFinish()
                callback.onError(getExceptionMsg(t))
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                callback.onFinish()
                val t = response.body()
                if (response.code() == 200 && t != null) {
                    callback.onResponseSuccess(call, response)
                } else {
                    callback.onError(UNKNOWN_ERROR)
                }
            }
        })

    }


}

