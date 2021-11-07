package com.jogayed.tempo.journal.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jogayed.tempo.journal.R
import com.jogayed.tempo.journal.news.presentation.view.NewsActivity
import kotlinx.coroutines.delay

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        lifecycleScope.launchWhenStarted {
            delay(2000)

            Intent(this@SplashActivity, NewsActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }
}