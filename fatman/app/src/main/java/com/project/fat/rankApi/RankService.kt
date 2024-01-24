package com.project.fat.rankApi

import retrofit2.Call
import retrofit2.http.GET
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
    ): Call<WeekRankResponseModel>

    @GET("weekRank/{year}/{week}")
    fun getTopWeekRank(
        @Path(value = "year") year : Int,
        @Path(value = "week") week : Int
    ): Call<WeekRankResponseModel>
}