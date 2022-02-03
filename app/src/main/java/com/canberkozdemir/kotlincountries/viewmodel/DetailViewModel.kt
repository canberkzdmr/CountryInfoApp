package com.canberkozdemir.kotlincountries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.canberkozdemir.kotlincountries.model.Country

class DetailViewModel : ViewModel() {
    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom() {
//        val country = Country("Turkey", "Ankara", "Asia", "TRY", "test.com", "Turkish")
//        val country = Country("Ankara")
//        countryLiveData.value = country
    }
}