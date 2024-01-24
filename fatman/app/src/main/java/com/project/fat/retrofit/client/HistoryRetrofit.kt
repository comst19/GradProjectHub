package com.project.fat.retrofit.client

import com.google.gson.GsonBuilder
import com.project.fat.BuildConfig
import com.project.fat.retrofit.api_interface.HistoryService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object HistoryRetrofit {
    private const val BASE_URL = BuildConfig.backend_api_end_point

    fun getApiService() : HistoryService?{
        return getInstance()?.create(HistoryService::class.java)
    }

    private fun getInstance(): Retrofit? {
        val gson = GsonBuilder().setLenient().create()

        val httpClient = OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.MINUTES)
            .readTimeout(50, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(loggingInterceptor)

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()
    }
}