package com.hackathon.zero.di

import android.content.Context
import com.hackathon.zero.data.repository.ProductRepositoryImpl
import com.hackathon.zero.data.repository.UserInfoRepositoryImpl
import com.hackathon.zero.di.api.ProductListApi
import com.hackathon.zero.di.api.ZeroUserApi
import com.hackathon.zero.domain.ProductRepository
import com.hackathon.zero.domain.UserInfoRepository
import com.hackathon.zero.util.Constants
import com.hackathon.zero.util.SharedPreferencesUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton // have a singleton...
    fun provideHttpClient(): OkHttpClient {

        val interceptor = HttpLoggingInterceptor().apply {
            // level : BODY -> logs headers + bodies of request, response
            // NONE, BASIC, HEADERS, BODY
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
            connectTimeout(30, TimeUnit.SECONDS)
            // readTimeout : maximum time gap between 'arrivals' of two data packets when waiting for the server's response
            readTimeout(20, TimeUnit.SECONDS)
            // writeTimeout : maximum time gap between two data packets when 'sending' them to the server
            writeTimeout(25, TimeUnit.SECONDS)
        }.build()

        return client
    }

    @Provides
    @Singleton
    fun provideService(okHttpClient: OkHttpClient): ZeroUserApi =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ZeroUserApi::class.java)

    @Provides
    @Singleton
    fun provideProductListService(okHttpClient: OkHttpClient): ProductListApi =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductListApi::class.java)

    @Provides
    @Singleton
    fun provideUserInfoRepository(api: ZeroUserApi): UserInfoRepository =
        UserInfoRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideProductRepository(api: ProductListApi): ProductRepository =
        ProductRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ) = SharedPreferencesUtil(context)
}