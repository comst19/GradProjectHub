package com.project.fat.data.dto

import java.time.LocalDateTime

data class CreateHistoryResponse (
    val monsterNum: Long,
    val distance: Double,
    val id: Long,
    val user: CreateHistoryUser,
    val date: String
)

data class CreateHistoryUser (
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