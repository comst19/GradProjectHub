package com.hackathon.zero.data

import com.google.gson.annotations.SerializedName

data class HomeUserInfo(
    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("sugar")
    val sugar: Double,
    @SerializedName("calorie")
    val calorie: Int
)
