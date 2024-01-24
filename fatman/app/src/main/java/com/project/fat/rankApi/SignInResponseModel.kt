package com.project.fat.rankApi

import com.google.gson.annotations.SerializedName
import java.util.Date

data class SignInResponseModel(
    @SerializedName("createdAt")
    val createdAt: Date,

    @SerializedName("updatedAt")
    val updatedAt: Date,

    @SerializedName("id")
    val id: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("nickname")
    val nickname: String,

    @SerializedName("birth")
    val birth: Date,

    @SerializedName("social")
    val social: Boolean,

    @SerializedName("address")
    val address: String,

    @SerializedName("activated")
    val activated: Boolean,

    /*@Header("Refresh-Token")
    val refresh_token: String,

    @Header("Access-Token")
    val access_token: String*/
)
