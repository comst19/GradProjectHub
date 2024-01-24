package com.hackathon.zero.domain.use_case

import com.hackathon.zero.core.Resource
import com.hackathon.zero.domain.UserInfoRepository
import com.hackathon.zero.util.Constants.ERROR_UNKNOWN
import com.hackathon.zero.util.isSuccessful
import kotlinx.coroutines.flow.flow
import java.util.concurrent.CancellationException
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) {
    operator fun invoke(userId: Int) = flow {
        try {
            emit(Resource.loading())
            val response = userInfoRepository.getUserInfo(userId)
            if (isSuccessful(response.status)) {
                emit(Resource.success(response.result))
            } else {
                emit(Resource.error(response.message))
            }
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            emit(Resource.error(e.localizedMessage ?: ERROR_UNKNOWN))
        }
    }
}