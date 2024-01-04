package com.comst.arlocationtest.AR

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.util.Log
import com.comst.arlocationtest.AR.rendering.LocationNode
import com.comst.arlocationtest.AR.sensor.DeviceOrientation
import com.comst.arlocationtest.AR.sensor.LocationManager
import com.comst.arlocationtest.AR.utils.LocationUtils
import com.google.ar.core.Frame
import com.google.ar.core.Pose
import com.google.ar.core.Session
import com.google.ar.sceneform.ArSceneView
import kotlin.math.cos
import kotlin.math.sin

class LocationScene(mContext: Context, mActivity: Activity, mArSceneView: ArSceneView) {
    private val TAG = "LocationScene"
    var mArSceneView: ArSceneView
    var locationManager: LocationManager
    var deviceOrientation: DeviceOrientation
    var mContext: Context
    var mActivity: Activity
    var mLocationMarkers = ArrayList<LocationMarker>()

    // Anchors are currently re-drawn on an interval. There are likely better
    // ways of doing this, however it's sufficient for now. Anchor刷新间隔
    private var anchorRefreshInterval = 1000 * 5 // 5 seconds
    /**
     * The distance cap for distant markers.
     * ARCore doesn't like markers that are 2000km away :/
     *
     * @return
     */
    /**
     * The distance cap for distant markers.
     * ARCore doesn't like markers that are 2000km away :/
     * Default 20
     */
    // Limit of where to draw markers within AR scene.
    // They will auto scale, but this helps prevents rendering issues 在AR Scene中绘制标识的距离范围
    var distanceLimit = 20
    private var offsetOverlapping = false

    // Bearing adjustment. Can be set to calibrate with true north
    private var bearingAdjustment = 0
    private var anchorsNeedRefresh = true
    private var minimalRefreshing = false
    private var refreshAnchorsAsLocationChanges = false
    private val mHandler = Handler()
    var anchorRefreshTask: Runnable = object : Runnable {
        override fun run() {
            anchorsNeedRefresh = true
            mHandler.postDelayed(this, anchorRefreshInterval.toLong())
        }
    }
    var isDebugEnabled = false
    private val mSession: Session?

    init {
        Log.i(TAG, "Location Scene initiated.")
        this.mContext = mContext
        this.mActivity = mActivity
        mSession = mArSceneView.session
        this.mArSceneView = mArSceneView
        startCalculationTask()
        locationManager = LocationManager(mContext)
        deviceOrientation = DeviceOrientation(this)
        deviceOrientation.resume()
    }

    fun isDebugEnabled(): Boolean {
        return isDebugEnabled
    }

    fun setDebugEnabled(debugEnabled: Boolean) {
        isDebugEnabled = debugEnabled
    }

    fun minimalRefreshing(): Boolean {
        return minimalRefreshing
    }

    fun setMinimalRefreshing(minimalRefreshing: Boolean) {
        this.minimalRefreshing = minimalRefreshing
    }

    fun refreshAnchorsAsLocationChanges(): Boolean {
        return refreshAnchorsAsLocationChanges
    }

    fun setRefreshAnchorsAsLocationChanges(refreshAnchorsAsLocationChanges: Boolean) {
        if (refreshAnchorsAsLocationChanges) {
            stopCalculationTask()
        } else {
            startCalculationTask()
        }
        refreshAnchors()
        this.refreshAnchorsAsLocationChanges = refreshAnchorsAsLocationChanges
    }

    fun getAnchorRefreshInterval(): Int {
        return anchorRefreshInterval
    }

    /**
     * Set the interval at which anchors should be automatically re-calculated.
     *
     * @param anchorRefreshInterval
     */
    fun setAnchorRefreshInterval(anchorRefreshInterval: Int) {
        this.anchorRefreshInterval = anchorRefreshInterval
        stopCalculationTask()
        startCalculationTask()
    }

    fun clearMarkers() {
        for (lm in mLocationMarkers) {
            if (lm.anchorNode != null) {
                lm.anchorNode!!.anchor?.detach()
                lm.anchorNode!!.isEnabled = false
                lm.anchorNode = null
            }
        }
        mLocationMarkers = ArrayList()
    }

    fun getDistanceLimit(): Int {
        return distanceLimit
    }

    fun setDistanceLimit(distanceLimit: Int) {
        this.distanceLimit = distanceLimit
    }


    fun shouldOffsetOverlapping(): Boolean {
        return offsetOverlapping
    }

    /**
     * Attempts to raise markers vertically when they overlap.
     * Needs work!
     *
     * @param offsetOverlapping
     */
    fun setOffsetOverlapping(offsetOverlapping: Boolean) {
        this.offsetOverlapping = offsetOverlapping
    }

    fun processFrame(frame: Frame) {
        refreshAnchorsIfRequired(frame)
    }

    /**
     * Force anchors to be re-calculated
     */
    fun refreshAnchors() {
        anchorsNeedRefresh = true
    }

    private fun refreshAnchorsIfRequired(frame: Frame) {
        if (anchorsNeedRefresh) {
            Log.i(TAG, "Refreshing anchors...")
            anchorsNeedRefresh = false
            if (locationManager.currentLocation == null) {
                Log.i(TAG, "Location not yet established.")
                return
            }
            for (i in mLocationMarkers.indices) {
                try {
                    val markerDistance = Math.round(
                        LocationUtils.distance(
                            mLocationMarkers[i].latitude,
                            locationManager.currentLocation!!.latitude,
                            mLocationMarkers[i].longitude,
                            locationManager.currentLocation!!.longitude
                        )
                    ).toInt()
                    if (markerDistance > mLocationMarkers[i].onlyRenderWhenWithin) {
                        // Don't render if this has been set and we are too far away.
                        Log.i(
                            TAG,
                            "Not rendering. Marker distance: " + markerDistance + " Max render distance: " + mLocationMarkers[i].onlyRenderWhenWithin
                        )
                        continue
                    }
                    var markerBearing: Float =
                        deviceOrientation.currentDegree + LocationUtils.bearing(
                            locationManager.currentLocation!!.latitude,
                            locationManager.currentLocation!!.longitude,
                            mLocationMarkers[i].latitude,
                            mLocationMarkers[i].longitude
                        ) as Float

                    // Bearing adjustment can be set if you are trying to
                    // correct the heading of north - setBearingAdjustment(10)
                    markerBearing = markerBearing + bearingAdjustment
                    markerBearing = markerBearing % 360
                    var rotation = Math.floor(markerBearing.toDouble())

                    // When pointing device upwards (camera towards sky)
                    // the compass bearing can flip.
                    // In experiments this seems to happen at pitch~=-25
                    if (deviceOrientation.pitch > -25) rotation = rotation * Math.PI / 180
                    var renderDistance = markerDistance

                    // Limit the distance of the Anchor within the scene.
                    // Prevents rendering issues.
                    if (renderDistance > distanceLimit) renderDistance = distanceLimit

                    // Adjustment to add markers on horizon, instead of just directly in front of camera
                    var heightAdjustment = 0.0
                    // Math.round(renderDistance * (Math.tan(Math.toRadians(deviceOrientation.pitch)))) - 1.5F;

                    // Raise distant markers for better illusion of distance
                    // Hacky - but it works as a temporary measure
                    val cappedRealDistance = if (markerDistance > 500) 500 else markerDistance
                    if (renderDistance != markerDistance) heightAdjustment += (0.005f * (cappedRealDistance - renderDistance)).toDouble()
                    val x = 0f
                    val z = -renderDistance.toFloat()
                    val zRotated = (z * cos(rotation) - x * sin(rotation)).toFloat()
                    val xRotated = -(z * sin(rotation) + x * cos(rotation)).toFloat()

                    // Current camera height
                    val y = frame.camera.displayOrientedPose.ty()
                    if (mLocationMarkers[i].anchorNode != null &&
                        mLocationMarkers[i].anchorNode!!.anchor != null
                    ) {
                        mLocationMarkers[i].anchorNode!!.anchor!!.detach()
                        mLocationMarkers[i].anchorNode!!.anchor = null
                        mLocationMarkers[i].anchorNode!!.isEnabled = false
                        mLocationMarkers[i].anchorNode = null
                    }

                    // Don't immediately assign newly created anchor in-case of exceptions
                    val newAnchor = mSession!!.createAnchor(
                        frame.camera.pose
                            .compose(
                                Pose.makeTranslation(
                                    xRotated,
                                    y + heightAdjustment.toFloat(),
                                    zRotated
                                )
                            )
                    )
                    mLocationMarkers[i].anchorNode = LocationNode(
                        newAnchor,
                        mLocationMarkers[i], this
                    )
                    mLocationMarkers[i].anchorNode!!.setParent(mArSceneView.scene)
                    mLocationMarkers[i].anchorNode!!.addChild(mLocationMarkers[i].node)
                    if (mLocationMarkers[i].renderEvent != null) {
                        mLocationMarkers[i].anchorNode!!.setRenderEvent(mLocationMarkers[i].renderEvent)
                    }
                    mLocationMarkers[i].anchorNode!!.setScaleModifier(mLocationMarkers[i].scaleModifier)
                    mLocationMarkers[i].anchorNode!!.setScalingMode(mLocationMarkers[i].scalingMode)
                    mLocationMarkers[i].anchorNode!!.setGradualScalingMaxScale(mLocationMarkers[i].gradualScalingMaxScale)
                    mLocationMarkers[i].anchorNode!!.setGradualScalingMinScale(mLocationMarkers[i].gradualScalingMinScale)
                    mLocationMarkers[i].anchorNode!!.setHeight(mLocationMarkers[i].height)
                    if (minimalRefreshing) mLocationMarkers[i].anchorNode!!.scaleAndRotate()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            System.gc()
        }
    }

    /**
     * Adjustment for compass bearing.
     *
     * @return
     */
    fun getBearingAdjustment(): Int {
        return bearingAdjustment
    }

    /**
     * Adjustment for compass bearing.
     * You may use this for a custom method of improving precision.
     *
     * @param i
     */
    fun setBearingAdjustment(i: Int) {
        bearingAdjustment = i
        anchorsNeedRefresh = true
    }

    /**
     * Resume sensor services. Important!
     */
    fun resume() {
        deviceOrientation.resume()
    }

    /**
     * Pause sensor services. Important!
     */
    fun pause() {
        deviceOrientation.pause()
    }

    fun startCalculationTask() {
        anchorRefreshTask.run()
    }

    fun stopCalculationTask() {
        mHandler.removeCallbacks(anchorRefreshTask)
    }
}