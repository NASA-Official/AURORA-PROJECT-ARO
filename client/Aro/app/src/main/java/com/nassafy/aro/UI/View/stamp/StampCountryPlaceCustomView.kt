package com.nassafy.aro.UI.View.stamp

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.nassafy.aro.R

class StampCountryPlaceCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?
) : ConstraintLayout(context, attrs) {

    private var countryPlaceNameTextView: TextView? = null
    private var countryNameTextView: TextView? = null
    private var countryPlaceInformTextView: TextView? = null

    init {
        val inflater: LayoutInflater =
            getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.stamp_country_customview_layout, this, false)
        addView(view)

        countryPlaceNameTextView = view.findViewById(R.id.stamp_country_place_name_textview)
        countryNameTextView = view.findViewById(R.id.stamp_country_name_textview)
        countryPlaceInformTextView = view.findViewById(R.id.stamp_country_place_inform_textview)
    }


} // End of StampCountryPlaceCustomView class
