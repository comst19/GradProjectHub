package com.project.fat.manager

import android.util.Log
import com.project.fat.data.dto.AuthorizeResponse
import com.project.fat.retrofit.client.UserRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object TokenManager {
    private var accessToken : String? = null
    private var refreshToken : String? = null

    fun setToken(accessToken : String, refreshToken : String){
        TokenManager.accessToken = accessToken
        TokenManager.refreshToken = refreshToken
    }
    fun logout(){
        this.accessToken = null
        this.refreshToken = null
    }

    fun getAccessToken() = accessToken

    fun getRefreshToken() = refreshToken

    fun authorize(
        accessToken: String,
        refreshToken: String,
        callback: (Boolean, String?, String?) -> Unit) {

        if(TokenManager.accessToken == null || TokenManager.refreshToken == null) {
            TokenManager.accessToken = accessToken
            TokenManager.refreshToken = refreshToken
        }

        UserRetrofit.getApiService()!!.authorize(refreshToken, accessToken)
            .enqueue(object : Callback<AuthorizeResponse>{
                override fun onResponse(
                    call: Call<AuthorizeResponse>,
                    response: Response<AuthorizeResponse>
                ) {
                    if (response.isSuccessful) {
                        val result = response.headers()
                        val backendApiAccessToken = result["Access-Token"]
                        val backendApiRefreshToken = result["Refresh-Token"]
                        Log.d(
                            "BackEnd API Authorize Success",
                            "accessToken : $backendApiAccessToken\nrefreshToken : $backendApiRefreshToken"
                        )
                        callback(true, backendApiAccessToken, backendApiRefreshToken)
                    }
                    else{
                        Log.d("BackEnd API Authorize response not successful", "Error : ${response.code()}")
                        callback(false, null, null)
                    }
                }

                override fun onFailure(call: Call<AuthorizeResponse>, t: Throwable) {
                    Log.d("BackEnd API Authorize Failure", "Fail : ${t.printStackTrace()}\n Error message : ${t.message}")
                    callback(false, null, null)
                }

            })
    }
}