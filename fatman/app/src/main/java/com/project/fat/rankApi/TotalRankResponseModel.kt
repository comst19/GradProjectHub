package com.project.fat.rankApi

import com.google.gson.annotations.SerializedName

data class TotalRankResponseModel(
    @SerializedName("id")
    val id: String,

    @SerializedName("monsterNum")
    val monsterNum: Int,

    @SerializedName("distance")
    val distance: Float,

    @SerializedName("user")
    val user: SignInResponseModel
)
