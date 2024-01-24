package com.project.fat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.project.fat.MapsActivity.Companion.monsterIndex
import com.project.fat.RunningTimeActivity.Companion.runningFinalData
import com.project.fat.data.dto.CreateHistoryRequest
import com.project.fat.data.dto.CreateHistoryResponse
import com.project.fat.data.dto.ErrorResponse
import com.project.fat.data.dto.PostUserMonsterRequest
import com.project.fat.dataStore.UserDataStore.dataStore
import com.project.fat.databinding.ActivityResultBinding
import com.project.fat.manager.MonsterManager
import com.project.fat.manager.TokenManager
import com.project.fat.retrofit.client.HistoryRetrofit
import com.project.fat.retrofit.client.UserMonsterRetrofit
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.node.ModelNode
import io.github.sceneview.utils.colorOf
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private var modelUrl : String? = null

    private lateinit var callCreateHistory: Call<CreateHistoryResponse>
    private lateinit var modelNode: ModelNode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        modelUrl = intent.getStringExtra("glbFileLocation")


        modelNode = ModelNode(binding.monster3d.engine).apply {
            position = Position(x = 0.0f, y = 0.0f, z = -4.0f)
            rotation = Rotation(x = 0.0f, y = 2.5f, z = 0.0f)
        }

        lifecycleScope.launchWhenCreated {
            val modelInstance = modelUrl?.let {
                modelNode.loadModelGlb(
                    context = this@ResultActivity,
                    glbFileLocation = it
                )
            }
        }

        binding.goHome.setOnClickListener{
            sendNewHistory()
            addUserMonster()
        }

        val background = colorOf(resources.getColor(R.color.translucent_white))

        binding.monster3d.addChild(modelNode)
        binding.monster3d.scene.skybox?.setColor(background[0], background[1], background[2], background[3])

        binding.distance.text =
            (runningFinalData?.distance + " km") ?: getString(R.string.data_miss)
        binding.time.text = (runningFinalData?.time + " time") ?: getString(R.string.data_miss)
    }

    private fun sendNewHistory(){
        lifecycleScope.launch {
            val time = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

            this@ResultActivity.dataStore.data.collect {
                val accessToken = TokenManager.getAccessToken()
                val createHistoryRequest = CreateHistoryRequest(1, runningFinalData!!.distance.toDouble(), time)

                callCreateHistory = HistoryRetrofit.getApiService()!!.createHistory(accessToken.toString(), createHistoryRequest)
                callCreateHistory.enqueue(object : Callback<CreateHistoryResponse>{
                    override fun onResponse(
                        call: Call<CreateHistoryResponse>,
                        response: Response<CreateHistoryResponse>
                    ) {
                        if(response.isSuccessful){
                            val result = response.body()
                            if(result != null){
                                goHome()
                            }else{
                                Log.d("BackEnd API createHistory result is null", "val result : SocialLoginResponse? = response.body()")
                            }
                        }else{
                            Log.d("BackEnd API SocialLogin response not successful", "Error : ${response.code()}")
                        }
                    }

                    override fun onFailure(
                        call: Call<CreateHistoryResponse>,
                        t: Throwable
                    ) {
                        Log.d("createHistory Failure", "Fail : ${t.printStackTrace()}\n Error message : ${t.message}")
                        Toast.makeText(this@ResultActivity, "전송 오류 : 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                    }

                })
            }
        }
    }

    private fun addUserMonster(){
        lifecycleScope.launch {
            UserMonsterRetrofit.getApiService()!!.postUserMonster(
                TokenManager.getAccessToken()!!,
                PostUserMonsterRequest(MonsterManager.getReadyMonster(MapsActivity.monsterIndex ?: 0).name)
            )
                .enqueue(object : Callback<ErrorResponse>{
                    override fun onResponse(
                        call: Call<ErrorResponse>,
                        response: Response<ErrorResponse>
                    ) {
                        if (response.isSuccessful) {
                            val result = response.body()
                            if (result != null) {
                                Log.d("addUserMonster", "code : ${result.code}\nerrorMessage : ${result.errorMessage}\nhttpStatus : ${result.httpStatus}")
                            } else {
                                Log.d("addUserMonster result is null", "val result = response.body()")
                            }
                        } else {
                            Log.d("addUserMonster response is not success", "Error : ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<ErrorResponse>, t: Throwable) {
                        Log.d("addUserMonster Failure", "Fail : ${t.printStackTrace()}\n Error message : ${t.message}")
                        Toast.makeText(this@ResultActivity, "전송 오류 : 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                    }

                })
        }
    }

    private fun goHome(){
        MonsterManager.deleteMonster(monsterIndex?:0)
        startActivity(Intent(this@ResultActivity, BottomNavigationActivity::class.java))
        finish()
    }
}