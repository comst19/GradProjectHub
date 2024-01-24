package com.project.fat.data.dto

import com.google.gson.annotations.SerializedName

data class SocialLoginRequest(
    @SerializedName("token")
    val token : String
)
