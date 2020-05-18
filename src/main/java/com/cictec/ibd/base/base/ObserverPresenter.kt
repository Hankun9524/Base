package com.cictec.ibd.base.base

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cictec.ibd.base.app.BaseModelApplication
import com.cictec.ibd.base.http.BaseCallback
import com.cictec.ibd.base.http.NetExecute
import com.cictec.ibd.base.model.ObserverModel
import io.reactivex.Observable

/**
 * CopyRight (c)2019: <北京中航讯科技股份有限公司>
 *
 *  集成LiveData 和 Lifecycle 的网络请求
 *
 * @author    HanKun
 * @date      2020/3/20
 * @version   1.0
 */
abstract class ObserverPresenter<T> : BasePresenter<BaseCallback>() {

    var observerModel: ObserverModel<T>
        private set

    init {
        observerModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(BaseModelApplication.getContext() as Application)
                .create(ObserverModel::class.java) as ObserverModel<T>
    }


    /**
     *
     * 绑定观察者
     *
     * @param owner 观察者实现
     * @param observer 观察者处理
     *
     */
    fun observe(owner: LifecycleOwner, observer: Observer<T>) {
        observerModel.getData().observe(owner, observer)
    }


    /**
     * 请求访问
     *
     * @param observable 网络请求
     */
    fun postRequest(observable: Observable<T>) {
        netHelper.execute(observable, object : NetExecute.Callback<T> {
            override fun onStart() {
                instance?.onRequestBegin(this@ObserverPresenter)
            }

            override fun onFinish() {
                instance?.onRequestFinish(this@ObserverPresenter)
            }

            override fun onError(e: String) {
                instance?.onFail(this@ObserverPresenter, e)

            }

            override fun onSuccess(data: T) {
                observerModel.getData().value = data
            }
        })


    }


}