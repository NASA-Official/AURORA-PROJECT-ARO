package com.nassafy.aro.ui.view.main.stamp

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.nassafy.aro.R
import com.nassafy.aro.data.dto.PlaceDiaryTest
import com.nassafy.aro.data.dto.PlaceTest
import com.nassafy.aro.databinding.FragmentStampCountryPlacesBinding
import com.nassafy.aro.ui.view.BaseFragment

private const val TAG = "StampHomeFragment_싸피"


class StampCountryPlacesFragment :
    BaseFragment<FragmentStampCountryPlacesBinding>(FragmentStampCountryPlacesBinding::inflate) {
    private lateinit var mContext: Context

    // viewPager
    private lateinit var countryPlaceViewPager: CountryPlaceViewPagerAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    } // End of onAttach

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEventListeners()
        initViewPagerAdapter()
    } // End of onViewCreated

    private fun initEventListeners() {
    } // End of initEventListeners

    private fun initViewPagerAdapter() {
        countryPlaceViewPager = CountryPlaceViewPagerAdapter("미국", placeList)
        binding.stampCountryCustomViewpager2.apply {
            adapter = countryPlaceViewPager
            ViewPager2.ORIENTATION_HORIZONTAL
        }

        countryPlaceViewPager.setItemClickListener(object :
            CountryPlaceViewPagerAdapter.ItemClickListener {
            override fun writeDiaryButtonClick(position: Int) {
                val bundle = bundleOf("placeDiaryTest" to placeList[position].placeDiary)

                // 해당 명소에 해당하는 일기 데이터를 가져옴.
                Navigation.findNavController(binding.stampCountryCustomViewpager2.findViewById(R.id.stamp_country_custom_make_diary_button))
                    .navigate(
                        R.id.action_stampCountryPlacesFragment_to_stampDiaryFragment,
                        bundle
                    )
            }
        })

    } // End of initViewPagerAdapter

    companion object {
        var cookCountyDiaryImageList = arrayListOf(
            R.drawable.diary_test_image1,
            R.drawable.diary_test_image2,
            R.drawable.diary_test_image3,
            R.drawable.diary_test_image4,
        )

        var idahoDiaryImageList = arrayListOf(
            R.drawable.idaho_test_image1,
            R.drawable.idaho_test_image2,
            R.drawable.idaho_test_image3,
            R.drawable.idaho_test_image4,
        )


        var placeList: List<PlaceTest> = arrayListOf(
            PlaceTest(
                "미국",
                "쿡 카운티",
                R.drawable.usa_cook_county_color,
                "시카고근교에서 즐기는 북극광 명소",
                PlaceDiaryTest(
                    "미국",
                    "쿡 카운티", "시카고근교에서 즐기는 북극광 명소", "", cookCountyDiaryImageList
                )
            ),
            PlaceTest(
                "미국",
                "아이다호 팬핸들",
                R.drawable.usa_idaho_color,
                "국유림의 장관 하지만, 곰조심!",
                PlaceDiaryTest(
                    "미국",
                    "아이다호 팬핸들", "국유림의 장관 하지만, 곰조심!", "", idahoDiaryImageList
                )
            ),
            PlaceTest(
                "미국",
                "어퍼 반도",
                R.drawable.usa_upper_color,
                "미시간주의 자랑",
                PlaceDiaryTest(
                    "미국",
                    "어퍼 반도", "미시간주의 자랑", "", cookCountyDiaryImageList
                )
            ),
            PlaceTest(
                "미국",
                "아루스투크",
                R.drawable.usa_arustuk_color,
                "국립야생공원에서 보이는 북극광",
                PlaceDiaryTest(
                    "미국",
                    "아루스투크", "국립야생공원에서 보이는 북극광", "", cookCountyDiaryImageList
                )
            ),
            PlaceTest(
                "미국",
                "데날리",
                R.drawable.usa_denali_color,
                "알래스카 산악 공원의 자연 예술",
                PlaceDiaryTest(
                    "미국",
                    "데날리", "알래스카 산악 공원의 자연 예술", "", cookCountyDiaryImageList
                )
            ),
        )

    }
} // End of StampHomeFragment class