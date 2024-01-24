package com.project.fat.retrofit.api_interface

import com.project.fat.data.dto.Monster
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface MonsterService {
    @GET("monster")
    fun getMonster(
        @Header("Access-Token") accessToken : String
    ) : Call<ArrayList<Monster>>

    @GET("random-monster")
    fun getRandomMonster(
        @Header("Access-Token") accessToken: String,
    ) : Call<Monster>
}