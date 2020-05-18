package com.cictec.ibd.base.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * CopyRight (c)2019: <北京中航讯科技股份有限公司>
 *
 * 数据模型
 *
 * @author    HanKun
 * @date      2020/3/20
 * @version   1.0
 */
class ObserverModel<T> : ViewModel() {

    private var data: MutableLiveData<T>? = null

    fun getData(): MutableLiveData<T> {
        if (data == null) {
            data = MutableLiveData()
        }
        return data!!
    }


}