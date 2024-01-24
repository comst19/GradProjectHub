package com.project.fat.googleLoginAccessToken

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface LoginService {
    @POST("oauth2/v4/token")
    fun getAccessToken(
        @Body request: LoginGoogleRequestModel
    ):
            Call<LoginGoogleResponseModel>


    @POST("login")
    @Headers("content-type: application/json")
    fun sendAccessToken(
        @Body accessToken:SendAccessTokenModel
    ): Call<String>

    companion object {

        private val gson = GsonBuilder().setLenient().create()

        private val httpClient = OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(20, TimeUnit.SECONDS)

        fun loginRetrofit(baseUrl: String): LoginService {
            return Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()
                .create(LoginService::class.java)
        }
    }
}