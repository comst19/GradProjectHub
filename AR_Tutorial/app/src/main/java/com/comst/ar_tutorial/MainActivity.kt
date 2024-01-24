package com.comst.ar_tutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isGone
import com.comst.ar_tutorial.databinding.ActivityMainBinding
import io.github.sceneview.ar.node.ArModelNode

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var modelNode : ArModelNode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.place.setOnClickListener {
            placeModel()
        }

        modelNode = ArModelNode().apply {
            loadModelGlbAsync(
                glbFileLocation = "models/phoenix_bird.glb"
            ){
                binding.sceneView.planeRenderer.isVisible = true
            }

            onAnchorChanged = {
                binding.place.isGone
            }

        }

        binding.sceneView.addChild(modelNode)
    }

    private fun placeModel(){
        modelNode?.anchor()
        binding.sceneView.planeRenderer.isVisible = false
    }

}