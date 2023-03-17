package com.nassafy.aro.ui.view.main.stamp

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintSet.Transform
import androidx.recyclerview.widget.RecyclerView
import com.nassafy.aro.R
import com.nassafy.aro.data.dto.Country
import com.nassafy.aro.databinding.StampDiaryImageListItemBinding
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import jp.wasabeef.picasso.transformations.CropTransformation

private const val TAG = "StampDiaryImageViewPage_싸피"

class StampDiaryImageViewPagerAdapter(
    private val diaryImageList: List<Country>
) : RecyclerView.Adapter<StampDiaryImageViewPagerAdapter.StampDiaryPlaceHolder>() {
    private lateinit var binding: StampDiaryImageListItemBinding

    inner class StampDiaryPlaceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindInfo(data: Country) {

        } // End of bindInfo
    } // End of StampDiaryPlaceHolder class

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StampDiaryPlaceHolder {
        binding = StampDiaryImageListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StampDiaryPlaceHolder(binding.root)
    } // End of onCreateViewHolder

    override fun onBindViewHolder(holder: StampDiaryPlaceHolder, position: Int) {
        binding.stampDiaryImageListImageview.setImageResource(diaryImageList[position].image)

        val imageView =
            holder.itemView.findViewById<ImageView>(R.id.stamp_diary_image_list_imageview)
        Picasso.get().load(diaryImageList[position].image).fit().centerCrop().into(imageView)

        holder.itemView.setOnClickListener {
            itemClickListener.imageRemoveButtonClick(position)
        }
    } // End of onBindViewHolder

    override fun getItemCount(): Int = diaryImageList.size

    interface ItemClickListener {
        fun imageRemoveButtonClick(position: Int)
    } // End of ItemClickListener Interface

    private lateinit var itemClickListener: ItemClickListener

    //클릭리스너 등록 매소드
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    } // End of setItemClickListener
} // End of StampDiaryImageViewPagerAdapter class
