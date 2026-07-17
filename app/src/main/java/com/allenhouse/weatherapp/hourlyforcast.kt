package com.allenhouse.weatherapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

class hourlyforcast : AppCompatActivity() {

    private lateinit var mainLayout: LinearLayout
    private lateinit var tvTime1 : TextView
    private lateinit var tvTemp1 : TextView
    private lateinit var imgHour1 : ImageView

    private lateinit var tvTime2 : TextView
    private lateinit var tvTemp2 : TextView
    private lateinit var imgHour2 : ImageView

    private lateinit var tvTime3 : TextView
    private lateinit var tvTemp3 : TextView
    private lateinit var imgHour3 : ImageView

    private lateinit var tvTime4 : TextView
    private lateinit var tvTemp4 : TextView
    private lateinit var imgHour4 : ImageView

    private lateinit var tvTime5 : TextView
    private lateinit var tvTemp5 : TextView
    private lateinit var imgHour5 : ImageView

    private lateinit var tvTime6 : TextView
    private lateinit var tvTemp6 : TextView
    private lateinit var imgHour6 : ImageView

    private lateinit var tvTime7 : TextView
    private lateinit var tvTemp7 : TextView
    private lateinit var imgHour7 : ImageView

    private lateinit var tvTime8 : TextView
    private lateinit var tvTemp8 : TextView
    private lateinit var imgHour8 : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hourlyforcast)

        mainLayout = findViewById(R.id.main)
        tvTime1 = findViewById(R.id.tvTime1)
        tvTemp1 = findViewById(R.id.tvTemp1)
        imgHour1 = findViewById(R.id.imgHour1)

        tvTime2 = findViewById(R.id.tvTime2)
        tvTemp2 = findViewById(R.id.tvTemp2)
        imgHour2 = findViewById(R.id.imgHour2)

        tvTime3 = findViewById(R.id.tvTime3)
        tvTemp3 = findViewById(R.id.tvTemp3)
        imgHour3 = findViewById(R.id.imgHour3)

        tvTime4 = findViewById(R.id.tvTime4)
        tvTemp4 = findViewById(R.id.tvTemp4)
        imgHour4 = findViewById(R.id.imgHour4)

        tvTime5 = findViewById(R.id.tvTime5)
        tvTemp5 = findViewById(R.id.tvTemp5)
        imgHour5 = findViewById(R.id.imgHour5)

        tvTime6 = findViewById(R.id.tvTime6)
        tvTemp6 = findViewById(R.id.tvTemp6)
        imgHour6 = findViewById(R.id.imgHour6)

        tvTime7 = findViewById(R.id.tvTime7)
        tvTemp7 = findViewById(R.id.tvTemp7)
        imgHour7 = findViewById(R.id.imgHour7)

        tvTime8 = findViewById(R.id.tvTime8)
        tvTemp8 = findViewById(R.id.tvTemp8)
        imgHour8 = findViewById(R.id.imgHour8)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val city = if (intent.hasExtra("CITY_NAME")) {intent.getStringExtra("CITY_NAME")
        } else{
            intent.getStringExtra("USER_LOCATION") } ?: "Delhi"
        getForecast(city)
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

                    if (list.isNotEmpty()) {
                        val condition = list[0].weather[0].main
                        when (condition) {
                            "Clear" -> mainLayout.setBackgroundResource(R.drawable.bggradient)
                            "Rain", "Drizzle", "Thunderstorm" -> mainLayout.setBackgroundResource(R.drawable.bggradientdark)
                            else -> mainLayout.setBackgroundResource(R.drawable.bggradient)
                        }
                    }

                    if (list.size >= 8) {
                        updateHour(list[0], tvTime1, tvTemp1, imgHour1)
                        updateHour(list[1], tvTime2, tvTemp2, imgHour2)
                        updateHour(list[2], tvTime3, tvTemp3, imgHour3)
                        updateHour(list[3], tvTime4, tvTemp4, imgHour4)
                        updateHour(list[4], tvTime5, tvTemp5, imgHour5)
                        updateHour(list[5], tvTime6, tvTemp6, imgHour6)
                        updateHour(list[6], tvTime7, tvTemp7, imgHour7)
                        updateHour(list[7], tvTime8, tvTemp8, imgHour8)
                    }
                }
            }

            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun updateHour(
        item: ForecastItem,
        tvTime: TextView,
        tvTemp: TextView,
        imgHour: ImageView
    ) {
        val input = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val output = SimpleDateFormat("hh a", Locale.getDefault())

        val date = try {
            input.parse(item.dt_txt)
        } catch (e: Exception) {
            null
        }

        tvTime.text = if (date != null) output.format(date) else "--"

        val hour = date?.let {
            val cal = java.util.Calendar.getInstance()
            cal.time = it
            cal.get(java.util.Calendar.HOUR_OF_DAY)
        } ?: 0

        val isNight = hour >= 18 || hour < 6
        tvTemp.text = "${item.main.temp.toInt()}°C"

        when (item.weather[0].main) {
            "Clear" -> {
                if (isNight) {
                    imgHour.setImageResource(R.drawable.half_moon_svgrepo_com)
                } else {
                    imgHour.setImageResource(R.drawable.suniday)
                }
            }
            "Clouds" -> {
                if (isNight) {
                    imgHour.setImageResource(R.drawable.hail_halfmoon_svgrepo_com)
                } else {
                    imgHour.setImageResource(R.drawable.cloud)
                }
            }
            "Rain", "Drizzle", "Thunderstorm" -> {
                if (isNight) {
                    imgHour.setImageResource(R.drawable.hail_halfmoon_svgrepo_com)
                } else {
                    if (item.weather[0].main == "Thunderstorm") {
                        imgHour.setImageResource(R.drawable.darkclod)
                    } else {
                        imgHour.setImageResource(R.drawable.heavy_rain)
                    }
                }
            }
            else -> {
                if (isNight) {
                    imgHour.setImageResource(R.drawable.half_moon_svgrepo_com)
                } else {
                    imgHour.setImageResource(R.drawable.suniday)
                }
            }
        }
    }
}
