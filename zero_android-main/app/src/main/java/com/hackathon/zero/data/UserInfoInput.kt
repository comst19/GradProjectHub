package com.hackathon.zero.data


import com.google.gson.annotations.SerializedName

data class UserInfoInput(
    @SerializedName("gender")
    val gender: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("weight")
    val weight: Int,
    @SerializedName("age")
    val age: Int,
    @SerializedName("purposeType")
    val purposeType: String,
    @SerializedName("activityType")
    val activityType: String
)