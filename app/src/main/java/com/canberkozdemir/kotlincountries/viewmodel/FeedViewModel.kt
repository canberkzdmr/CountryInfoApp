package com.canberkozdemir.kotlincountries.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.canberkozdemir.kotlincountries.model.Country
import com.canberkozdemir.kotlincountries.service.CountryAPIService
import com.canberkozdemir.kotlincountries.service.CountryDatabase
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application) {
    private val countryAPIService = CountryAPIService()
    private val disposable = CompositeDisposable()

    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoadingProgress = MutableLiveData<Boolean>()

    fun refreshData() {
        getDataFromAPI()
    }

    private fun getDataFromAPI() {
        countryLoadingProgress.value = true

        disposable.add(
            countryAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(t: List<Country>) {
                        storeInSQLite(t)
                    }

                    override fun onError(e: Throwable) {
                        countryLoadingProgress.value = false
                        countryError.value = true
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun showCountries(countryList: List<Country>) {
        countryLoadingProgress.value = false
        countryError.value = false
        countries.value = countryList
    }

    private fun storeInSQLite(countryList: List<Country>) {
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val countryListLong = dao.insertAll(*countryList.toTypedArray()) // -> individual, list'ten tek tek diziye atiyor kotline ozel bu
            var i = 0
            while (i < countryList.size) {
                countryList[i].uuid = countryListLong[i].toInt()
                i += 1
            }
            showCountries(countryList)
        }
    }

}