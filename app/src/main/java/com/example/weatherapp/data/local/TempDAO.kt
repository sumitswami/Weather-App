package com.example.weatherapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.example.weatherapp.data.model.entity.Temp

@Dao
interface TempDAO {

    @Insert
    suspend fun insertData(temp: Temp)

    @Update
    suspend fun updateData(temp: Temp)

    @Delete
    suspend fun deleteData(temp: Temp)
}