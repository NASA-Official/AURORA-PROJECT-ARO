package com.nassafy.aro.ui.view.main.stamp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nassafy.aro.R
import com.nassafy.aro.data.dto.Country
import com.nassafy.aro.databinding.StampCountryPlaceListItemBinding

private const val TAG = "CountryPlaceViewPagerAd_싸피"

class CountryPlaceViewPagerAdapter(
    private val countryPlaceList: List<Country>
) : RecyclerView.Adapter<CountryPlaceViewPagerAdapter.CountryPlaceHolder>() { // End of CountryPlaceViewPagerAdapter class
    private lateinit var binding: StampCountryPlaceListItemBinding

    inner class CountryPlaceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindInfo(data: Country) {
            binding.stampCountryNameTextview.text = data.countryName.toString()
            binding.stampCountryPlaceNameTextview.text = data.placeName.toString()
            binding.stampCountryPlaceImageview.setImageResource(data.image)
            binding.stampCountryPlaceInformTextview.text = data.countryDetail.toString()
        } // End of bindInfo
    } // End of CountryPlaceHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryPlaceHolder {
        binding = StampCountryPlaceListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CountryPlaceHolder(binding.root)
    } // End of onCreateViewHolder

    override fun onBindViewHolder(holder: CountryPlaceHolder, position: Int) {
        val viewHolder: CountryPlaceHolder = holder
        viewHolder.bindInfo(countryPlaceList[position])
    } // End of onBindViewHolder

    override fun getItemCount(): Int = countryPlaceList.size
} // End of CountryPlaceViewPagerAdapter class
