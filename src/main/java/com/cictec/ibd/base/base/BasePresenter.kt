package com.cictec.ibd.base.base

import android.app.Activity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.cictec.ibd.base.http.NetExecute
import com.cictec.ibd.base.utils.LogUtil
import org.jetbrains.annotations.NotNull

/**
 * CopyRight (c)2019: <北京中航讯科技股份有限公司>
 *
 *
 *
 * @author    HanKun
 * @date      2020/3/10
 * @version   1.0
 */
abstract class BasePresenter<I> : LifecycleEventObserver {

    val netHelper = NetExecute()


    protected var instance: I? = null

    private val tName = this.javaClass.simpleName


    open fun bindView(lifecycle: Lifecycle, @NotNull instance: I) {
        this.instance = instance
        lifecycle.addObserver(this)
    }

    open fun unBindView() {
        instance = null
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> onCreate(source)
            Lifecycle.Event.ON_START -> onStart(source)
            Lifecycle.Event.ON_RESUME -> onResume(source)
            Lifecycle.Event.ON_PAUSE -> onPause(source)
            Lifecycle.Event.ON_STOP -> onStop(source)
            Lifecycle.Event.ON_DESTROY -> onDestroy(source)
            Lifecycle.Event.ON_ANY -> onLifeChanged(source)
        }
    }

    /**
     * 创建时被调用
     */
    open fun onCreate(source: LifecycleOwner) {

    }

    /**
     * 开始时被调用
     */
    open fun onStart(source: LifecycleOwner) {

    }

    /**
     * 可见时被调用
     */
    open fun onResume(source: LifecycleOwner) {

    }

    /**
     * 暂停时被调用
     */
    open fun onPause(source: LifecycleOwner) {

    }

    /**
     * 停止时被调用
     */
    open fun onStop(source: LifecycleOwner) {

    }

    /**
     * 销毁时被调用
     */
    open fun onDestroy(source: LifecycleOwner) {
        unBindView()
        LogUtil.i(tName, "Presenter source release")
    }

    /**
     * 每次生命周期发生变化时被调用
     */
    open fun onLifeChanged(source: LifecycleOwner) {

    }


}