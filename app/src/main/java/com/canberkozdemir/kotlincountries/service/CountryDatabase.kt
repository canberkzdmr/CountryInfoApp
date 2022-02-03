package com.canberkozdemir.kotlincountries.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.canberkozdemir.kotlincountries.model.Country

@Database(entities = arrayOf(Country::class), version = 1)
@TypeConverters(Converters::class)
abstract class CountryDatabase : RoomDatabase() {

    abstract fun countryDao(): CountryDAO

    //Singleton
    companion object {

        @Volatile
        private var instance: CountryDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CountryDatabase::class.java,
            "countrydatabase"
        ).build()
    }

}