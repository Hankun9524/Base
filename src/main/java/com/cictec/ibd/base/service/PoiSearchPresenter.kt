package com.cictec.ibd.base.service

import com.amap.api.services.core.PoiItem
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.cictec.ibd.base.app.BaseModelApplication
import com.cictec.ibd.base.base.BasePresenter
import com.cictec.ibd.base.base.ObserverPresenter
import com.cictec.ibd.base.cache.UserLocationCache
import com.cictec.ibd.base.http.BaseCallback
import com.cictec.ibd.base.model.PoiInfo


/**
 *
 * Poi检索
 *
 * CopyRight (c)2018: <北京中航讯科技股份有限公司>
 * @author    HanKun
 * @date      2018-12-06
 * @version   1.0
 */
class PoiSearchPresenter : ObserverPresenter<PoiResult>(), PoiSearch.OnPoiSearchListener {


    private var poiSearch: PoiSearch? = null


    /**
     * 搜索热点
     */
    fun searchPoi(key: String, pageSize: Int = 30) {
        val query = PoiSearch.Query(key, null, UserLocationCache.getCityName()).apply {
            cityLimit = true
            pageNum = 0
            isDistanceSort = true
            pageSize
        }
        if (poiSearch == null) {
            poiSearch = PoiSearch(BaseModelApplication.getContext(), query)
            poiSearch!!.setOnPoiSearchListener(this)
        } else {
            poiSearch!!.query = query
        }
        poiSearch!!.searchPOIAsyn()
    }


    override fun onPoiItemSearched(result: PoiItem?, p1: Int) {

    }

    override fun onPoiSearched(result: PoiResult?, p1: Int) {
        result?.let {
            observerModel.getData().value = it
        }
    }


}



