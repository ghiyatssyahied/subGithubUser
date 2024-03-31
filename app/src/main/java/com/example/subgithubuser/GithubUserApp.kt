package com.example.subgithubuser

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.example.subgithubuser.ui.main.MainActivity
import com.example.subgithubuser.ui.setting.SettingPreferences
import com.example.subgithubuser.ui.setting.SettingViewModel
import com.example.subgithubuser.ui.setting.SettingViewModelFactory
import com.example.subgithubuser.ui.setting.dataStore

@SuppressLint("CustomSplashScreen")
class GithubUserApp : AppCompatActivity() {
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_splash)
        imageView = findViewById(R.id.imageView)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, DELAY_TIME)
        val pref = SettingPreferences.getInstance(application.dataStore)
        val mainViewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                imageView.setImageResource(R.drawable.logo_github_light)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                imageView.setImageResource(R.drawable.logo_github)

            }
        }
    }


    companion object {
        const val DELAY_TIME = 2500L
    }
}