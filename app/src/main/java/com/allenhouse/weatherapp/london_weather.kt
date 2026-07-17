package com.allenhouse.weatherapp

import android.content.Intent
import android.widget.LinearLayout
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class london_weather : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_london_weather)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val cityName = intent.getStringExtra("cityName")
        findViewById<TextView>(R.id.tvCityName).text = cityName

        val userName = intent.getStringExtra("USER_NAME")
        if (!userName.isNullOrEmpty()) {
            findViewById<TextView>(R.id.tvLondonUserName).text = "Welcome, $userName!"
        }

        val hourlyWeather = findViewById<LinearLayout>(R.id.hourlyweather)
        hourlyWeather.setOnClickListener {
            val intent = Intent(this, hourlyforcast::class.java)
            startActivity(intent)
        }
    }
}
