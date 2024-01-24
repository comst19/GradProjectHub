package com.hackathon.zero.core.dto

data class ResponseBody<T: Any?>(
    val status: Int, // http 상태 코드
    val code: String, // 서버 측에서 자체적으로 정한 코드
    val message: String,
    val result: T?
)
