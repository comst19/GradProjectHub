package com.comst.arlocationtest.AR.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.comst.arlocationtest.AR.LocationScene

class DeviceOrientation(locationScene: LocationScene) : SensorEventListener {
    private val mSensorManager: SensorManager

    // Gravity rotational data 重力数据
    private lateinit var gravity: FloatArray

    // Magnetic rotational data 磁场数据
    private lateinit var magnetic: FloatArray
    private var accels: FloatArray? = FloatArray(3)
    private var mags: FloatArray? = FloatArray(3)
    private val values = FloatArray(3)
    private var azimuth = 0f
    var pitch = 0f
    var roll = 0f
    private val locationScene: LocationScene

    // North 北方向
    var currentDegree = 0f

    init {
        this.locationScene = locationScene
        mSensorManager = locationScene.mContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onSensorChanged(event: SensorEvent) {

        // Get the device heading 获取设备朝向
        val degree = -Math.round(event.values[0]).toFloat()

        // Temporary fix until we can work out what's causing the anomalies
        if (degree.toDouble() != 1.0 && degree != 0f && degree.toDouble() != 2.0 && degree.toDouble() != -1.0) currentDegree =
            degree
        when (event.sensor.type) {
            Sensor.TYPE_MAGNETIC_FIELD -> mags = lowPass(event.values.clone(), mags)
            Sensor.TYPE_ACCELEROMETER -> accels = lowPass(event.values.clone(), accels)
        }
        if (mags != null && accels != null) {
            gravity = FloatArray(9)
            magnetic = FloatArray(9)
            SensorManager.getRotationMatrix(gravity, magnetic, accels, mags)
            val outGravity = FloatArray(9)
            SensorManager.remapCoordinateSystem(
                gravity,
                SensorManager.AXIS_X,
                SensorManager.AXIS_Z,
                outGravity
            )
            SensorManager.getOrientation(outGravity, values)
            azimuth = values[0] * 57.2957795f
            pitch = values[1] * 57.2957795f
            roll = values[2] * 57.2957795f
            mags = null
            accels = null
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    fun resume() {
        mSensorManager.registerListener(
            this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_GAME
        )
        mSensorManager.registerListener(
            this,
            mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL
        )
        mSensorManager.registerListener(
            this,
            mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    fun pause() {
        mSensorManager.unregisterListener(this)
    }

     fun lowPass(input: FloatArray, output: FloatArray?): FloatArray {
        if (output == null) return input
        for (i in input.indices) {
            output[i] = output[i] + ALPHA * (input[i] - output[i])
        }
        return output
    }

    companion object {
        private const val ALPHA = 0.25f
    }
}
