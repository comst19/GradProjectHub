package com.hackathon.zero.di.api

import com.hackathon.zero.core.dto.ResponseBody
import com.hackathon.zero.data.HomeData
import com.hackathon.zero.data.HomeUserInfo
import com.hackathon.zero.data.PostUserInfo
import com.hackathon.zero.data.UserInfoInput
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ZeroUserApi {
    @POST("/api/v1/user")
    suspend fun postUserInfo(
        @Body userInfo: UserInfoInput
    ): ResponseBody<PostUserInfo?>

    @GET("/api/v1/user/{userId}")
    suspend fun getUserInfo(
        @Path("userId") userId: Int
    ): ResponseBody<HomeUserInfo?>

    @GET("/api/v1/user/{userId}/note")
    suspend fun getHomeInfo(
        @Path("userId") userId: Int
    ): ResponseBody<HomeData?>
}