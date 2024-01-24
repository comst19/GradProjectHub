package com.project.fat.data.dto

import com.google.gson.annotations.SerializedName

data class SocialLoginResponse (
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("newUser")
    val newUser: Boolean
)
