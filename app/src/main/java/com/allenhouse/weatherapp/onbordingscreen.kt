package com.allenhouse.weatherapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.allenhouse.weatherapp.databinding.ActivityOnbordingscreenBinding

class OnboardingScreen : AppCompatActivity() {
    private lateinit var binding: ActivityOnbordingscreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOnbordingscreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btn.setOnClickListener {
            val i = Intent(this, userdetails::class.java)
            startActivity(i)
        }

    }
}