package com.project.fat.retrofit.api_interface

import com.project.fat.data.dto.Monster
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface MonsterRetrofitInterface {
    @GET
    fun getUserMonster(
        @Header("Access_token") accessToken : String
    ) : MutableList<Call<Monster>>

    @POST
    fun addUserMonster(
        @Header("Access_token") accessToken : String,
        @Path("monster_id") monster_id : Long
    ) : Call<Monster>
}