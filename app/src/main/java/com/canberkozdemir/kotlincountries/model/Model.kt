package com.canberkozdemir.kotlincountries.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity()
data class Country(
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val countryName: CountryName,
    @ColumnInfo(name = "capital")
    @SerializedName("capital")
    val countryCapital: List<String>?,
    @ColumnInfo(name = "currency")
    @SerializedName("currencies")
    val countryCurrency: Map<String, CountryCurrencies>,
    @ColumnInfo(name = "region")
    @SerializedName("region")
    val countryRegion: String?,
    @ColumnInfo(name = "language")
    @SerializedName("languages")
    val countryLanguage: CountryLanguages,
    @ColumnInfo(name = "timezone")
    @SerializedName("timezones")
    val countryTimeZone: List<String>?,
    @ColumnInfo(name = "flag")
    @SerializedName("flags")
    val countryFlagUrl: CountryFlag
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}

data class CountryName(
    @SerializedName("common")
    val name: String?
)

/*data class CountryCurrencies(
    @SerializedName("PGK")
    val currencySubObject: CountryCurrencySub
)*/

data class CountryCurrencies(
    @SerializedName("name")
    val currencyName: String?
)

data class CountryCurrencySub(
    @SerializedName("name")
    val currencyName: String?
)

data class CountryLanguages(
    @SerializedName("eng")
    val eng: String?
)

data class CountryFlag(
    @SerializedName("png")
    val countryFlagPng: String?
)
