package com.project.fat.retrofit

import com.project.fat.manager.TokenManager
import com.project.fat.retrofit.client.UserRetrofit

class LoginRepository {
    var loginApiService = UserRetrofit.getApiService()



    fun deleteUser(){
        loginApiService?.deleteUser(accessToken = TokenManager.getAccessToken()!!)
    }


}