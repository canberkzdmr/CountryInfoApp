package com.canberkozdemir.kotlincountries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.canberkozdemir.kotlincountries.R
import com.canberkozdemir.kotlincountries.databinding.ItemCountryRowBinding
import com.canberkozdemir.kotlincountries.model.Country
import com.canberkozdemir.kotlincountries.util.downloadFromUrl
import com.canberkozdemir.kotlincountries.util.placeHolderProgressBar
import com.canberkozdemir.kotlincountries.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.item_country_row.view.*

class CountryAdapter(val countryList: ArrayList<Country>) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(), ICountryClickListener {

    class CountryViewHolder(var view: ItemCountryRowBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.item_country_row, parent, false)
        val view = DataBindingUtil.inflate<ItemCountryRowBinding>(inflater, R.layout.item_country_row, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

        holder.view.country = countryList[position]
        holder.view.listener = this

        /*holder.view.tvName.text = countryList[position].countryName.name

        countryList[position].countryCapital?.let {
            if (it.isNotEmpty())
                holder.view.tvCapital.text = it[0]
        }

        holder.view.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToDetailFragment()
            action.countryUuid = countryList[position].uuid
            Navigation.findNavController(it).navigate(action)
        }

        holder.view.ivCountry.downloadFromUrl(countryList[position].countryFlagUrl.countryFlagPng, placeHolderProgressBar(holder.view.context))
        */
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    fun updateCountryList(newCountryList: List<Country>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun onCountryClicked(v: View) {
        val action = FeedFragmentDirections.actionFeedFragmentToDetailFragment()
        action.countryUuid = v.tvUuid.text.toString().toInt()
        Navigation.findNavController(v).navigate(action)
    }
}