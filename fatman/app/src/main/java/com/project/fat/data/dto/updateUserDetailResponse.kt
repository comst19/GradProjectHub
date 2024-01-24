package com.project.fat.data.dto

import com.google.gson.annotations.SerializedName

data class updateUserDetailResponse(
    @SerializedName("email")
    var email: String,

    @SerializedName("name")
    var name: String,

    @SerializedName("nickname")
    var nickname: String,

    @SerializedName("address")
    var address: String,

    @SerializedName("birth")
    var birth: String
)
