package com.canberkozdemir.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.canberkozdemir.kotlincountries.R
import com.canberkozdemir.kotlincountries.util.downloadFromUrl
import com.canberkozdemir.kotlincountries.util.placeHolderProgressBar
import com.canberkozdemir.kotlincountries.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    private lateinit var viewModel : DetailViewModel

    private var countryUuid = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid = DetailFragmentArgs.fromBundle(it).countryUuid
        }
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.getDataFromRoom(countryUuid)

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->
            country?.let {
                tvCountryName.text = country.countryName.name
                country.countryCapital?.get(0)?.let {
                    tvCountryCapital.text = country.countryCapital[0]
                }
                tvCountryCurrency.text = country.countryCurrency.values.toList()[0].currencyName.toString()
                tvCountryLanguage.text = country.countryLanguage.values.toList()[0]
                tvCountryRegion.text = country.countryRegion
                context?.let {
                    ivCountryFlag.downloadFromUrl(country.countryFlagUrl.countryFlagPng, placeHolderProgressBar(it))
                }
            }
        })
    }

}