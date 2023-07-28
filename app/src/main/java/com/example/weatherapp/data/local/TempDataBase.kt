package com.example.weatherapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.weatherapp.data.model.entity.Temp

@Database(entities = [Temp::class],version=2)
abstract class TempDataBase :RoomDatabase(){

    abstract fun tempDao(): TempDAO

    companion object{

        val migration_1_2 = object : Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE 'Temperature Table' ADD COLUMN humidity INTEGER NOT NULL DEFAULT 0")
            } }

        @Volatile
        private var Instance: TempDataBase? = null

        fun getDatbase(context: Context) : TempDataBase {
            if (Instance ==null) {
                synchronized(this){
                    Instance = Room.databaseBuilder(context.applicationContext,
                        TempDataBase::class.java,
                        "TempDB")
                        .addMigrations(migration_1_2)
                        .build()
                }

            }
            return Instance!!
        }
    }

}