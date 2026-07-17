package com.allenhouse.weatherapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import android.widget.ImageView
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.widget.Toast

class home : AppCompatActivity() {


    private lateinit var tvTodayMax: TextView
    private lateinit var tvTodayMin: TextView
    private lateinit var tvTueMax: TextView
    private lateinit var tvTueMin: TextView
    private lateinit var tvWedMax: TextView
    private lateinit var tvWedMin: TextView
    private lateinit var tvThuMax: TextView
    private lateinit var tvThuMin: TextView
    private lateinit var tvFriMax: TextView
    private lateinit var tvFriMin: TextView
    private lateinit var HourlyForecast: TextView

    private lateinit var tvDay1: TextView
    private lateinit var tvDay2: TextView
    private lateinit var tvDay3: TextView
    private lateinit var tvDay4: TextView
    private lateinit var tvDay5: TextView

    private lateinit var imgToday: ImageView
    private lateinit var imgTue: ImageView
    private lateinit var imgWed: ImageView
    private lateinit var imgThu: ImageView
    private lateinit var imgFri: ImageView
    private lateinit var tvTemperature: TextView
    private lateinit var tvCondition: TextView
    private lateinit var tvFeelsLike: TextView
    private lateinit var tvHumidity: TextView
    private lateinit var tvWind: TextView
    private lateinit var tvSunrise: TextView
    private lateinit var tvSunset: TextView
    private lateinit var tvUserLocation: TextView

    private lateinit var imgWeather: ImageView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        HourlyForecast=findViewById<TextView>(R.id.HourlyForecast)
        tvTemperature = findViewById<TextView>(R.id.tvTemperature)
        tvCondition = findViewById<TextView>(R.id.tvCondition)
        tvFeelsLike = findViewById<TextView>(R.id.tvFeelsLike)
        tvHumidity = findViewById<TextView>(R.id.tvHumidity)
        tvWind = findViewById<TextView>(R.id.tvWind)
        tvSunrise = findViewById<TextView>(R.id.tvSunrise)
        tvSunset = findViewById<TextView>(R.id.tvSunset)
        imgWeather = findViewById<ImageView>(R.id.imgWeather)

        tvTodayMax = findViewById<TextView>(R.id.tvTodayMax)
        tvTodayMin = findViewById<TextView>(R.id.tvTodayMin)

        tvTueMax = findViewById(R.id.tvTueMax)
        tvTueMin = findViewById(R.id.tvTueMin)

        tvWedMax = findViewById(R.id.tvWedMax)
        tvWedMin = findViewById(R.id.tvWedMin)

        tvThuMax = findViewById(R.id.tvThuMax)
        tvThuMin = findViewById(R.id.tvThuMin)

        tvFriMax = findViewById(R.id.tvFriMax)
        tvFriMin = findViewById(R.id.tvFriMin)

        tvDay1 = findViewById(R.id.tvDay1)
        tvDay2 = findViewById(R.id.tvDay2)
        tvDay3 = findViewById(R.id.tvDay3)
        tvDay4 = findViewById(R.id.tvDay4)
        tvDay5 = findViewById(R.id.tvDay5)

        imgToday = findViewById(R.id.imgToday)
        imgTue = findViewById(R.id.imgTue)
        imgWed = findViewById(R.id.imgWed)
        imgThu = findViewById(R.id.imgThu)
        imgFri = findViewById(R.id.imgFri)


        val drawerLayout: DrawerLayout = findViewById(R.id.main)
        val navigationView: NavigationView = findViewById(R.id.navigationView)
        val menuIcon: ImageView = findViewById(R.id.menu_icon)
        val tvUserName: TextView = findViewById(R.id.tvUserName)

        HourlyForecast.setOnClickListener {
            val intent = Intent(this, hourlyforcast::class.java)
            intent.putExtra("CITY_NAME",tvUserLocation.text.toString())
            startActivity(intent)
        }

        tvUserLocation = findViewById(R.id.tvUserLocation)
        val userName = intent.getStringExtra("USER_NAME")
        if (!userName.isNullOrEmpty()) {
            tvUserName.text = "Welcome, $userName!"
        }

        val userLocation = intent.getStringExtra("USER_LOCATION")
        if (!userLocation.isNullOrEmpty()) {
            tvUserLocation.text = userLocation
        }

        ViewCompat.setOnApplyWindowInsetsListener(drawerLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        menuIcon.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.search -> {
                    val intent = Intent(this, seacrch::class.java)
                    intent.putExtra("USER_NAME", userName)
                    startActivity(intent)
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.settings -> {
                    startActivity(Intent(this, setting::class.java))
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.location -> {
                    startActivity(Intent(this, location::class.java))
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
            }
            true
        }
        val city = if (intent.hasExtra("CITY_NAME")) {intent.getStringExtra("CITY_NAME")
        } else{
            intent.getStringExtra("USER_LOCATION") } ?: "Delhi"
        getWeather(city)
    }

    private fun getWeather(city: String) {
        RetrofitClient.api.getWeather(
            city,
            "94fa22efd6b150c5a87bf4e96c4ac6fa"
        ).enqueue(object : Callback<WeatherResponse> {

            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val weather = response.body()!!

                    tvTemperature.text = "${weather.main.temp.toInt()}°C"
                    tvCondition.text = weather.weather[0].description
                    val condition = weather.weather[0].main
                    
                    // Use weather.dt for the city's local time calculation
                    val currentTime = weather.dt
                    val isNight = currentTime < weather.sys.sunrise || currentTime > weather.sys.sunset

                    val mainLayout = findViewById<DrawerLayout>(R.id.main)

                    // Background Change Logic
                    when (condition) {
                        "Rain", "Drizzle", "Thunderstorm", "Snow" -> {
                            mainLayout.setBackgroundResource(R.drawable.bggradientdark)
                        }
                        else -> {
                            if (isNight) {
                                mainLayout.setBackgroundResource(R.drawable.bggradientdark)
                            } else {
                                mainLayout.setBackgroundResource(R.drawable.bggradient)
                            }
                        }
                    }

                    // Weather Icon Logic
                    when (condition) {
                        "Clear" -> {
                            if (isNight) {
                                imgWeather.setImageResource(R.drawable.half_moon_svgrepo_com)
                            } else {
                                imgWeather.setImageResource(R.drawable.suniday)
                            }
                        }
                        "Clouds", "Mist", "Haze", "Fog" -> {
                            if (isNight) {
                                imgWeather.setImageResource(R.drawable.hail_halfmoon_svgrepo_com)
                            } else {
                                imgWeather.setImageResource(R.drawable.cloud)
                            }
                        }
                        "Rain", "Drizzle", "Snow" -> {
                            if (isNight) {
                                imgWeather.setImageResource(R.drawable.hail_halfmoon_svgrepo_com)
                            } else {
                                imgWeather.setImageResource(R.drawable.heavy_rain)
                            }
                        }
                        "Thunderstorm" -> imgWeather.setImageResource(R.drawable.darkclod)
                        else -> imgWeather.setImageResource(R.drawable.suniday)
                    }

                    tvFeelsLike.text = "Feels Like ${weather.main.feels_like.toInt()}°C"
                    tvHumidity.text = "Humidity ${weather.main.humidity}%"
                    tvWind.text = "Wind ${weather.wind.speed} m/s"
                    tvUserLocation.text = city

                    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
                    tvSunrise.text = sdf.format(Date(weather.sys.sunrise * 1000))
                    tvSunset.text = sdf.format(Date(weather.sys.sunset * 1000))

                    // Fetch forecast data
                    getForecast(city)
                } else {
                    Toast.makeText(this@home, "City not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Toast.makeText(this@home, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getForecast(city: String) {
        RetrofitClient.api.getForecast(
            city,
            "94fa22efd6b150c5a87bf4e96c4ac6fa"
        ).enqueue(object : Callback<ForecastResponse> {
            override fun onResponse(
                call: Call<ForecastResponse>,
                response: Response<ForecastResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val list = response.body()!!.list
                    if (list.size >= 40) {
                        updateForecastRow(list[0], tvTodayMax, tvTodayMin, imgToday)
                        updateForecastRow(list[8], tvTueMax, tvTueMin, imgTue)
                        updateForecastRow(list[16], tvWedMax, tvWedMin, imgWed)
                        updateForecastRow(list[24], tvThuMax, tvThuMin, imgThu)
                        updateForecastRow(list[32], tvFriMax, tvFriMin, imgFri)

                        setDayNames()
                    }
                }
            }

            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
            }
        })
    }

    private fun setDayNames() {
        val sdf = SimpleDateFormat("EEE", Locale.getDefault())
        val calendar = java.util.Calendar.getInstance()

        tvDay1.text = "Today"

        calendar.add(java.util.Calendar.DAY_OF_YEAR, 1)
        tvDay2.text = sdf.format(calendar.time)

        calendar.add(java.util.Calendar.DAY_OF_YEAR, 1)
        tvDay3.text = sdf.format(calendar.time)

        calendar.add(java.util.Calendar.DAY_OF_YEAR, 1)
        tvDay4.text = sdf.format(calendar.time)

        calendar.add(java.util.Calendar.DAY_OF_YEAR, 1)
        tvDay5.text = sdf.format(calendar.time)
    }

    private fun updateForecastRow(item: ForecastItem, tvMax: TextView, tvMin: TextView, img: ImageView) {
        tvMax.text = "${item.main.temp_max.toInt()}°C"
        tvMin.text = "${item.main.temp_min.toInt()}°C"
        val condition = item.weather[0].main
        when (condition) {
            "Clear" -> img.setImageResource(R.drawable.suniday)
            "Clouds", "Mist", "Haze", "Fog" -> img.setImageResource(R.drawable.cloud)
            "Rain", "Drizzle", "Snow" -> img.setImageResource(R.drawable.heavy_rain)
            "Thunderstorm" -> img.setImageResource(R.drawable.darkclod)
            else -> img.setImageResource(R.drawable.suniday)
        }
    }


}
