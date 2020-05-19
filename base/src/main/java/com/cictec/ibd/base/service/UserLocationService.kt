package com.cictec.ibd.base.service

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.amap.api.location.AMapLocation
import com.cictec.ibd.base.cache.UserLocationCache
import com.cictec.ibd.base.utils.GpsUtils
import com.cictec.ibd.base.utils.GpsUtils.getDistance
import com.cictec.ibd.base.utils.LogUtil


/**
 * 获取采集用户位置信息
 *
 * CopyRight (c)2018: <北京中航讯科技股份有限公司>
 * @author    HanKun
 * @date      2018-12-01
 * @version   1.0
 */
class UserLocationService(context: Context) : LocationPresenter.LocationCallback {


    private val locationPresenter = LocationPresenter(context)

    private val handler = Handler(Looper.getMainLooper())

    private var refreshTime = 2

    private val runnable = Runnable { locationPresenter.onStart() }

    init {
        locationPresenter.bindView(this)
    }

    fun onStart() {
        locationPresenter.onStart()
    }


    override fun onLocationChanged(location: AMapLocation) {
        if (location.errorCode == AMapLocation.LOCATION_SUCCESS && (location.cityCode == UserLocationCache.getCityCode())) {
            val current = GpsUtils.gcj02_To_Gps84(location.latitude, location.longitude)
            val last = UserLocationCache.getLocationGps()
            if (null != last && getDistance(current[0], current[1], last[0], last[1]) > 200) {
                UserLocationCache.setLocation(location)
            }
        }
        UserLocationCache.setLocation(location)
        LogUtil.i("定位成功")
        locationPresenter.onStop()
        handler.postDelayed(runnable, refreshTime * 1000L)
        if (refreshTime < 30) {
            refreshTime += refreshTime
        } else {
            refreshTime = 30
        }

    }

    fun onDestroy() {
        LogUtil.i("定位服务销毁")
        refreshTime = 2
        handler.removeCallbacks(runnable)
        locationPresenter.onStop()
    }

}