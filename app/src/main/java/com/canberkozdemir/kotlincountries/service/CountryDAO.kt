package com.canberkozdemir.kotlincountries.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.canberkozdemir.kotlincountries.model.Country

@Dao
interface CountryDAO {

    //Data Access Object

    //Insert -> INSERT INTO
    //suspend -> coroutine, pause&resume
    //vararg -> bir objeyi farkli sayilarla verebilmek icin gerekli keyword
    //List<Long> -> primary keys
    @Insert
    suspend fun insertAll(vararg countries: Country) : List<Long>

    @Query("SELECT * FROM country")
    suspend fun getAllCountries() : List<Country>

    @Query("SELECT * FROM country WHERE uuid = :countryId")
    suspend fun getCountry(countryId : Int) : Country

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()
}