package com.nassafy.aro.ui.view.main.stamp

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import com.nassafy.aro.R
import com.nassafy.aro.data.dto.Country

class StampCountryPlaceCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
) : ConstraintLayout(context, attrs) {
    private lateinit var countryPlaceAdapter: CountryPlaceViewPagerAdapter

    private var countryPlaceNameTextView: TextView? = null
    private var countryNameTextView: TextView? = null
    private var countryPlaceInformTextView: TextView? = null
    private var viewPager: ViewPager2? = null
    private lateinit var viewPagerAdapter: CountryPlaceViewPagerAdapter

    init {
        val inflater: LayoutInflater =
            getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.stamp_country_customview_layout, this, false)
        addView(view)

        // 값이 들어갈 부분
//        countryPlaceNameTextView = view.findViewById(R.id.stamp_country_place_name_textview)
//        countryNameTextView = view.findViewById(R.id.stamp_country_name_textview)
//        countryPlaceInformTextView = view.findViewById(R.id.stamp_country_place_inform_textview)

        var countryPlaceList = ArrayList<Country>()
//        countryPlaceList.add(Country("미국", R.drawable.usa_cook_county_color, "시카고근교에서 즐기는 북극광 명소"))
//        countryPlaceList.add(Country("미국", R.drawable.usa_upper_color, "국유림의 장관 하지만, 곰조심!"))
//        countryPlaceList.add(Country("미국", R.drawable.usa_arustuk_color, "미시간주의 자랑"))
//        countryPlaceList.add(Country("미국", R.drawable.usa_fairbanks_color, "국립야생공원에서 보이는 북극광"))
//        countryPlaceList.add(Country("미국", R.drawable.usa_denali_color, "알래스카 산악 공원의 자연 예술"))


        viewPager = view.findViewById(R.id.stamp_country_custom_viewpager2)
        viewPagerAdapter = CountryPlaceViewPagerAdapter(countryPlaceList)


        // viewPager.pageMargin = 15
        viewPager!!.setPadding(50, 0, 50, 0);
        viewPager!!.clipToPadding = false
//        viewPager.setPageMargin(25)
//        viewPager.adapter = mViewPagerAdapter
//        viewPager.addOnPageChangeListener(viewPagerPageChangeListener)

    }

    fun setView(typeNum: Int) {
        countryPlaceNameTextView!!.text = "덴벌라"

    } // End of setView
} // End of StampCountryPlaceCustomView class
