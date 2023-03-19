package com.nassafy.aro.ui.view.main.stamp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nassafy.aro.data.dto.CountryTest
import com.nassafy.aro.data.dto.PlaceTest
import com.nassafy.aro.databinding.StampCountryPlaceListItemBinding

private const val TAG = "CountryPlaceViewPagerAd_싸피"

class CountryPlaceViewPagerAdapter(
    private val countryName: String,
    private val countryPlaceList: List<PlaceTest>
) : RecyclerView.Adapter<CountryPlaceViewPagerAdapter.CountryPlaceHolder>() {
    private lateinit var binding: StampCountryPlaceListItemBinding

    inner class CountryPlaceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindInfo(data: CountryTest) {
//            binding.stampCountryNameTextview.text = data.countryName.toString()
//            binding.stampCountryPlaceNameTextview.text = data.placeName.toString()
//            binding.stampCountryPlaceImageview.setImageResource(data.image)
//            binding.stampCountryPlaceInformTextview.text = data.countryDetail.toString()
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
        binding.stampCountryNameTextview.text = countryName
        binding.stampCountryPlaceNameTextview.text = countryPlaceList[position].placeName.toString()
        binding.stampCountryPlaceImageview.setImageResource(countryPlaceList[position].placeImage)
        binding.stampCountryPlaceInformTextview.text =
            countryPlaceList[position].placeExplanation.toString()

        binding.stampCountryCustomMakeDiaryButton
            .setOnClickListener {
                itemClickListener.writeDiaryButtonClick(position)
            }
    } // End of onBindViewHolder

    override fun getItemCount(): Int = countryPlaceList.size

    private lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener {
        fun writeDiaryButtonClick(position: Int)
    } // End of ItemClickListener interface

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    } // End of setItemClickListener
} // End of CountryPlaceViewPagerAdapter class
