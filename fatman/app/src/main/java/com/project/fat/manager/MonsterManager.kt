package com.project.fat.manager

import android.util.Log
import com.project.fat.data.marker.Marker
import com.project.fat.data.monster.Monster
import com.project.fat.retrofit.client.MonsterRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MonsterManager {

    private const val MAXINDEXREADYMONSTERLIST = Marker.MAXNUM_MARKER
    private var readyMonsterList : List<Monster> = listOf()
    private val monsterGraphList : List<String> = listOf(
        "model/black.glb",
        "model/blue.glb",
        "model/fat.glb",
        "model/green.glb",
        "model/mint.glb",
        "model/per_green.glb",
        "model/purple.glb",
        "model/yellow.glb")

    fun deleteMonster(index : Int){
        readyMonsterList = readyMonsterList.drop(index)
    }

    fun getReadyMonsterList() = readyMonsterList

    fun getReadyMonster(index : Int) = readyMonsterList[index]

    fun setRandomMonster() {
        if(readyMonsterList.lastIndex == MAXINDEXREADYMONSTERLIST)
            return
        for(i in 0..MAXINDEXREADYMONSTERLIST){
            MonsterRetrofit.getApiService()!!.getRandomMonster(TokenManager.getAccessToken()!!)
                .enqueue(object : Callback<com.project.fat.data.dto.Monster>{
                    override fun onResponse(
                        call: Call<com.project.fat.data.dto.Monster>,
                        response: Response<com.project.fat.data.dto.Monster>
                    ) {
                        if(response.isSuccessful){
                            val result = response.body()
                            if(result != null){
                                val monster = Monster(result.id, result.name, result.imageUrl, monsterGraphList[((result.id)% monsterGraphList.size).toInt()])
                                Log.d("setRandomMonster", "index = $i\nrandom monster = ${monster.name}")
                                readyMonsterList = readyMonsterList.plus(monster)
                            }else{
                                Log.d("setRandomMonster", "result = null")
                            }
                        }else{
                            Log.d("setRandomMonster", "response is failed")
                        }
                    }

                    override fun onFailure(
                        call: Call<com.project.fat.data.dto.Monster>,
                        t: Throwable
                    ) {
                        Log.d("setRandomMonster", "Error : ${t.message}")
                    }

                })
        }
    }
}