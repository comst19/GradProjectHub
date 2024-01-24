package com.project.fat.data.dto

import com.google.gson.annotations.SerializedName

//typealias WeekRankResponseModel = ArrayList<WeekRankResponse>

data class WeekRankResponseModel(
    @SerializedName("id")
    var id: String,

    @SerializedName("monsterNum")
    var monsterNum: Int,

    @SerializedName("distance")
    var distance: Float,

    @SerializedName("user")
    var user: SignInResponse,

    @SerializedName("weekNum")
    var weekNum: Int,

    @SerializedName("yearNum")
    var yearNum: Int
)
