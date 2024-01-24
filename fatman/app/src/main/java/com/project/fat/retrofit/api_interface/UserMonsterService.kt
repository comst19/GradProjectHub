package com.project.fat.retrofit.api_interface

import com.project.fat.data.dto.ErrorResponse
import com.project.fat.data.dto.PostUserMonsterRequest
import com.project.fat.data.dto.UserMonster
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserMonsterService {
    @GET("user_monster")
    fun getUserMonster(
        @Header("Access-Token") accessToken : String
    ) : Call<ArrayList<UserMonster>>

    @POST("user_monster")
    fun postUserMonster(
        @Header("Access-Token") accessToken : String,
        @Body postUserMonsterRequest: PostUserMonsterRequest
    ) : Call<ErrorResponse>
}