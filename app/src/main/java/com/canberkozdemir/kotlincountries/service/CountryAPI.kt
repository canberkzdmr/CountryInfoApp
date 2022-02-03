package com.canberkozdemir.kotlincountries.service

import com.canberkozdemir.kotlincountries.model.Country
import io.reactivex.Single
import retrofit2.http.GET

interface CountryAPI {

    //GET, POST
    //BASE_URL = https://restcountries.com/
    //GET ALL = v3.1/all

//    @GET("v3.1/all?fields=name,capital,currencies,languages,region,flags,timezones")
    @GET("v3.1/all?fields=name,capital,currencies,languages,region,flags,timezones")
    fun getAllCountries(): Single<List<Country>>

}