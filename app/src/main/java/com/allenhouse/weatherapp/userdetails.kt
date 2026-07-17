package com.allenhouse.weatherapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.allenhouse.weatherapp.databinding.ActivityUserdetailsBinding

class userdetails : AppCompatActivity() {

    private lateinit var binding: ActivityUserdetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        binding = ActivityUserdetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.Continuebtn.setOnClickListener {
            val name = binding.etFullName.text.toString().trim()
            val location = binding.location.text.toString().trim()

            if (name.isEmpty() || location.isEmpty()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Location saved: $location", Toast.LENGTH_SHORT).show()
                val i = Intent(this, home::class.java)
                i.putExtra("USER_NAME", name)
                i.putExtra("USER_LOCATION", location)
                startActivity(i)
                finish()
            }
        }
    }
}
