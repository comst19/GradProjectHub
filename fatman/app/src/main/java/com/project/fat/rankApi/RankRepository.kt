package com.project.fat.rankApi

import android.content.ContentValues.TAG
import android.util.Log
import com.project.fat.adapter.RecyclerviewAdapter
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import java.time.Year

class RankRepository {
    private var RankingApiService: RankService = RankObject.getApiService()


    fun totalRank(){
        RankingApiService!!.totalRank().enqueue(object : Callback<TotalRankResponseModel> {
            override fun onResponse(
                call: Call<TotalRankResponseModel>,
                response: Response<TotalRankResponseModel>
            ) {
                if (response.isSuccessful) {
                    var list = response.body()
                    val id = response.body()!!.id
                    val monsterNum = response.body()!!.monsterNum
                    val user = response.body()!!.user
                    val distance = response.body()!!.distance
                    Log.d(
                        TAG, "Id: $id" +
                                "\nMonsterNum: $monsterNum" +
                                "\nDistance: $distance" +
                                "\nlist: $list"
                    )

                }
            }

            override fun onFailure(call: Call<TotalRankResponseModel>, t: Throwable) {
                Log.e(TAG, "getOnFailure: ",t.fillInStackTrace() )
            }

        })
    }


    fun getTopTotalRank(){
        RankingApiService.getTopTotalRank().enqueue(object : Callback<TotalRankResponseModel>{
            override fun onResponse(
                call: Call<TotalRankResponseModel>,
                response: Response<TotalRankResponseModel>
            ) {
                if(response.isSuccessful){
                    val id = response.body()!!.id
                    val monsterNum = response.body()!!.monsterNum
                    val user = response.body()!!.user
                    val distance = response.body()!!.distance
                    Log.d(
                        TAG, "Id: $id" +
                                "\nMonsterNum: $monsterNum" +
                                "\nDistance: $distance" +
                                "\nUser Name: ${user.name}"
                    )
                }
            }

            override fun onFailure(call: Call<TotalRankResponseModel>, t: Throwable) {
                Log.e(TAG, "getOnFailure: ",t.fillInStackTrace() )
            }

        })
    }

    fun getWeekRank(){
        RankingApiService.getWeekRank().enqueue(object : Callback<WeekRankResponseModel>{
            override fun onResponse(
                call: Call<WeekRankResponseModel>,
                response: Response<WeekRankResponseModel>
            ) {
                if(response.isSuccessful){
                    val id = response.body()!!.id
                    val monsterNum = response.body()!!.monsterNum
                    val user = response.body()!!.user
                    val distance = response.body()!!.distance
                    val yearNum = response.body()!!.yearNum
                    val weekNum = response.body()!!.weekNum
                    Log.d(
                        TAG, "Id: $id" +
                                "\nMonsterNum: $monsterNum" +
                                "\nDistance: $distance" +
                                "\nUser Name: ${user.name}" +
                                "\nYear: $yearNum" +
                                "\nWeek: $weekNum"
                    )
                }
            }

            override fun onFailure(call: Call<WeekRankResponseModel>, t: Throwable) {
                Log.e(TAG, "getOnFailure: ",t.fillInStackTrace() )
            }

        })
    }

    fun getTopWeekRank(year: Int, week: Int){
        RankingApiService.getTopWeekRank(year, week).enqueue(object : Callback<WeekRankResponseModel>{
            override fun onResponse(
                call: Call<WeekRankResponseModel>,
                response: Response<WeekRankResponseModel>
            ) {
                if(response.isSuccessful){
                    val id = response.body()!!.id
                    val monsterNum = response.body()!!.monsterNum
                    val user = response.body()!!.user
                    val distance = response.body()!!.distance
                    val yearNum = response.body()!!.yearNum
                    val weekNum = response.body()!!.weekNum
                    Log.d(
                        TAG, "Id: $id" +
                                "\nMonsterNum: $monsterNum" +
                                "\nDistance: $distance" +
                                "\nUser Name: ${user.name}" +
                                "\nYear: $yearNum" +
                                "\nWeek: $weekNum"
                    )
                }
            }

            override fun onFailure(call: Call<WeekRankResponseModel>, t: Throwable) {
                Log.e(TAG, "getOnFailure: ",t.fillInStackTrace() )
            }

        })
    }
}