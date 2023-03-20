package com.nassafy.aro.ui.view.custom

import android.content.Context
import android.graphics.BitmapFactory
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import com.nassafy.aro.R
import com.nassafy.aro.databinding.AroServiceCardviewLayoutBinding
import com.nassafy.aro.util.convertImageToBlurImage
import com.nassafy.aro.util.setImageWithGrayScale


class AroServiceCardView @JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0)  :  LinearLayoutCompat(context, attrs, defStyleAttr) {

    init {
        // CustomView viewBinding
        val binding = AroServiceCardviewLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        val view = binding.root

        val styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.AroServiceCardView, defStyleAttr, 0)
        val resourceId = styledAttributes.getResourceId(R.styleable.AroServiceCardView_service_image, 0)

        //TODO 이미지 블러 최적화
        //get Blur 이미지
        val blurImage = convertImageToBlurImage(context, BitmapFactory.decodeResource(resources, resourceId))
        setImageWithGrayScale(blurImage, binding.serviceImageview)
        styledAttributes.recycle()

        view.setOnClickListener {
            when(view.isSelected) {
                false -> {
                    binding.serviceImageview.colorFilter = null
                    binding.serviceImageview.setImageResource(resourceId)
                }
                true -> {
                    setImageWithGrayScale(blurImage, binding.serviceImageview)
                }
            }
            view.isSelected = !view.isSelected
        }

    } // End of Init

} // End of AroServiceCarView