package com.example.weatherapp.ui.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.data.api.RetrofitHelper
import com.example.weatherapp.data.local.TempDataBase
import com.example.weatherapp.data.model.CardDescription
import com.example.weatherapp.data.model.Example
import com.example.weatherapp.data.model.entity.Temp
import com.example.weatherapp.data.repository.Repository
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.ui.adapter.Adapter
import com.example.weatherapp.ui.intent.MainIntent
import com.example.weatherapp.ui.viewmodel.MainViewModel
import com.example.weatherapp.ui.viewstate.MainState
import com.example.weatherapp.util.MainViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var db: TempDataBase = TempDataBase.getDatbase(this)
    lateinit var mainViewModel: MainViewModel
    val descriptionObjects : MutableList<CardDescription> = mutableListOf<CardDescription>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) //initializing the binding class
        setContentView(binding.root)
        /* database = Room.databaseBuilder(applicationContext,
            TempDatabase::class.java,
            "temperatureTable ").build()*/

       /* val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)*/

       // db = TempDataBase.getDatbase(this)

        Log.d("logtesting","log1")
        Log.e("logtesting","log12")
        Log.w("logtesting","log123")
        Log.i("logtesting","log1234")
        Log.d("logtesting","log12345")


       // val result = RetrofitHelper.getInstance().create(ApiInterface::class.java)
       // val repository = Repository(db,RetrofitHelper.result)
        // mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)

        //fetchWeatherData()

        setupUI()
        setupViewModel()
        GlobalScope.launch {
            observeViewModel()
        }
    }

    private fun setupUI() {
        binding.itemList.adapter = Adapter(descriptionObjects)
        binding.itemList.layoutManager = LinearLayoutManager(this@MainActivity ,LinearLayoutManager.HORIZONTAL,false)
    }

    private fun setupViewModel() {
        val repository =Repository(db,RetrofitHelper.result)
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)

    }

    private suspend fun observeViewModel() {
        mainViewModel.state.collect {
            when(it) {
                is MainState.Idle -> {

                }
                is MainState.Loading -> {

                }
                is MainState.CardDescription-> {
                    val response = RetrofitHelper.result.getWeatherData("Bangalore", "8118ed6ee68db2debfaaa5a44c832918" , "metric")

                    response.enqueue(object : Callback<Example> {
                        override fun onResponse(call: Call<Example>, response: Response<Example>) {
                            val responseBody = response.body()

                            if (responseBody != null) {
                                binding.temp.text = responseBody.main.temp.toString()
                                binding.address.text = responseBody.name
                                val updatedAt:Long = responseBody.dt
                                val dateString = SimpleDateFormat("dd/MM/yyyy hh:mm a",
                                    Locale.ENGLISH).format(Date(updatedAt*1000))
                                binding.updatedat.text = "Updated at: " + dateString
                                binding.status.text = responseBody.weather[0].description
                                binding.tempmin.text = responseBody.main.temp_min.toString()
                                binding.tempmax.text = responseBody.main.temp_max.toString()
                                val sunrise:Long = responseBody.sys.sunrise
                                val sunset:Long = responseBody.sys.sunset


                                descriptionObjects.add(CardDescription("Sunrise",SimpleDateFormat("hh:mm a",Locale.ENGLISH).format(Date(sunrise*1000))))
                                descriptionObjects.add(CardDescription("Sunset",SimpleDateFormat("hh:mm a",Locale.ENGLISH).format(Date(sunset*1000))))
                                descriptionObjects.add(CardDescription("humidity",responseBody.main.humidity.toString()))
                                descriptionObjects.add(CardDescription("Pressure",responseBody.main.pressure.toString()))
                                descriptionObjects.add(CardDescription("Wind",responseBody.wind.speed.toString()))
                                descriptionObjects.add(CardDescription("Feels Like",responseBody.main.feels_like.toString()))


                                /*findViewById<TextView>(R.id.sunrise).text = SimpleDateFormat("hh:mm a",Locale.ENGLISH).format(Date(sunrise*1000))
                                findViewById<TextView>(R.id.sunset).text = SimpleDateFormat("hh:mm a",Locale.ENGLISH).format(Date(sunset*1000))
                                findViewById<TextView>(R.id.wind).text = responseBody.wind.speed.toString()
                                findViewById<TextView>(R.id.pressure).text = responseBody.main.pressure.toString()
                                findViewById<TextView>(R.id.humidity).text = responseBody.main.humidity.toString()
                                findViewById<TextView>(R.id.feelslike).text = responseBody.main.feels_like.toString()*/

                                GlobalScope.launch {
                                    db.tempDao().insertData(Temp(dateString,responseBody.main.temp_min,responseBody.main.temp_max,responseBody.main.humidity))
                                }

                            }
                        }

                        override fun onFailure(call: Call<Example>, t: Throwable) {
                            Log.d("DATA",t.toString())
                        }

                    })
                }
                is MainState.Error -> {
                    Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

  /*  private fun fetchWeatherData() {

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
    }*/

}



