package com.nassafy.aro.UI.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nassafy.aro.R
import com.nassafy.aro.databinding.BottomSheetRecyclerviewItemBinding

class BottomSheetFavoriteAdapter(var itemList: MutableList<MutableList<String>>) :
    RecyclerView.Adapter<BottomSheetFavoriteAdapter.BottomSheetFavoriteViewHolder>() {
    private lateinit var binding: BottomSheetRecyclerviewItemBinding

    inner class BottomSheetFavoriteViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bindInfo(item: MutableList<String>) {

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BottomSheetFavoriteViewHolder {
        binding = BottomSheetRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context))
        return BottomSheetFavoriteViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: BottomSheetFavoriteViewHolder, position: Int) {
        holder.bindInfo(itemList[position])

        val view = holder.itemView
        var mColor = R.color.over_70
        var mImage = R.drawable.over_triangle
        when {
            itemList[position][1].toInt() >= 70 -> {

            }
            itemList[position][1].toInt() >= 50 -> {
                mColor = R.color.over_50
            }
            itemList[position][1].toInt() < 50 -> {
                mColor = R.color.under_50
                mImage = R.drawable.under_triangle
            }
            itemList[position][1].toInt() < 20 -> {
                mColor = R.color.under_20
                mImage = R.drawable.under_triangle
            }
        }

        view.findViewById<TextView>(R.id.bottom_sheet_item_name_textview).text =
            itemList[position][0]

        view.findViewById<TextView>(R.id.bottom_sheet_item_percent_textview).apply {
            text = "${itemList[position][1]}%"
            setTextColor(ContextCompat.getColor(context, mColor))
        }

        view.findViewById<ImageView>(R.id.bottom_sheet_item_updown_imageview).apply {
            setImageResource(mImage)
            setColorFilter(ContextCompat.getColor(context, mColor))
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}