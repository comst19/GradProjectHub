package com.project.fat.data.dto

import com.google.gson.annotations.SerializedName
import retrofit2.http.Header
import java.util.Date

data class SignInResponse(
    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("updatedAt")
    val updatedAt: String,

    @SerializedName("id")
    val id: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("role")
    val role: String,

    @SerializedName("nickname")
    val nickname: String,

    @SerializedName("birth")
    val birth: String,

    @SerializedName("authProvider")
    val authProvider: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("money")
    val money: Int,

    @SerializedName("activated")
    val activated: Boolean,

    @Header("Access-Token")
    val accessToken: String,

    @Header("Refresh-Token")
    val refreshToken: String
)
