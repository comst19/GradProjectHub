package com.project.fat.data.permission

import android.Manifest

object Permission{
    val PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA)
    const val PERMISSION_FLAG = 99
}