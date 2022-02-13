package com.canberkozdemir.kotlincountries.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.canberkozdemir.kotlincountries.model.Country
import com.canberkozdemir.kotlincountries.service.CountryAPIService
import com.canberkozdemir.kotlincountries.service.CountryDatabase
import com.canberkozdemir.kotlincountries.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application) {
    private val countryAPIService = CountryAPIService()
    private val disposable = CompositeDisposable()
    private var customPrefences = CustomSharedPreferences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoadingProgress = MutableLiveData<Boolean>()

    fun refreshData() {
        val updateTime = customPrefences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime)
            getDataFromSQLite()
        else
            getDataFromAPI()
    }

    private fun getDataFromSQLite() {
        countryLoadingProgress.value = true
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(), "Countries retrieved from local!", Toast.LENGTH_LONG).show()
        }
    }

    fun refreshDataFromAPI() {
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
                        Toast.makeText(getApplication(), "Countries retrieved online!", Toast.LENGTH_LONG).show()
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
            val countryListLong =
                dao.insertAll(*countryList.toTypedArray()) // -> individual, list'ten tek tek diziye atiyor kotline ozel bu
            var i = 0
            while (i < countryList.size) {
                countryList[i].uuid = countryListLong[i].toInt()
                i += 1
            }
            showCountries(countryList)
        }

        customPrefences.saveTime(System.nanoTime())
    }

    /**
     * hafizayi verimli kullanmak icin temizlemek gerekiyor
     */
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}