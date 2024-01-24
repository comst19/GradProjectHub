package com.project.fat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import com.project.fat.databinding.ActivityCountBinding

class CountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCountBinding
    private lateinit var countTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        countTextView = binding.countText

        val countDownTimer = object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000

                if (secondsRemaining > 0) {
                    countTextView.text = secondsRemaining.toString()
                } else {
                    countTextView.text = "Go!"
                }
            }
            override fun onFinish() {
                moveToTimer()
            }
        }
        countDownTimer.start()
    }
    private fun moveToTimer(){
        var intent= Intent(this, RunningTimeActivity::class.java)
        startActivity(intent)
        finish()
    }
}