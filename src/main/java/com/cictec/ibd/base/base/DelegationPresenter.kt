package com.cictec.ibd.base.base

import com.cictec.ibd.base.core.Invoke
import com.cictec.ibd.base.http.NetExecute
import io.reactivex.Observable
import java.lang.reflect.ParameterizedType

/**
 * CopyRight (c)2019: <北京中航讯科技股份有限公司>
 *
 *  通用可以使用的网络请求，也可以继承该请求
 *
 *
 * @author    HanKun
 * @date      2020/3/10
 * @version   1.0
 */
abstract class DelegationPresenter<I, T> : BasePresenter<I>() {


    /**
     *
     * 泛型接口
     *
     */
    protected lateinit var imp: Class<*>


    init {
        val type = javaClass.genericSuperclass as? ParameterizedType
        if (type != null) {
            val t = type.actualTypeArguments[0]
            imp = t as Class<*>
            Invoke.initInstance(imp)
        }
    }


    /**
     *
     * 网络请求与
     *
     * @param observable  rxjava的网络请求
     *
     */
    fun postRequest(observable: Observable<T>) {
        netHelper.execute(observable, object : NetExecute.Callback<T> {
            override fun onStart() {
                instance?.let {
                    Invoke.invokeOnStart(instance, imp, this@DelegationPresenter)
                }
            }

            override fun onFinish() {
                instance?.let {
                    Invoke.invokeOnFinish(instance, imp, this@DelegationPresenter)
                }
            }

            override fun onError(e: String) {
                instance?.let {
                    Invoke.invokeOnFailed(instance, imp, this@DelegationPresenter, e)
                }

            }

            override fun onSuccess(data: T) {
                instance?.let { Invoke.invokeOnSuccess(instance, imp, data) }
            }
        })


    }


}
