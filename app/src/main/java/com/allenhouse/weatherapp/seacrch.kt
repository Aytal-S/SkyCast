package com.allenhouse.weatherapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class seacrch : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_seacrch)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val rvCity: RecyclerView = findViewById(R.id.rvcity)
        val tvCancel: TextView = findViewById(R.id.tvCancel)
        val etSearch: EditText = findViewById(R.id.citySearch)

        // Sample data for testing
        val cities = listOf("New York", "London", "Paris", "Tokyo", "Mumbai", "Delhi", "Sydney", "Berlin", "Dubai", "Singapore","Kochi","Kanpur")

        val userName = intent.getStringExtra("USER_NAME")

        val adapter = CityAdapter(cities) { cityName ->
            val intent = Intent(this, home::class.java)
            intent.putExtra("CITY_NAME", cityName)
            intent.putExtra("USER_NAME", userName)
            startActivity(intent)
        }
        rvCity.layoutManager = LinearLayoutManager(this)
        rvCity.adapter = adapter

        // Add search filtering logic
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().lowercase()
                val filteredList = if (query.isEmpty()) {
                    cities
                } else {
                    cities.filter { it.lowercase().contains(query) }
                }
                adapter.updateList(filteredList)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Add cancel button functionality to go back
        tvCancel.setOnClickListener {
            finish()
        }

        val searchButton: ImageButton = findViewById(R.id.search)

        searchButton.setOnClickListener {
            val cityName = etSearch.text.toString().trim()
            if (cityName.isNotEmpty()) {
                val intent = Intent(this, home::class.java)
                intent.putExtra("CITY_NAME", cityName)
                intent.putExtra("USER_NAME", userName)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please enter a city name", Toast.LENGTH_SHORT).show()
            }
        }
        
    }
}