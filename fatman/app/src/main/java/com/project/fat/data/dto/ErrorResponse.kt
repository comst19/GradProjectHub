package com.project.fat.data.dto

data class ErrorResponse(
    val errorMessage : String,
    val httpStatus : String,
    val code : String
)
