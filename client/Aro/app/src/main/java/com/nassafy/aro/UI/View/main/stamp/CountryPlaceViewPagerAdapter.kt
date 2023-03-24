package com.nassafy.aro.ui.view.main.stamp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.nassafy.aro.data.dto.CountryTest
import com.nassafy.aro.data.dto.UserStampPlace
import com.nassafy.aro.databinding.StampCountryPlaceListItemBinding
import com.squareup.picasso.Picasso

private const val TAG = "CountryPlaceViewPagerAd_싸피"

class CountryPlaceViewPagerAdapter(
    private val countryName: String,
    private val countryPlaceList: List<UserStampPlace>
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
        binding.stampCountryPlaceNameTextview.text = countryPlaceList[position].attractionName.toString()
        Picasso.get().load(countryPlaceList[position].attractionName!!.toUri()).fit().centerCrop()
            .into(binding.stampCountryPlaceImageview)

        binding.stampCountryPlaceInformTextview.text =
            countryPlaceList[position].description.toString()

        // 일기 작성 버튼 클릭 이벤트 리스너
        binding.stampCountryPlaceWriteDiaryButton
            .setOnClickListener {
                itemClickListener.writeDiaryButtonClick(position)
            }


        // 인증 하기 버튼 클릭 이벤트 리스너
        binding.stampCountryPlaceWriteDiaryButton.setOnClickListener {
            itemClickListener.validateButtonclick(position)
        }
    } // End of onBindViewHolder

    override fun getItemCount(): Int = countryPlaceList.size

    private lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener {
        fun writeDiaryButtonClick(position: Int)
        fun validateButtonclick(position: Int)
    } // End of ItemClickListener interface

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    } // End of setItemClickListener
} // End of CountryPlaceViewPagerAdapter class
