package com.cictec.ibd.base.base

import com.amap.api.maps.MapView
import com.amap.api.maps.model.Marker
import com.amap.api.maps.model.Polyline

/**
 * Created by HanKun on 2017/11/11.
 */
abstract class OverlayManager(mapView: MapView) {

    protected val aMap = mapView.map

    protected val mapView = mapView

    protected var polyLineList = ArrayList<Polyline>()

    protected var markerList = ArrayList<Marker>()


    abstract fun addToMap()

    abstract fun zoomToSpan()

    /**
     * 移除所有Marker
     */
    fun removeAllMarkers() {
        for (marker in markerList) {
            marker.remove()
        }
        markerList.clear()
    }

    /**
     * 移除所有线路标记
     */
    fun removeAllPolyLines() {
        for (polyline in polyLineList) {
            polyline.remove()
        }
        polyLineList.clear()
    }

    /**
     * 移除所有的线路和marker标记
     */
    fun removeAll() {
        aMap.clear()
        polyLineList.clear()
        markerList.clear()
    }


}