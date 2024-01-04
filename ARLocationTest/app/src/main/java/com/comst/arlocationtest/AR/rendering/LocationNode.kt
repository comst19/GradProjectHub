package com.comst.arlocationtest.AR.rendering

import android.annotation.SuppressLint
import com.comst.arlocationtest.AR.LocationMarker
import com.comst.arlocationtest.AR.LocationScene
import com.comst.arlocationtest.AR.utils.LocationUtils
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import kotlin.math.ceil

class LocationNode(
    newAnchor: Anchor,
    locationMarker: LocationMarker,
    locationScene: LocationScene
) : AnchorNode() {
    private val TAG = "LocationNode"

    private var locationMarker: LocationMarker? = null
    private var renderEvent: LocationNodeRender? = null
    private var distanceInGPS = 0
    private var distanceInAR = 0.0
    private var scaleModifier = 1f
    private var height = 0f
    private var gradualScalingMinScale = 0.8f
    private var gradualScalingMaxScale = 1.4f

    private var scalingMode: LocationMarker.ScalingMode =
        LocationMarker.ScalingMode.FIXED_SIZE_ON_SCREEN
    private var locationScene: LocationScene? = null

    class LocationNode(anchor: Anchor, val locationMarker: LocationMarker, val locationScene: LocationScene) : Node() {}

    fun getHeight(): Float {
        return height
    }

    fun setHeight(height: Float) {
        this.height = height
    }

    fun getScaleModifier(): Float {
        return scaleModifier
    }

    fun setScaleModifier(scaleModifier: Float) {
        this.scaleModifier = scaleModifier
    }

    fun getRenderEvent(): LocationNodeRender? {
        return renderEvent
    }

    fun setRenderEvent(renderEvent: LocationNodeRender?) {
        this.renderEvent = renderEvent
    }

    fun getDistanceInGPS(): Int {
        return distanceInGPS
    }

    fun getDistanceInAR(): Double {
        return distanceInAR
    }

    fun setDistanceInGPS(distanceInGPS: Int) {
        this.distanceInGPS = distanceInGPS
    }

    fun setDistanceInAR(distanceInAR: Double) {
        this.distanceInAR = distanceInAR
    }

    fun getScalingMode(): LocationMarker.ScalingMode? {
        return scalingMode
    }

    fun setScalingMode(scalingMode: LocationMarker.ScalingMode) {
        this.scalingMode = scalingMode
    }

    override fun onUpdate(frameTime: FrameTime?) {
        // Typically, getScene() will never return null because onUpdate() is only called when the node
        // is in the scene.
        // However, if onUpdate is called explicitly or if the node is removed from the scene on a
        // different thread during onUpdate, then getScene may be null.
        for (n in children) {
            if (scene == null) {
                return
            }
            val cameraPosition = scene!!.camera.worldPosition
            val nodePosition = n.worldPosition

            // Compute the difference vector between the camera and anchor 计算相机与Anchor之间各坐标轴距离
            val dx = cameraPosition.x - nodePosition.x
            val dy = cameraPosition.y - nodePosition.y
            val dz = cameraPosition.z - nodePosition.z

            // Compute the straight-line distanceInAR 计算在AR中的直线距离
            setDistanceInAR(Math.sqrt((dx * dx + dy * dy + dz * dz).toDouble()))
            if (locationScene!!.shouldOffsetOverlapping()) {
                if (locationScene!!.mArSceneView.getScene().overlapTestAll(n).size > 0) {
                    setHeight(getHeight() + 1.2f)
                }
            }
        }
        if (!locationScene!!.minimalRefreshing()) scaleAndRotate()
        if (renderEvent != null) {
            if (this.isTracking && this.isActive && this.isEnabled) renderEvent!!.render(this)
        }
    }

    fun scaleAndRotate() {
        for (n in children) {
            val markerDistance = ceil(
                LocationUtils.distance(
                    locationMarker!!.latitude,
                    locationScene!!.locationManager.currentLocation!!.latitude,
                    locationMarker!!.longitude,
                    locationScene!!.locationManager.currentLocation!!.longitude
                )
            ).toInt()
            setDistanceInGPS(markerDistance)

            // Limit the distanceInGPS of the Anchor within the scene. 限制显示的距离
            var renderDistance = markerDistance
            if (renderDistance > locationScene!!.getDistanceLimit()) renderDistance =
                locationScene!!.getDistanceLimit()
            var scale = 1f
            when (scalingMode) {
                LocationMarker.ScalingMode.FIXED_SIZE_ON_SCREEN -> {
                    scale = 0.5f * renderDistance.toFloat()
                    // Distant markers a little smaller
                    if (markerDistance > 3000) scale *= 0.75f
                }

                LocationMarker.ScalingMode.GRADUAL_TO_MAX_RENDER_DISTANCE -> {
                    val scaleDifference = gradualScalingMaxScale - gradualScalingMinScale
                    scale =
                        gradualScalingMinScale + (locationScene!!.getDistanceLimit() - markerDistance) * (scaleDifference / locationScene!!.getDistanceLimit()) * renderDistance
                }

                LocationMarker.ScalingMode.NO_SCALING -> {}
            }
            scale *= scaleModifier
            val cameraPosition = scene!!.camera.worldPosition
            val nodePosition = n.worldPosition
            // 设置位置
            n.worldPosition = Vector3(n.worldPosition.x, getHeight(), n.worldPosition.z)
            val direction = Vector3.subtract(cameraPosition, nodePosition)
            val lookRotation = Quaternion.lookRotation(direction, Vector3.up())
            // 设置朝向
            n.worldRotation = lookRotation
            // 设置大小
            n.worldScale = Vector3(scale, scale, scale)
        }
    }

    fun getGradualScalingMinScale(): Float {
        return gradualScalingMinScale
    }

    fun setGradualScalingMinScale(gradualScalingMinScale: Float) {
        this.gradualScalingMinScale = gradualScalingMinScale
    }

    fun getGradualScalingMaxScale(): Float {
        return gradualScalingMaxScale
    }

    fun setGradualScalingMaxScale(gradualScalingMaxScale: Float) {
        this.gradualScalingMaxScale = gradualScalingMaxScale
    }
}