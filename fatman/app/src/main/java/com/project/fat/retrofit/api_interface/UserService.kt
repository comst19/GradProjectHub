package com.project.fat.retrofit.api_interface

import com.project.fat.data.dto.AuthorizeResponse
import com.project.fat.data.dto.SignInRequest
import com.project.fat.data.dto.SignInResponse
import com.project.fat.data.dto.SignUpRequest
import com.project.fat.data.dto.SocialLoginRequest
import com.project.fat.data.dto.SocialLoginResponse
import com.project.fat.data.dto.getUserResponse
import com.project.fat.data.dto.updateUserDetailRequest
import com.project.fat.data.dto.updateUserDetailResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserService {
    @POST("users/signup")
    fun signUp(
        @Body signUp: SignUpRequest
    ) : Call<String>
    @POST("users/signin")
    fun signIn(
        @Body signIn: SignInRequest
    ) : Call<SignInResponse>

    @POST("users")
    fun socialLogin(
        @Body token : SocialLoginRequest
    ) : Call<SocialLoginResponse>
    @PUT("users/details")
    fun updateUserDetail2(
        @Header("Access-Token") accessToken : String,
        @Body update: updateUserDetailRequest
    ) : Call<updateUserDetailResponse>
    @GET("users/refresh")
    fun authorize(
        @Header("Refresh-Token") refreshToken : String,
        @Header("Access-Token") accessToken : String
    ) : Call<AuthorizeResponse>
    @DELETE("users")
    fun deleteUser(
        @Header("Access-Token") accessToken : String
    )
    @GET("users")
    fun getUser(
        @Header("Access-Token") accessToken : String
    ) : Call<getUserResponse>
}