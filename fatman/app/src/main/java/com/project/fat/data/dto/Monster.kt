package com.project.fat.data.dto

import com.google.gson.annotations.SerializedName

data class Monster(
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("id") val id : Long,
    @SerializedName("name") val name : String,
    @SerializedName("imageUrl") val imageUrl : String
)
