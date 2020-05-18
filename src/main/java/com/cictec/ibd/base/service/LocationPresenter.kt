package com.cictec.ibd.base.service

import android.content.Context
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption


/**
 *
 * 定位服务的控制类。 定位参数： 高精度定位，暂定 2 秒刷新点，不使用缓存点来进行
 *
 *
 * CopyRight (c)2018: <北京中航讯科技股份有限公司>
 * @author    HanKun
 * @date      2018-09-20
 * @version   1.0
 */
class LocationPresenter(context: Context) {


    /**
     * 定位获取成功以后的回调，这个信息是没有经过仔细筛选的信息，只判断是否定位成功，定位成功后返回定位信息
     *
     * CopyRight (c)2018: <北京中航讯科技股份有限公司>
     * @author    HanKun
     * @date      2018-09-20
     * @version   1.0
     */
    interface LocationCallback {

        /**
         *
         * 回调定位成功后的信息
         *
         */
        fun onLocationChanged(location: AMapLocation)
    }


    /**
     * locationClient对象
     */
    private var mLocationClient: AMapLocationClient = AMapLocationClient(context)


    fun bindView(instance: LocationCallback) {
        //声明mLocationOption对象
        val mLocationOption: AMapLocationClientOption = AMapLocationClientOption().apply {
            //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
            locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
            //设置定位间隔,单位毫秒,默认为2000ms
            interval = 2000
            //需要返回地址
            isNeedAddress = true
            //不使用缓存定位
            isLocationCacheEnable = false
        }
        mLocationClient.setLocationOption(mLocationOption)
        mLocationClient.setLocationListener { aMapLocation ->
            aMapLocation?.let {
                instance.onLocationChanged(it)
            }
        }


    }

    /**
     *
     * 开始进行定位
     *
     */
    fun onStart() {
        mLocationClient.startLocation()
    }


    /**
     *
     * 停止定位
     *
     */
    fun onStop() {
        mLocationClient.stopLocation()
    }


    fun unBindView() {
        mLocationClient.onDestroy()
    }

}