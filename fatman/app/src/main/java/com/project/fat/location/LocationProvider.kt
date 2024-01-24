package com.project.fat.location

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.project.fat.data.permission.Permission

object LocationProvider {
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest : LocationRequest

    fun setFusedLocationProviderClient(context: Context){
        Log.d("LocationProvider", "setFusedLocationProviderClient")
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    }

    fun setLocationCallback(locationCallback: LocationCallback){
        Log.d("LocationProvider", "setLocationCallback")
        this.locationCallback = locationCallback

    }

    fun setLocationRequest(priority : Int, interMillis : Long) {
        locationRequest = LocationRequest.Builder(priority, interMillis).apply {
            setMaxUpdateDelayMillis(2000)
            setMinUpdateIntervalMillis(100)
        }.build()
    }

    fun stopLocationUpdates() {
        fusedLocationProviderClient?.removeLocationUpdates(locationCallback)
        Log.d("LocationProvider", "stopLocationUpdates")
    }

    fun requestLocationUpdates(context: Context) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(context as Activity, Permission.PERMISSIONS, Permission.PERMISSION_FLAG)
            Log.d("LocationProvider", "need permissions")
        }else{
            fusedLocationProviderClient?.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
            Log.d("LocationProvider", "requestLocationUpdates end")
        }

    }
}