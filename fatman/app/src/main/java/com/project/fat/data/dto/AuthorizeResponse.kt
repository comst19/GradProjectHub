package com.project.fat.data.dto

import retrofit2.http.Header

data class AuthorizeResponse(
    @Header("Refresh-Token") val refreshToken : String,
    @Header("Access-Token") val accessToken : String
)
