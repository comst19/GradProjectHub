package com.project.fat

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.project.fat.data.dto.SignUpRequest
import com.project.fat.databinding.ActivitySignUp2Binding
import com.project.fat.databinding.ActivitySignUpBinding
import com.project.fat.retrofit.client.UserRetrofit
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class SignUp2Activity : AppCompatActivity() {
    lateinit var binding: ActivitySignUp2Binding

    var loginApiService = UserRetrofit.getApiService()

    var loginState: String? = "ROLE_USER"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUp2Binding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.arrowBack.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
        binding.signUpBtn.setOnClickListener {
            val userName = binding.signUpUsername.text?.toString()
            val nickname = binding.signUpNickname.text?.toString()
            val address = binding.signUpAddress.text?.toString()
            val birth = binding.signUpBirth.text?.toString()
            val email = intent?.getStringExtra("email")
            val password = intent?.getStringExtra("password")

            if (userName.isNullOrEmpty())
                Toast.makeText(this, "이름을 입력해야합니다", Toast.LENGTH_SHORT).show()
            else if (address.isNullOrEmpty())
                Toast.makeText(this, "주소를 입력해야합니다", Toast.LENGTH_SHORT).show()
            else if (birth.isNullOrEmpty())
                Toast.makeText(this, "생년월일을 입력해야합니다", Toast.LENGTH_SHORT).show()
            else if (nickname.isNullOrEmpty())
                Toast.makeText(this, "닉네임을 입력해야합니다", Toast.LENGTH_SHORT).show()
            else {
                Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                signUp(email!!, userName, password, nickname, address, birth, loginState)
            }
        }
    }

    fun moveActivity(){
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }



    fun signUp(
        email: String,
        name: String?,
        password: String?,
        nickname: String?,
        address: String,
        birth: String,
        state: String?
    ){
        val signup = SignUpRequest(email, name!!,password!!,nickname!!,address,birth,state!!)
        loginApiService?.signUp(signup)!!.enqueue(object: retrofit2.Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    //val message = response.body()
                    Log.d(TAG, "회원가입 성공: ${response.message()}")
                    Toast.makeText(this@SignUp2Activity, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()

                    moveActivity()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(ContentValues.TAG, "getOnFailure: ",t.fillInStackTrace() )
            }

        })
    }
}