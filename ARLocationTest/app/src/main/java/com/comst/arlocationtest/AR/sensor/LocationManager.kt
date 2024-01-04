package com.comst.arlocationtest.AR.sensor

import android.content.Context
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationListener
import com.amap.api.location.DPoint
import com.comst.arlocationtest.AR.utils.GCJ2WGSUtils

class LocationManager(var mContext: Context) {
    var mLocationClient: AMapLocationClient? = null
    var mLocationListener: AMapLocationListener
    var currentLocation: DPoint? = null
    var currentAmapLocation: AMapLocation? = null

    init {
        //声明AMapLocationClient类对象
        //初始化定位
        mLocationClient = AMapLocationClient(mContext)
        //设置定位回调操作
        mLocationListener = AMapLocationListener { amapLocation ->
            if (amapLocation != null) {
                if (amapLocation.errorCode == 0) {
                    //解析定位结果
                    currentAmapLocation = amapLocation
                    val gcjLat = currentAmapLocation!!.latitude
                    val gcjLon = currentAmapLocation!!.longitude
                    val wgsLat: Double = GCJ2WGSUtils.WGSLat(gcjLat, gcjLon)
                    val wgsLon: Double = GCJ2WGSUtils.WGSLon(gcjLat, gcjLon)
                    currentLocation = DPoint(wgsLat, wgsLon)
                } else {
                    //错误信息
                }
            } else {
                //返回为空
            }
        }
        //设置定位回调监听
        mLocationClient!!.setLocationListener(mLocationListener)
        //启动定位
        mLocationClient!!.startLocation()
        //异步获取定位结果
    }
}