package com.hackathon.zero.domain.use_case

import android.util.Log
import com.hackathon.zero.data.UserInfoInput
import com.hackathon.zero.domain.UserInfoRepository
import com.hackathon.zero.util.Constants.ERROR_UNKNOWN
import com.hackathon.zero.core.Resource
import com.hackathon.zero.util.Constants.USER_ID
import com.hackathon.zero.util.SharedPreferencesUtil
import com.hackathon.zero.util.isSuccessful
import kotlinx.coroutines.flow.flow
import java.util.concurrent.CancellationException
import javax.inject.Inject

class PostUserInfoUseCase @Inject constructor(
    private val userInfoRepository: UserInfoRepository,
    private val sp: SharedPreferencesUtil
) {
    operator fun invoke(userInfoInput: UserInfoInput) = flow {
        try {
            emit(Resource.loading())
            val response = userInfoRepository.postUserInfo(userInfoInput)
            if (isSuccessful(response.status)) {
                response.result?.userId?.let {
                    sp.setInt(USER_ID, it)
                }

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