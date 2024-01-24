package com.project.fat.googleLoginAccessToken

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.project.fat.BuildConfig.google_client_id
import com.project.fat.BuildConfig.google_client_secret

class LoginRepository {
    private val getAccessTokenBaseUrl = "https://www.googleapis.com/"
    private val sendAccessTokenBaseUrl = "http://localhost:8080/"  //토큰을 보낼 서버 주소

    fun getAccessToken(authCode:String, callback: (String?) -> Unit) {

        LoginService.loginRetrofit(getAccessTokenBaseUrl).getAccessToken(
            request = LoginGoogleRequestModel(
                grant_type = "authorization_code",
                client_id = google_client_id,
                client_secret = google_client_secret,
                code = authCode.orEmpty()
            )
        ).enqueue(object : Callback<LoginGoogleResponseModel> {

            override fun onFailure(call: Call<LoginGoogleResponseModel>, t: Throwable) {
                Log.e(TAG, "getOnFailure: ",t.fillInStackTrace() )
                callback(null)
            }

            override fun onResponse(
                call: Call<LoginGoogleResponseModel>,
                response: Response<LoginGoogleResponseModel>
            ) {
                if(response.isSuccessful) {
                    val accessToken = response.body()?.access_token.orEmpty()
                    Log.d(TAG, "accessToken: $accessToken")
                    sendAccessToken(accessToken!!)
                    callback(accessToken)
                }
            }
        })
    }

    fun sendAccessToken(accessToken:String){
        LoginService.loginRetrofit(sendAccessTokenBaseUrl).sendAccessToken(
            accessToken = SendAccessTokenModel(accessToken)
        ).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    Log.d(TAG, "sendOnResponse: ${response.body()}")
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, "sendOnFailure: ${t.fillInStackTrace()}", )
            }
        })
    }


    companion object {
        const val TAG = "LoginRepository"
    }
}