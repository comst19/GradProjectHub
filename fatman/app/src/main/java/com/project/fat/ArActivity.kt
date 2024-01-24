package com.project.fat

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.ar.sceneform.rendering.ViewRenderable
import com.project.fat.MapsActivity.Companion.monsterIndex
import com.project.fat.databinding.ActivityArBinding
import com.project.fat.databinding.MonsterInfoBinding
import com.project.fat.location.LocationProvider
import com.project.fat.manager.MonsterManager
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Position
import io.github.sceneview.math.Scale
import io.github.sceneview.node.ViewNode
import java.lang.Exception
import kotlin.system.exitProcess


class ArActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityArBinding

    private lateinit var mMap: GoogleMap

    private lateinit var modelNode : ArModelNode
    private lateinit var url : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val monster = MonsterManager.getReadyMonster(monsterIndex ?: 0)

        modelNode = ArModelNode(binding.sceneView.engine).apply {
            placementMode = PlacementMode.INSTANT
            screenPosition = Position(0.0f, 0.0f, - 7.0f)
            followHitPosition = false
            instantAnchor = true
            scale = Scale(0.6f, 0.6f, 0.6f)

            lifecycleScope.launchWhenStarted {
                url = monster.graphic
                val modelInstance = modelNode.loadModelGlb(
                    context = this@ArActivity,
                    glbFileLocation = url
                ) {
                    binding.sceneView.planeRenderer.isVisible = true
                }
            }
        }

        binding.sceneView.addChild(modelNode)

        val arTextViewBinding = MonsterInfoBinding.inflate(layoutInflater)
        arTextViewBinding.info.text = monster.name

        ViewRenderable.builder()
            .setView(this, arTextViewBinding.root)
            .setVerticalAlignment(ViewRenderable.VerticalAlignment.TOP)
            .build()
            .thenAccept { renderable: ViewRenderable ->
                val viewNode = ViewNode(modelNode.engine)
                viewNode.parent = modelNode
                viewNode.setRenderable(renderable)
                viewNode.position = Position(x = 0.0f, y = 3.8f, z = 0.0f)
            }

        modelNode.onTap = { _, _ ->
            val intent = Intent(this@ArActivity, ResultActivity::class.java)
            LocationProvider.stopLocationUpdates()
            intent.putExtra("glbFileLocation", url)
            startActivity(intent)
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_ar) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onDestroy() {
        LocationProvider.stopLocationUpdates()
        super.onDestroy()
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        lifecycleScope.launchWhenCreated {
            LocationProvider.setLocationRequest(Priority.PRIORITY_BALANCED_POWER_ACCURACY, 1000)
            LocationProvider.setLocationCallback(object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult.lastLocation?.let { location ->
                        Log.d("onLocationResult in ArActivity", "${location.latitude}, ${location.longitude}")
                        setLocation(location)
                    }
                }
            })

            LocationProvider.requestLocationUpdates(this@ArActivity)
        }

        if(mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_simple_style))){
            Log.d("setMapStyle", "success")
        }else{
            Log.d("setMapStyle", "false")
            try{
                startActivity(Intent.makeRestartActivityTask(packageManager.getLaunchIntentForPackage(packageName)?.component))
                exitProcess(0)
            }catch (e : Exception){
                Log.d("setMapStyle", "restart false")
                Toast.makeText(this@ArActivity, "오류 : 재시작을 실패했습니다. 앱을 나갔다가 다시 실행해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
        mMap.isMyLocationEnabled = true
        mMap.isBuildingsEnabled = false

    }

    private fun setLocation(location : Location){
        Log.d("map", "setLocation")
        val myLocation = LatLng(location.latitude, location.longitude)

        val camerOption = CameraPosition.builder()
            .target(myLocation)
            .zoom(17.5f)
            .build()
        val camera = CameraUpdateFactory.newCameraPosition(camerOption)

        mMap.moveCamera(camera)
    }

    private fun setCameraPosition(location : Location){
    }
}