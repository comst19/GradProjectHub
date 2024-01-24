package com.hackathon.zero.data.repository

import com.hackathon.zero.core.dto.ResponseBody
import com.hackathon.zero.data.HomeData
import com.hackathon.zero.data.HomeUserInfo
import com.hackathon.zero.data.PostUserInfo
import com.hackathon.zero.data.UserInfoInput
import com.hackathon.zero.di.api.ZeroUserApi
import com.hackathon.zero.domain.UserInfoRepository

class UserInfoRepositoryImpl(
    private val api: ZeroUserApi
) : UserInfoRepository {
    override suspend fun postUserInfo(userInfoInput: UserInfoInput): ResponseBody<PostUserInfo?> {
        return api.postUserInfo(userInfoInput)
    }

    override suspend fun getUserInfo(userId: Int): ResponseBody<HomeUserInfo?> {
        return api.getUserInfo(userId)
    }

    override suspend fun getHomeData(userId: Int): ResponseBody<HomeData?> {
        return api.getHomeInfo(userId)
    }
}