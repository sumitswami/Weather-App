package com.example.weatherapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.repository.Repository
import com.example.weatherapp.ui.intent.MainIntent
import com.example.weatherapp.ui.viewstate.MainState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MainViewModel(private  val repository: Repository) : ViewModel() {

    val userIntent=  Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state: StateFlow<MainState>
    get() = _state

    init {
       handleIntent()
    }


    private fun handleIntent(){

        viewModelScope.launch {
            userIntent.consumeAsFlow().collect(){
                when (it) {
                    is MainIntent.FetchData -> getWeatherData()
                }
            }
        }
    }

    private fun getWeatherData() {
        viewModelScope.launch {
            _state.value = MainState.Loading
            _state.value = try{
                MainState.CardDescription(repository.getWeatherData("Bangalore","8118ed6ee68db2debfaaa5a44c832918","metric"))
            }
            catch (e: java.lang.Exception) {
                MainState.Error(e.localizedMessage)
            }
        }
    }

}