package com.hackathon.zero.domain

import com.hackathon.zero.core.dto.ResponseBody
import com.hackathon.zero.data.HomeData
import com.hackathon.zero.data.HomeUserInfo
import com.hackathon.zero.data.PostUserInfo
import com.hackathon.zero.data.UserInfoInput

interface UserInfoRepository {
    suspend fun postUserInfo(userInfoInput: UserInfoInput): ResponseBody<PostUserInfo?>

    suspend fun getUserInfo(userId: Int): ResponseBody<HomeUserInfo?>

    suspend fun getHomeData(userId: Int): ResponseBody<HomeData?>
}