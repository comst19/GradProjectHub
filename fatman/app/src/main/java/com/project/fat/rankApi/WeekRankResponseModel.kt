package com.project.fat.rankApi

import com.google.gson.annotations.SerializedName

data class WeekRankResponseModel(
    @SerializedName("id")
    val id: String,

    @SerializedName("monsterNum")
    val monsterNum: Int,

    @SerializedName("distance")
    val distance: Float,

    @SerializedName("user")
    val user: SignInResponseModel,

    @SerializedName("weekNum")
    val weekNum: Int,

    @SerializedName("weekNum")
    val yearNum: Int,
)
