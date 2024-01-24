package com.project.fat.rankApi

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RankObject {
    private val base_url = "https://fatman.ap-northeast-2.elasticbeanstalk.com/api/"

    fun getApiService(): RankService {
        return getInstance().create(RankService::class.java)

    }
    fun getInstance(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.MINUTES)
            .readTimeout(50, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()


        return Retrofit.Builder()
            .baseUrl(base_url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}