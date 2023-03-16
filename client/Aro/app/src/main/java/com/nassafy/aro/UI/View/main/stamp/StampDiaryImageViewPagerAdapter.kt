package com.nassafy.aro.ui.view.main.stamp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nassafy.aro.data.dto.Country
import com.nassafy.aro.databinding.StampDiaryImageListItemBinding

private const val TAG = "StampDiaryImageViewPage_싸피"

class StampDiaryImageViewPagerAdapter(
    private val diaryImageList: List<Country>
) : RecyclerView.Adapter<StampDiaryImageViewPagerAdapter.StampDiaryPlaceHolder>() {
    private lateinit var binding: StampDiaryImageListItemBinding

    inner class StampDiaryPlaceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindInfo(data: Country) {
            binding.stampDiaryImageListImageview.setImageResource(data.image)
        } // End of bindInfo
    } // End of StampDiaryPlaceHolder class

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StampDiaryPlaceHolder {
        binding = StampDiaryImageListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StampDiaryPlaceHolder(binding.root)
    } // End of onCreateViewHolder

    override fun onBindViewHolder(holder: StampDiaryPlaceHolder, position: Int) {
        val viewHolder: StampDiaryPlaceHolder = holder
        viewHolder.bindInfo(diaryImageList[position])
    } // End of onBindViewHolder

    override fun getItemCount(): Int = diaryImageList.size
} // End of StampDiaryImageViewPagerAdapter class
