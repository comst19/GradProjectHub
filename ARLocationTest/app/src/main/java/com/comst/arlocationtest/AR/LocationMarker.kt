package com.comst.arlocationtest.AR

import com.comst.arlocationtest.AR.rendering.LocationNode
import com.comst.arlocationtest.AR.rendering.LocationNodeRender
import com.google.ar.sceneform.Node

class LocationMarker(
    var longitude: Double,
    var latitude: Double,
    var node: Node
) {
    var anchorNode: LocationNode? = null
    var renderEvent: LocationNodeRender? = null
    var scaleModifier = 1f
    var height = 0f
    var onlyRenderWhenWithin = Int.MAX_VALUE
    var scalingMode = ScalingMode.FIXED_SIZE_ON_SCREEN
    var gradualScalingMinScale = 0.8f
    var gradualScalingMaxScale = 1.4f

    enum class ScalingMode {
        FIXED_SIZE_ON_SCREEN,
        NO_SCALING,
        GRADUAL_TO_MAX_RENDER_DISTANCE
    }
}