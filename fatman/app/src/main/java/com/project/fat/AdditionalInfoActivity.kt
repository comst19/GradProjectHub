package com.project.fat

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.project.fat.data.dto.getUserResponse
import com.project.fat.data.dto.updateUserDetailRequest
import com.project.fat.data.dto.updateUserDetailResponse
import com.project.fat.dataStore.UserDataStore
import com.project.fat.databinding.ActivityAdditionalInfoBinding
import com.project.fat.manager.UserDataManager
import com.project.fat.retrofit.client.UserRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdditionalInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdditionalInfoBinding

    var loginApiService = UserRetrofit.getApiService()
    var nickname: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdditionalInfoBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.arrowBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        binding.addInfoBtn.setOnClickListener {
            nickname = binding.addInfoNickname.text?.toString()
            val address = binding.addInfoAddress.text?.toString()
            val birth = binding.addInfoBirth.text?.toString()

            if(nickname.isNullOrEmpty())
                Toast.makeText(this, "닉네임을 입력해야합니다", Toast.LENGTH_SHORT).show()
            else if(address.isNullOrEmpty())
                Toast.makeText(this, "주소를 입력해야합니다", Toast.LENGTH_SHORT).show()
            else if(birth.isNullOrEmpty())
                Toast.makeText(this, "생년월일을 입력해야합니다", Toast.LENGTH_SHORT).show()
            else
                updateUserDetail2(nickname!!, address,birth)

        }

    }
    fun updateUserDetail2(nickname: String, address: String, birth: String){
        val update = updateUserDetailRequest(nickname, address, birth)

        loginApiService?.updateUserDetail2(accessToken = UserDataStore.ACCESS_TOKEN.toString(), update)
            ?.enqueue(object : Callback<updateUserDetailResponse> {
                override fun onResponse(
                    call: Call<updateUserDetailResponse>,
                    response: Response<updateUserDetailResponse>
                ) {
                    if(response.isSuccessful){
                        val result = response.body()!!
                        val email = result.email
                        val name = result.name
                        val nickname = result.nickname
                        val money: Int = 0

                        Toast.makeText(this@AdditionalInfoActivity, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()

                        Toast.makeText(this@AdditionalInfoActivity, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()

                        moveActivity(nickname, money)

                        Log.d(
                            ContentValues.TAG, "Email: $email" +
                                    "\nName: $name" +
                                    "\nNickName: $nickname"
                        )
                    }
                }

                override fun onFailure(call: Call<updateUserDetailResponse>, t: Throwable) {
                    Log.e(ContentValues.TAG, "getOnFailure: ",t.fillInStackTrace())
                }

            })
    }

    fun moveActivity(nickname: String, money: Int){
        val intent = Intent(this, BottomNavigationActivity::class.java)
        intent.putExtra("nickname", nickname)
        intent.putExtra("money", money)
        startActivity(intent)
        finish()
    }
}