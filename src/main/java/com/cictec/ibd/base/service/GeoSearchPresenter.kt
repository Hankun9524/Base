package com.cictec.ibd.base.service

import com.amap.api.maps.model.LatLng
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.RegeocodeQuery
import com.amap.api.services.geocoder.RegeocodeResult
import com.cictec.ibd.base.app.BaseModelApplication
import com.cictec.ibd.base.base.BasePresenter
import com.cictec.ibd.base.base.ObserverPresenter
import com.cictec.ibd.base.http.BaseCallback
import com.cictec.ibd.base.model.PoiInfo

/**
 * CopyRight (c)2018: <北京中航讯科技股份有限公司>
 *
 *     根据经纬度反查出一个地理未知信息
 *
 * @author    HanKun
 * @date      2018-12-06
 * @version   1.0
 */

class GeoSearchPresenter : ObserverPresenter<PoiInfo>(), GeocodeSearch.OnGeocodeSearchListener {

    private val search = GeocodeSearch(BaseModelApplication.getContext())
    private var query: RegeocodeQuery? = null


    init {
        search.setOnGeocodeSearchListener(this)
    }

    /**
     * 根据经纬度查询
     */
    fun search(latLng: LatLng) {
        val point = LatLonPoint(latLng.latitude, latLng.longitude)
        if (query == null) {
            query = RegeocodeQuery(point, 500f, GeocodeSearch.AMAP)
        } else {
            query!!.point = point
        }
        search.getFromLocationAsyn(query)
    }


    override fun onRegeocodeSearched(result: RegeocodeResult?, p1: Int) {
        instance?.let { _ ->
            result?.let {
                try {
                    val poi = result.regeocodeAddress.pois.first()
                    var address = poi.snippet
                    if (address.isEmpty()) {
                        address = poi.adName
                    }
                    val poiInfo = PoiInfo(
                        poi.title,
                        address,
                        poi.latLonPoint.latitude,
                        poi.latLonPoint.longitude
                    )
                    observerModel.getData().value = poiInfo
                } finally {
                }
            }
        }
    }

    override fun onGeocodeSearched(p0: GeocodeResult?, p1: Int) {

    }

}