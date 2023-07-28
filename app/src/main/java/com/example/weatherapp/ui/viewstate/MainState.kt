package com.example.weatherapp.ui.viewstate

sealed class MainState{

    object Idle:MainState()
    object Loading:MainState()
    data class CardDescription(val Cards: Unit) :MainState()
    data class Error(val error: String) :MainState()

}
