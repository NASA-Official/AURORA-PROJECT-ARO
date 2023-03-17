package com.nassafy.aro.ui.view.main.stamp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.nassafy.aro.R
import com.nassafy.aro.data.dto.Country
import com.nassafy.aro.databinding.FragmentStampDiaryBinding
import com.nassafy.aro.util.showSnackBarMessage

class StampDiaryFragment : Fragment() {
    private lateinit var binding: FragmentStampDiaryBinding
    private lateinit var mContext: Context

    private lateinit var stampDiaryImageViewpager2: StampDiaryImageViewPagerAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    } // End of onAttach

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStampDiaryBinding.inflate(inflater, container, false)

        // 이미지가 5개가 될 경우 더이상 추가할 수 없으므로 버튼을 보이지 않도록 함
        when (countryPlaceList.size) {
            5 -> binding.stampDiarySaveButton.visibility = View.GONE
            else -> binding.stampDiarySaveButton.visibility = View.VISIBLE
        }

        binding.stampDiaryHistoryEdittext.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_MASK -> {
                        v!!.parent.requestDisallowInterceptTouchEvent(false)
                    }
                }
                return false
            }
        })

        return binding.root
    } // End of onCreateView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뷰페이저 달아주기
        initViewPagerAdapter()
    } // End of onViewCreated

    private fun initViewPagerAdapter() {
        // 뷰페이저
        stampDiaryImageViewpager2 = StampDiaryImageViewPagerAdapter(countryPlaceList)
        binding.stampDiaryImageViewpager2.apply {
            adapter = stampDiaryImageViewpager2
        }

        binding.stampDiaryImageViewpager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        stampDiaryImageViewpager2.setItemClickListener(object :
            StampDiaryImageViewPagerAdapter.ItemClickListener {
            override fun imageRemoveButtonClick(position: Int) {
                requireView().showSnackBarMessage("${position + 1} 이미지 선택됨")
            }
        })


        // stamp_diary_image_indicator
        // binding.stampDiaryImageIndicator.setupWithViewPager(stampDiaryImageViewpager2., true)
    } // End of initAdapter

    companion object {
        var countryPlaceList = arrayListOf(
            Country("미국", "쿡 카운티", R.drawable.usa_cook_county_color, "시카고근교에서 즐기는 북극광 명소"),
            Country("미국", "아이다호 팬핸들", R.drawable.usa_idaho_color, "국유림의 장관 하지만, 곰조심!"),
            Country("미국", "어퍼 반도", R.drawable.usa_upper_color, "미시간주의 자랑"),
            Country("미국", "아루스투크", R.drawable.usa_arustuk_color, "국립야생공원에서 보이는 북극광"),
            Country("미국", "데날리", R.drawable.usa_denali_color, "알래스카 산악 공원의 자연 예술"),
        )
    }
} // End of StampDiaryFragment