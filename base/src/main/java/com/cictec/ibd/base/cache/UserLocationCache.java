package com.cictec.ibd.base.cache;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.model.LatLng;
import com.cictec.ibd.base.config.LocationType;
import com.cictec.ibd.base.utils.GpsUtils;

/**
 * CopyRight (c)2019: <北京中航讯科技股份有限公司>
 *
 * @author HanKun
 * @version 1.0
 * @date 2020/3/6
 */
public class UserLocationCache {

    /**
     * 城市中心
     */
    private static double[] CITY_CENTER = {34.2616, 108.947328};

    /**
     * 城市名字
     */
    private static String CITY_NAME = "西安市";

    /**
     * 城市code
     */
    private static String CITY_CODE = "029";


    public static void setCityCenter(double[] cityCenter) {
        CITY_CENTER = cityCenter;
    }


    public static void setCityName(String cityName) {
        CITY_NAME = cityName;
    }

    public static void setCityCode(String cityCode) {
        CITY_CODE = cityCode;
    }





    private static AMapLocation location;

    private static boolean locationSuccess = false;


    /**
     * 定位模式的设置，连续不断的定位还是间断性的定位
     */
    private static LocationType locationType = LocationType.DISCONTINUOUS;


    public static LocationType getLocationType() {
        return locationType;
    }

    public static void setLocationType(LocationType locationType) {
        UserLocationCache.locationType = locationType;
    }

    /**
     * 定位是否成功
     *
     * @return
     */
    public static boolean isLocationSuccess() {
        return locationSuccess;
    }


    /**
     * 获取当前定位的Gps坐标
     *
     * @return
     */
    public static double[] getLocationGps() {
        if (location != null && location.getErrorCode() == AMapLocation.LOCATION_SUCCESS && location.getCityCode().equals(CITY_CODE)) {
            return GpsUtils.gcj02_To_Gps84(location.getLatitude(), location.getLongitude());
        } else {
            return CITY_CENTER;
        }
    }

    public static AMapLocation getLocation() {
        return location;
    }

    public static double[] getCityCenter() {
        return CITY_CENTER;
    }

    public static String getCityName() {
        return CITY_NAME;
    }

    public static String getCityCode() {
        return CITY_CODE;
    }

    /**
     * 获取当前定位点高德的坐标点
     *
     * @return 高德定位点
     */
    public static LatLng getLocationLatLng() {
        if (location != null && location.getErrorCode() == AMapLocation.LOCATION_SUCCESS && location.getCityCode().equals(CITY_CODE)) {

            return new LatLng(location.getLatitude(), location.getLongitude());
        } else {
            return new LatLng(CITY_CENTER[0], CITY_CENTER[1]);
        }
    }


    /**
     * 设置定位信息
     *
     * @param location
     */
    public static void setLocation(AMapLocation location) {
        if (location.getErrorCode() == 0) {
            locationSuccess = true;
        }
        UserLocationCache.location = location;
    }

    /**
     * 获取真实经纬度信息,Gps
     *
     * @return Gps坐标经纬度
     */
    public static double[] getRealPosition() {
        if (location != null && location.getErrorCode() == AMapLocation.LOCATION_SUCCESS) {
            return GpsUtils.gcj02_To_Gps84(location.getLatitude(), location.getLongitude());
        } else {
            return new double[]{0.0, 0.0};
        }
    }


}
