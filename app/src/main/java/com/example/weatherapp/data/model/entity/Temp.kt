package com.example.weatherapp.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Temperature Table")
class Temp(
    val date: String,
    val minTemp: Double,
    val maxTemp: Double,
    val humidity: Int


    ) {

    @PrimaryKey(autoGenerate = true)
    var id:Int = 0


}
