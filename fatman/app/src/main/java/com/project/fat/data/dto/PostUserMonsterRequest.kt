package com.project.fat.data.dto

import com.google.gson.annotations.SerializedName

data class PostUserMonsterRequest(
    @SerializedName("monsterName") val name : String
)
