package com.project.fat.retrofit.api_interface

import com.project.fat.data.dto.TotalRankResponseModel

import com.project.fat.data.dto.WeekRankResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface RankService {
    @GET("totalRank")
    fun totalRank(
    ): Call<TotalRankResponseModel>

    @GET("totalRank/top")
    fun getTopTotalRank(
    ): Call<TotalRankResponseModel>

    @GET("weekRank")
    fun getWeekRank(
    ): Call<ArrayList<WeekRankResponseModel>>

    @GET("weekRank/{year}/{week}")
    fun getTopWeekRank(
        @Path(value = "year") year : String,
        @Path(value = "week") week : String
    ): Call<ArrayList<WeekRankResponseModel>>
}