package com.hackathon.zero.data


import com.google.gson.annotations.SerializedName

data class PostUserInfo(
    @SerializedName("age")
    val age: Int,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("weight")
    val weight: Int
)