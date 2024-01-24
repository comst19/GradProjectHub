package com.project.fat.data.dto
import com.google.gson.annotations.SerializedName

data class CreateHistoryRequest(
    @SerializedName("monster_num") val monsterNum: Int,
    @SerializedName("distance") val distance: Double,
    @SerializedName("date") val date: String
)
