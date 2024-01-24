package com.project.fat.data.dto

import com.google.gson.annotations.SerializedName

data class AddUserMonsterRequest(
    @SerializedName("monster_id")
    val monster_id : Long
)
