package com.example.weatherapp.util

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel
import com.example.weatherapp.ui.viewmodel.MainViewModel
import com.example.weatherapp.data.repository.Repository


//since we have made a parametrized view model therefore we need to define a viewmodel factory
// params are reference of the viewmodel
class MainViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }




}