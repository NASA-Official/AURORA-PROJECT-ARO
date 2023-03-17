package com.nassafy.aro.ui.view.main.stamp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.nassafy.aro.R
import com.nassafy.aro.data.dto.Country
import com.nassafy.aro.databinding.FragmentStampHomeBinding

private const val TAG = "StampHomeFragment_싸피"

class StampHomeFragment : Fragment() {
    private lateinit var mContext: Context
    private lateinit var binding: FragmentStampHomeBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    } // End of onAttach

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStampHomeBinding.inflate(inflater, container, false)
        return binding.root
    } // End of onCreateView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.stampCountryCustomViewpager2.adapter =
            CountryPlaceViewPagerAdapter(countryPlaceList)
        binding.stampCountryCustomViewpager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL



    } // End of onViewCreated

    companion object {
        var countryPlaceList = arrayListOf(
            Country("미국", "쿡 카운티", R.drawable.usa_cook_county_color, "시카고근교에서 즐기는 북극광 명소"),
            Country("미국", "아이다호 팬핸들", R.drawable.usa_idaho_color, "국유림의 장관 하지만, 곰조심!"),
            Country("미국", "어퍼 반도", R.drawable.usa_upper_color, "미시간주의 자랑"),
            Country("미국", "아루스투크", R.drawable.usa_arustuk_color, "국립야생공원에서 보이는 북극광"),
            Country("미국", "데날리", R.drawable.usa_denali_color, "알래스카 산악 공원의 자연 예술"),
        )
    }
} // End of StampHomeFragment class