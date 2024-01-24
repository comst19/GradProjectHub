package com.project.fat

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.project.fat.databinding.ActivitySignInBinding
import com.project.fat.databinding.ActivitySignUpBinding
import com.project.fat.retrofit.client.UserRetrofit
import retrofit2.Call
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    var loginApiService = UserRetrofit.getApiService()

    var email: String? = null
    var password: String? = null
    var password_check: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.arrowBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
        binding.signUpBtn.setOnClickListener {
            email = binding.signUpEmail.text?.toString()
            password = binding.signUpPassword.text?.toString()
            password_check = binding.signUpPassword2.text?.toString()

            if(email.isNullOrEmpty())
                Toast.makeText(this, "이메일을 입력해야합니다", Toast.LENGTH_SHORT).show()
            else if(password.isNullOrEmpty())
                Toast.makeText(this, "비밀번호를 입력해야합니다", Toast.LENGTH_SHORT).show()
            else if(password_check.isNullOrEmpty())
                Toast.makeText(this, "비밀번호를 다시 입력해야합니다", Toast.LENGTH_SHORT).show()
            else if (password != password_check)
                Toast.makeText(this, "비밀번호를 확인하세요", Toast.LENGTH_SHORT).show()
            else
                moveActivity()


        }
    }

    fun moveActivity() {
        val intent = Intent(this, SignUp2Activity::class.java)
        intent.putExtra("email", email)
        intent.putExtra("password", password)
        startActivity(intent)
        finish()
    }




}