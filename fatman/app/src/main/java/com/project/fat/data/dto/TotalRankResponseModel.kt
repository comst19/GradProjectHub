package com.project.fat.data.dto

import com.google.gson.annotations.SerializedName

typealias TotalRankResponseModel = ArrayList<TotalRankResponse>

data class TotalRankResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("monsterNum")
    val monsterNum: Int,

    @SerializedName("distance")
    val distance: Float,

    @SerializedName("user")
    val user: SignInResponse
)
