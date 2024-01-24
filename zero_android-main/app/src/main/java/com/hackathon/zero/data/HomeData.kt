package com.hackathon.zero.data


import com.google.gson.annotations.SerializedName

data class HomeData(
    @SerializedName("completedDate")
    val completedDate: List<Int>,
    @SerializedName("totalKcal")
    val totalKcal: String,
    @SerializedName("totalSugar")
    val totalSugar: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("username")
    val username: String
)