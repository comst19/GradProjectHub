package com.project.fat

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import com.project.fat.data.dto.SignInRequest
import com.project.fat.data.dto.SignInResponse
import com.project.fat.data.dto.getUserResponse
import com.project.fat.dataStore.UserDataStore
import com.project.fat.dataStore.UserDataStore.dataStore
import com.project.fat.databinding.ActivitySignInBinding
import com.project.fat.manager.TokenManager
import com.project.fat.retrofit.client.UserRetrofit
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {
    var loginApiService = UserRetrofit.getApiService()

    lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)

        setContentView(binding.root)


        binding.arrowBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
        binding.signInBtn.setOnClickListener{
            val email = binding.signInEmail.text?.toString()
            val password = binding.signInPassword.text?.toString()

            if(email.isNullOrEmpty())
                Toast.makeText(this, "이메일을 입력하세요", Toast.LENGTH_SHORT).show()
            else if(password.isNullOrEmpty())
                Toast.makeText(this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show()
            else
                signIn(email, password)


        }
    }

    fun signIn(email: String, password: String){
        var signIn = SignInRequest(email, password)

        loginApiService?.signIn(signIn)?.enqueue(object : Callback<SignInResponse> {
            override fun onResponse(
                call: Call<SignInResponse>,
                response: Response<SignInResponse>
            ) {
                if(response.isSuccessful){
                    val result = response.body()

                        val accessToken = response.headers()["Access-Token"].toString()
                        val refreshToken = response.headers()["Refresh-Token"].toString()

                        val id = result?.id
                        val email = result?.email
                        val name = result?.name
                        val nickname = result?.nickname
                        val money = result?.money

                        Log.d(
                            ContentValues.TAG, "Id: $id" +
                                    "\nEmail: $email" +
                                    "\nName: $name" +
                                    "\nNickName: $nickname" +
                                    "\nAccess-Token: $accessToken" +
                                    "\nRefresh-Token: $refreshToken"
                        )
                    if(nickname != null) {
                        saveToken(accessToken, refreshToken)
                        getUser(accessToken)
                    } else
                        Toast.makeText(this@SignInActivity, "아이디나 비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
//                Log.e(ContentValues.TAG, "getOnFailure: ",t.fillInStackTrace() )
            }

        })
    }
    fun moveActivity(nickname: String, money: Int){
        val intent = Intent(this, BottomNavigationActivity::class.java)
        intent.putExtra("nickname", nickname)
        intent.putExtra("money",money)
        startActivity(intent)
        finish()
    }


    private fun saveToken(accessToken : String, refreshToken : String){ //access token과 refresh token을 dataStore에 저장
        lifecycleScope.launch {
            Log.d("saveToken in dataStore", "start")
            Log.d("saveToken", " context.dataStore = ${this@SignInActivity?.dataStore}")
            this@SignInActivity.dataStore.edit {
                Log.d("saveToken in dataStore", "start")
                it[UserDataStore.ACCESS_TOKEN] = accessToken
                Log.d("saveToken in dataStore", "accessToken saved")
                it[UserDataStore.REFRESH_TOKEN] = refreshToken
                Log.d("saveToken in dataStore", "refreshToken saved end")
            }

            TokenManager.setToken(accessToken, refreshToken)
            Log.d("saveToken in dataStore", "end")
        }
    }
    fun getUser(accessToken: String){
        loginApiService?.getUser(accessToken)?.enqueue(object : Callback<getUserResponse> {
            override fun onResponse(
                call: Call<getUserResponse>,
                response: Response<getUserResponse>
            ) {
                if(response.isSuccessful){
                    val result = response.body()!!
                    val email = result.email
                    val userName = result.name
                    val nickname = result.nickname
                    val money = result.money
                    val address = result.address
                    val birth = result.birth

                    moveActivity(nickname,money)


                    Log.d(
                        "getUser()", "유저 정보 불러오기" +
                                "\nEmail: $email" +
                                "\nName: $userName" +
                                "\nNickName: $nickname" +
                                "\nMoney: $money" +
                                "\nAddress: $address" +
                                "\nBirth: $birth" +
                                "\nAccessToken: $accessToken"
                    )

                }

            }
            override fun onFailure(call: Call<getUserResponse>, t: Throwable) {
                //Log.e(ContentValues.TAG, "getOnFailure: ",t.fillInStackTrace())
            }
        })
    }
}