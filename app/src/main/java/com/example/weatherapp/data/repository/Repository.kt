package com.example.weatherapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.data.model.Example
import com.example.weatherapp.data.local.TempDataBase
import com.example.weatherapp.data.api.ApiInterface


//Repository talks with external data sources such as api and database
class Repository (private val tempDao: TempDataBase, private val apiservice: ApiInterface){


    suspend fun getWeatherData(city:String,appId:String,units:String) {
        val response = apiservice.getWeatherData(city,appId,units)
        //if (response!=null && response.body.string() !=null)
    }
}