package com.project.fat.location

import android.util.Log
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class Distance {

    companion object{
        val EARTH_RADIUS = 6371.0
        val NOT_SIGNAL = "Not Signal"

        fun getDistance(fromLat : Double, fromLng : Double, toLat : Double?, toLng : Double?) : String {
            Log.d("Location Distance getDistance", "fromLat : $fromLat, fromLng : $fromLng toLat : $toLat, toLng : $toLng")
            if(toLat != null && toLng != null){
                val dLat = Math.toRadians(toLat - fromLat)
                val dLon = Math.toRadians(toLng - fromLng)

                val a = sin(dLat / 2) * sin(dLat / 2) +
                        cos(Math.toRadians(fromLat)) * cos(Math.toRadians(toLat)) *
                        sin(dLon / 2) * sin(dLon / 2)
                val c = 2 * atan2(sqrt(a), sqrt(1 - a))

                return String.format("%.3f", EARTH_RADIUS * c)
            }

            return NOT_SIGNAL
        }
    }
}