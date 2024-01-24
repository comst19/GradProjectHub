package com.project.fat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import com.project.fat.databinding.ActivityLoadingBinding
import com.project.fat.manager.MonsterManager
import kotlinx.coroutines.launch

class LoadingActivity : AppCompatActivity() {

    private val binding: ActivityLoadingBinding by lazy {
        ActivityLoadingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycleScope.launch {
            MonsterManager.setRandomMonster()
        }

        // 애니메이션 리소스 파일을 로드합니다.
        val blinkAnimation = AnimationUtils.loadAnimation(this, R.anim.loading_blink)

        // 깜빡이는 애니메이션을 TextView에 적용합니다.
        binding.loading.startAnimation(blinkAnimation)

        // 5초 후 지도 액티비티로 전환 -
        Handler().postDelayed({
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000) // 20초 후에 MapsActivity로 전환합니다. -> 임시 방편으로 20초로 설정했습니다 => 지도 준비하는 시간
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.root.removeAllViews()
    }
}