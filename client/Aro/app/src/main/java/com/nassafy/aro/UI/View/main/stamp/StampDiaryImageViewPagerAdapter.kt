package com.nassafy.aro.ui.view.main.stamp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.nassafy.aro.R
import com.nassafy.aro.data.dto.CountryTest
import com.nassafy.aro.data.dto.PlaceDiaryTest
import com.nassafy.aro.databinding.StampDiaryImageListItemBinding
import com.squareup.picasso.Picasso

private const val TAG = "StampDiaryImageViewPage_싸피"

class StampDiaryImageViewPagerAdapter(
    private val placeDiaryImageList: List<Int>
) : RecyclerView.Adapter<StampDiaryImageViewPagerAdapter.StampDiaryPlaceHolder>() {
    private lateinit var binding: StampDiaryImageListItemBinding

    inner class StampDiaryPlaceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindInfo(data: CountryTest) {

        } // End of bindInfo
    } // End of StampDiaryPlaceHolder class

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StampDiaryPlaceHolder {
        binding = StampDiaryImageListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StampDiaryPlaceHolder(binding.root)
    } // End of onCreateViewHolder

    override fun onBindViewHolder(holder: StampDiaryPlaceHolder, position: Int) {
        Picasso.get().load(placeDiaryImageList[position]).fit().centerCrop().into(binding.stampDiaryImageListImageview)

        binding.stampDiaryHistoryImageDeleteButton.setOnClickListener {
            itemClickListener.imageRemoveButtonClick(position)
        }

    } // End of onBindViewHolder

    override fun getItemCount(): Int = placeDiaryImageList.size

    interface ItemClickListener {
        fun imageRemoveButtonClick(position: Int)
    } // End of ItemClickListener Interface

    private lateinit var itemClickListener: ItemClickListener

    //클릭리스너 등록 매소드
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    } // End of setItemClickListener
} // End of StampDiaryImageViewPagerAdapter class
