package com.allenhouse.weatherapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class setting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_setting)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val homeLayout = findViewById<LinearLayout>(R.id.homepage)
        homeLayout.setOnClickListener {
            val intent = Intent(this, home::class.java)
            startActivity(intent)
        }

        val editLayout = findViewById<LinearLayout>(R.id.editpage)
        editLayout.setOnClickListener {
            val intent = Intent(this, userdetails::class.java)
            startActivity(intent)
        }

        val locationLayout = findViewById<LinearLayout>(R.id.locationpage)
        locationLayout.setOnClickListener {
            val intent = Intent(this, location::class.java)
            startActivity(intent)
        }

        val darkModeLayout = findViewById<LinearLayout>(R.id.darkmode_layout)
        val darkModeIcon = findViewById<ImageView>(R.id.darkmode_switch)

        // Initialize icon based on current mode
        val currentMode = AppCompatDelegate.getDefaultNightMode()
        if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
            darkModeIcon.setImageResource(R.drawable.switch_on_svgrepo_com) // Assuming switch_on is for dark
        } else {
            // If you have a switch_off, use it here. For now keeping it simple.
            darkModeIcon.setImageResource(R.drawable.switch_on_svgrepo_com)
        }

        darkModeLayout.setOnClickListener {
            val isDarkMode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            recreate() // Recreate to apply theme changes immediately
        }

        val logoutButton = findViewById<Button>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
