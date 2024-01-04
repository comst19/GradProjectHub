package com.comst.arlocationtest.AR.utils

object LocationUtils {
    // 输入经纬度计算方向角
    fun bearing(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val latitude1 = Math.toRadians(lat1)
        val latitude2 = Math.toRadians(lat2)
        val longDiff = Math.toRadians(lon2 - lon1)
        val y = Math.sin(longDiff) * Math.cos(latitude2)
        val x =
            Math.cos(latitude1) * Math.sin(latitude2) - Math.sin(latitude1) * Math.cos(latitude2) * Math.cos(
                longDiff
            )
        return (Math.toDegrees(Math.atan2(y, x)) + 360) % 360
    }

    // 输入经纬度计算距离
    fun distance(
        lat1: Double, lat2: Double, lon1: Double,
        lon2: Double
    ): Double {
        val R = 6371 // 地球半径
        val latDistance = Math.toRadians(lat2 - lat1)
        val lonDistance = Math.toRadians(lon2 - lon1)
        val a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + (Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2)))
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        var distance = R * c * 1000 // 单位转换成米
        distance = Math.pow(distance, 2.0)
        return Math.sqrt(distance)
    }
}