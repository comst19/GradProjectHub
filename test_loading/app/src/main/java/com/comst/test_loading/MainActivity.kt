package com.comst.test_loading

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.comst.test_loading.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.floor

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val startTime = Calendar.getInstance().apply {
            set(2024, Calendar.JANUARY, 29, 0, 0, 0)
        }
        val endTime = Calendar.getInstance().apply {
            set(2024, Calendar.JANUARY, 29, 23, 59, 59)
        }

        val totalTime = endTime.timeInMillis - startTime.timeInMillis
        val initialProgress = Calendar.getInstance().timeInMillis - startTime.timeInMillis

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val startText = "Start Time: ${dateFormat.format(startTime.time)}"
        val endText = "Deadline: ${dateFormat.format(endTime.time)}"

        binding.startTime.text = startText
        binding.endTime.text = endText

        binding.progressBar.max = totalTime.toInt()
        binding.progressBar.progress = initialProgress.toInt()

        val mTimer = object : CountDownTimer(totalTime - initialProgress, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val currentProgress = totalTime - millisUntilFinished
                binding.textTimer.text = "남은 시간 : ${getTime(millisUntilFinished)}"
                binding.progressBar.progress = currentProgress.toInt()
            }

            override fun onFinish() {
                binding.textTimer.text = "00:00:00"
                binding.progressBar.progress = totalTime.toInt()
                // 타이머 종료 시 로직
            }
        }
        mTimer.start()
    }

    private fun getTime(millis: Long): String {
        val hours = millis / (1000 * 60 * 60) % 24
        val minutes = millis / (1000 * 60) % 60
        val seconds = millis / 1000 % 60

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}