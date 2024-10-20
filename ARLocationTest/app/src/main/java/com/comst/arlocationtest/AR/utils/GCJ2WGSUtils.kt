package com.comst.arlocationtest.AR.utils

object GCJ2WGSUtils {
    //输入GCJ经纬度 转WGS纬度
    fun WGSLat(lat: Double, lon: Double): Double {
        val PI = 3.14159265358979324 //圆周率
        val a = 6378245.0 //克拉索夫斯基椭球参数长半轴a
        val ee = 0.00669342162296594323 //克拉索夫斯基椭球参数第一偏心率平方
        var dLat = transformLat(lon - 105.0, lat - 35.0)
        val radLat = lat / 180.0 * PI
        var magic = Math.sin(radLat)
        magic = 1 - ee * magic * magic
        val sqrtMagic = Math.sqrt(magic)
        dLat = dLat * 180.0 / (a * (1 - ee) / (magic * sqrtMagic) * PI)
        return lat - dLat
    }

    //输入GCJ经纬度 转WGS经度
    fun WGSLon(lat: Double, lon: Double): Double {
        val PI = 3.14159265358979324 //圆周率
        val a = 6378245.0 //克拉索夫斯基椭球参数长半轴a
        val ee = 0.00669342162296594323 //克拉索夫斯基椭球参数第一偏心率平方
        var dLon = transformLon(lon - 105.0, lat - 35.0)
        val radLat = lat / 180.0 * PI
        var magic = Math.sin(radLat)
        magic = 1 - ee * magic * magic
        val sqrtMagic = Math.sqrt(magic)
        dLon = dLon * 180.0 / (a / sqrtMagic * Math.cos(radLat) * PI)
        return lon - dLon
    }

    //转换经度所需
    fun transformLon(x: Double, y: Double): Double {
        val PI = 3.14159265358979324 //圆周率
        var ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x))
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0
        ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0
        ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0 * PI)) * 2.0 / 3.0
        return ret
    }

    //转换纬度所需
    fun transformLat(x: Double, y: Double): Double {
        val PI = 3.14159265358979324 //圆周率
        var ret =
            -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x))
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0
        ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0
        ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0
        return ret
    }
}