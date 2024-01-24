package com.project.fat.data.dto

import com.google.gson.annotations.SerializedName

data class updateUserDetailRequest(
    @SerializedName("nickname")
    var nickname: String,

    @SerializedName("address")
    var address: String,

    @SerializedName("birth")
    var birth: String
)
