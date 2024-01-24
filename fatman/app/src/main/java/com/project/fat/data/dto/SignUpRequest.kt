package com.project.fat.data.dto

data class SignUpRequest(
    val email: String,
    val name: String,
    val password: String,
    val nickname: String,
    val address: String,
    val birth: String,
    val state: String
)
