package com.cictec.ibd.base.utils;


import java.util.Locale;

/**
 * 坐标转换和距离计算
 * <p>
 * <p>
 * CopyRight (c)2018: <北京中航讯科技股份有限公司>
 *
 * @author HanKun
 * @version 1.0
 * @date 2018-10-10
 */
public class GpsUtils {

    private static final double PI = Math.PI;
    private static final double X_PI = Math.PI * 3000.0 / 180.0;
    private static final double EARTH_RADIUS = 6378245.0;
    private static final double EE = 0.00669342162296594323;


    /**
     * 计算地球每一个分度的距离
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }


    /**
     * 获取两个经纬度点的坐标实际距离
     *
     * @param lat1 开始纬度坐标点
     * @param lng1 开始经度坐标点
     * @param lat2 结束纬度坐标点
     * @param lng2 结束经度坐标点
     * @return 返回两个坐标点的实际距离 单位 米
     */
    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);

        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2.0), 2.0)
                + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2.0), 2.0)));
        s *= EARTH_RADIUS;
        s = Math.round(s * 10000.0) / 10000.0;
        return s;
    }


    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y
                + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI))
                * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0
                / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0))
                * 2.0 / 3.0;
        return ret;
    }

    private static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y
                + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI))
                * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0
                / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * PI)
                + 300.0 * Math.sin(x / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }

    private static double[] transform(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            return new double[]{lat, lon};
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * PI;
        double magic = Math.sin(radLat);
        magic = 1 - EE * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((EARTH_RADIUS * (1 - EE)) / (magic * sqrtMagic) * PI);
        dLon = (dLon * 180.0) / (EARTH_RADIUS / sqrtMagic * Math.cos(radLat) * PI);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        return new double[]{mgLat, mgLon};
    }

    private static boolean outOfChina(double lat, double lon) {
        if (lon < 72.004 || lon > 137.8347) {
            return true;
        } else if (lat < 0.8293 || lat > 55.8271) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * GPS转高德
     *
     * @param lat
     * @param lon
     * @return
     */
    public static double[] gps84_To_Gcj02(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            return new double[]{lat, lon};
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * PI;
        double magic = Math.sin(radLat);
        magic = 1 - EE * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((EARTH_RADIUS * (1 - EE)) / (magic * sqrtMagic) * PI);
        dLon = (dLon * 180.0) / (EARTH_RADIUS / sqrtMagic * Math.cos(radLat) * PI);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        return new double[]{mgLat, mgLon};
    }

    /**
     * 火星坐标系 (GCJ-02) to 84 *
     * <p>
     * 高德转GPS  转换为7位有效数字
     *
     * @param lon
     * @param lat
     * @return
     */
    public static double[] gcj02_To_Gps84(double lat, double lon) {
        double[] gps = transform(lat, lon);
        double longitude = lon * 2 - gps[1];
        double latitude = lat * 2 - gps[0];
        longitude = retainSeven(longitude);
        latitude = retainSeven(latitude);
        return new double[]{latitude, longitude};
    }

    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 将 GCJ-02 坐标转换成 BD-09 坐标
     * <p>
     * 高德转百度
     *
     * @param lat
     * @param lon
     */
    public static double[] gcj02_To_Bd09(double lat, double lon) {
        double x = lon, y = lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * X_PI);
        double tempLon = z * Math.cos(theta) + 0.0065;
        double tempLat = z * Math.sin(theta) + 0.006;
        double[] gps = {tempLat, tempLon};
        return gps;
    }

    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 * * 将 BD-09 坐标转换成GCJ-02 坐标 *
     * <p>
     * 百度转高德
     *
     * @param lat
     * @param lon
     * @return
     */
    public static double[] bd09_To_Gcj02(double lat, double lon) {
        double x = lon - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);
        double tempLon = z * Math.cos(theta);
        double tempLat = z * Math.sin(theta);
        double[] gps = {tempLat, tempLon};
        return gps;
    }

    /**
     * 将gps84转为bd09
     * <p>
     * <p>
     * GPS转百度
     *
     * @param lat
     * @param lon
     * @return
     */
    public static double[] gps84_To_bd09(double lat, double lon) {
        double[] gcj02 = gps84_To_Gcj02(lat, lon);
        double[] bd09 = gcj02_To_Bd09(gcj02[0], gcj02[1]);
        return bd09;
    }


    /**
     * 百度转GPS
     *
     * @param lat
     * @param lon
     * @return
     */
    public static double[] bd09_To_gps84(double lat, double lon) {
        double[] gcj02 = bd09_To_Gcj02(lat, lon);
        double[] gps84 = gcj02_To_Gps84(gcj02[0], gcj02[1]);
        // 保留小数点后六位
        gps84[0] = retainSeven(gps84[0]);
        gps84[1] = retainSeven(gps84[1]);
        return gps84;
    }

    /**
     * 保留小数点后七位
     *
     * @param num
     * @return
     */
    public static double retainSeven(double num) {
        String result = String.format(Locale.CHINA, "%.7f", num);
        return Double.valueOf(result);
    }

}
