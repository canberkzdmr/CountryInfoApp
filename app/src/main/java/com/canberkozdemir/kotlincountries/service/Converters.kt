package com.canberkozdemir.kotlincountries.service

import androidx.room.TypeConverter
import com.canberkozdemir.kotlincountries.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

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
    fun fromCountryCapital(countryCapital: List<String>): String {
        return if (!countryCapital.isNullOrEmpty())
            countryCapital[0]
        else
            ""
    }

    @TypeConverter
    fun toCountryCapital(countryCapital: String): List<String> {
        return listOf(countryCapital)
    }

    //CountryCurrency
    @TypeConverter
    fun fromCountryCurrency(map: Map<String, CountryCurrencies>): String {
        return if (!map.values.isEmpty()) {
            map.values.toList()[0].currencyName.toString()
        } else {
            ""
        }
    }

    @TypeConverter
    fun toCountryCurrency(value: String): Map<String, CountryCurrencies> =
        Gson().fromJson(value, object : TypeToken<Map<String, CountryCurrencies>>() {}.type)

    //CountryLanguage
    @TypeConverter
    fun fromCountryLanguage(countryLanguage: Map<String, String>): String {
        return if (!countryLanguage.values.isNullOrEmpty())
            countryLanguage.values.toList()[0]
        else
            ""
    }

    @TypeConverter
    fun toCountryLanguage(countryLanguage: String): Map<String, String> {
        val mapType = object : TypeToken<Map<String, String>>() {}.type
        return Gson().fromJson(countryLanguage, mapType)
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