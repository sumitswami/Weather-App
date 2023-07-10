package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*


const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchWeatherData()
    }

    private fun fetchWeatherData() {

        val updtdAt = "Updated at: "

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        Log.d("logtesting","log1")
        Log.e("logtesting","log12")
        Log.w("logtesting","log123")
        Log.i("logtesting","log1234")
        Log.d("logtesting","log12345")



        val response = retrofit.getWeatherData("Bangalore", "8118ed6ee68db2debfaaa5a44c832918" , "metric")

        response.enqueue(object : Callback<Example> {
            override fun onResponse(call: Call<Example>, response: Response<Example>) {
                val responseBody = response.body()

                if (responseBody != null) {
                    findViewById<TextView>(R.id.temp).text = responseBody.main.temp.toString()
                    findViewById<TextView>(R.id.address).text = responseBody.name
                    val updatedAt:Long = responseBody.dt
                    val dateString = SimpleDateFormat("dd/MM/yyyy hh:mm a",
                        Locale.ENGLISH).format(Date(updatedAt*1000))
                    findViewById<TextView>(R.id.updated_at).text = updtdAt + dateString
                    findViewById<TextView>(R.id.status).text = responseBody.weather[0].description
                    findViewById<TextView>(R.id.temp_min).text = responseBody.main.temp_min.toString()
                    findViewById<TextView>(R.id.temp_max).text = responseBody.main.temp_max.toString()
                    val sunrise:Long = responseBody.sys.sunrise
                    val sunset:Long = responseBody.sys.sunset
                    findViewById<TextView>(R.id.sunrise).text = SimpleDateFormat("hh:mm a",Locale.ENGLISH).format(Date(sunrise*1000))
                    findViewById<TextView>(R.id.sunset).text = SimpleDateFormat("hh:mm a",Locale.ENGLISH).format(Date(sunset*1000))
                    findViewById<TextView>(R.id.wind).text = responseBody.wind.speed.toString()
                    findViewById<TextView>(R.id.pressure).text = responseBody.main.pressure.toString()
                    findViewById<TextView>(R.id.humidity).text = responseBody.main.humidity.toString()
                    findViewById<TextView>(R.id.feelslike).text = responseBody.main.feels_like.toString()
                }
            }

            override fun onFailure(call: Call<Example>, t: Throwable) {
                Log.d("DATA",t.toString())
            }

        })
    }

}



