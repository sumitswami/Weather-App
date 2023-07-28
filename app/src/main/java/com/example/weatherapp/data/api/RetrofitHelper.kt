package com.example.weatherapp.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"


    private fun getInstance() :Retrofit{
         return Retrofit.Builder()
             .addConverterFactory(GsonConverterFactory.create())
             .baseUrl(BASE_URL)
             .build()
    }

    val result:ApiInterface = getInstance().create(ApiInterface::class.java)

}