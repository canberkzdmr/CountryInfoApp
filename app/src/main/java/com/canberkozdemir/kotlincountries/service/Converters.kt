package com.canberkozdemir.kotlincountries.service

import androidx.room.TypeConverter
import com.canberkozdemir.kotlincountries.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.*

class Converters {
    /**
     * Flag Model Converters
     */
    //CountryName
    @TypeConverter
    fun fromCountryName(countryName: CountryName): String {
        return countryName.name.toString()
    }
    @TypeConverter
    fun toCountryName(countryName: String): CountryName {
        return CountryName(countryName)
    }

    //CountryCapital
    @TypeConverter
    fun fromCountryCapital(countryCapital: List<String>) = Json.encodeToString(countryCapital)
    @TypeConverter
    fun toCountryCapital(countryCapital: String) =
        Json.decodeFromString<List<String>>(countryCapital)

    //CountryCurrency
    @TypeConverter
    fun fromCountryCurrency(map: Map<String, CountryCurrencies>): String {
        return if (!map.values.isEmpty()) {
            map.values.toList()[0].currencyName.toString()
        } else {
            "There is no currency info retrieved from the service!"
        }
    }
    @TypeConverter
    fun toCountryCurrency(value: String): Map<String, CountryCurrencies> {
        val mapType = object : TypeToken<Map<String, CountryCurrencies>>() {}.type
        return Gson().fromJson(value, mapType)
    }


    //CountryLanguage
    @TypeConverter
    fun fromCountryLanguage(countryLanguage: CountryLanguages): String {
        return countryLanguage.eng.toString()
    }
    @TypeConverter
    fun toCountryLanguage(countryLanguage: String): CountryLanguages {
        return CountryLanguages(countryLanguage)
    }

    //CountryFlag
    @TypeConverter
    fun fromCountryFlag(countryFlag: CountryFlag): String {
        return countryFlag.countryFlagPng.toString()
    }
    @TypeConverter
    fun toCountryFlag(countryFlag: String): CountryFlag {
        return CountryFlag(countryFlag)
    }

}