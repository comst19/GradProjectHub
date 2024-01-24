package com.project.fat.data.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

typealias GetHistoryResponse = ArrayList<GetHistoryResponseElement>

data class GetHistoryResponseElement (

    @SerializedName("id")
    @Expose val id: Int? = null,

    @SerializedName("date")
    @Expose val date: String? = null,

    @SerializedName("monsterNum")
    @Expose val monsterNum: Int? = null,

    @SerializedName("distance")
    @Expose val distance: Double? = null
)

data class User (
    val createdAt: String,
    val updatedAt: String,
    val id: Long,
    val email: String,
    val name: String,
    val nickname: String,
    val birth: String,
    val social: Any? = null,
    val address: String,
    val activated: Boolean
)
