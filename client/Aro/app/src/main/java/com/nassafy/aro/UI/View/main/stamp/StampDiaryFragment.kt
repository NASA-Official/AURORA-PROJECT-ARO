package com.nassafy.aro.ui.view.main.stamp

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.nassafy.aro.data.dto.PlaceDiaryTest
import com.nassafy.aro.databinding.FragmentStampDiaryBinding
import com.nassafy.aro.ui.view.BaseFragment
import com.nassafy.aro.util.showSnackBarMessage

class StampDiaryFragment() :
    BaseFragment<FragmentStampDiaryBinding>(FragmentStampDiaryBinding::inflate) {
    private lateinit var mContext: Context

    // viewPager
    private lateinit var stampDiaryImageViewpager2: StampDiaryImageViewPagerAdapter

    private var diaryImageList: List<Int> = emptyList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    } // End of onAttach

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 번들 가져오기
        val placeDiaryTest = requireArguments().getParcelable<PlaceDiaryTest>("placeDiaryTest")
        diaryImageList = placeDiaryTest!!.diaryImageList!!

        // 뷰페이저 달아주기
        initViewPagerAdapter()
        // 이벤트 리스너 등록
        initEventListeners()

        // val mScaleGestureDetector : ScaleGestureDetector = ScaleGestureDetector(requireContext(), Scale )

        binding.stampDiaryCountryPlaceNameTextview.text = placeDiaryTest.placeName.toString()
        binding.stampDiaryCountryPlaceInformTextview.text =
            placeDiaryTest.placeExplanation.toString()
        binding.stampDiaryHistoryEdittext.setText(placeDiaryTest.diaryContent.toString())

        // 이미지가 5개가 될 경우 더이상 추가할 수 없으므로 버튼을 보이지 않도록 함
        when (diaryImageList.size) {
            5 -> binding.stampDiaryHistoryImageAddButton.visibility = View.GONE
            else -> binding.stampDiaryHistoryImageAddButton.visibility = View.VISIBLE
        }

    } // End of onViewCreated

    private fun initViewPagerAdapter() {
        // 뷰페이저
        stampDiaryImageViewpager2 =
            StampDiaryImageViewPagerAdapter(diaryImageList)
        binding.stampDiaryImageViewpager2.apply {
            adapter = stampDiaryImageViewpager2
            ViewPager2.ORIENTATION_HORIZONTAL
        }

        stampDiaryImageViewpager2.setItemClickListener(object :
            StampDiaryImageViewPagerAdapter.ItemClickListener {
            override fun imageRemoveButtonClick(position: Int) {
                requireView().showSnackBarMessage("${position + 1} 이미지 선택됨")
            }
        })


        TabLayoutMediator(
            binding.stampDiaryImageIndicator, binding.stampDiaryImageViewpager2
        ) { tab, position ->
//            if (tab.view.isSelected == true) {
//                tab.view.background =
//                    resources.getDrawable(R.drawable.diary_image_pager_indicator_icon_isselected_true)
//            }
//
//            if (tab.view.isSelected == false) {
//                tab.view.background =
//                    resources.getDrawable(R.drawable.diary_image_pager_indicator_icon_isselected_false)
//            }
            //tab.text = countryPlaceList[position].countryName
            //tab.icon = resources.getDrawable(R.drawable.diary_image_pager_indicator_icon_isselected_false)
        }.attach()
    } // End of initAdapter

    private fun initEventListeners() {
        binding.stampDiarySaveButton.setOnClickListener {
            requireView().showSnackBarMessage("저장 버튼 클릭됨")
        }
    } // End of eventListeners
} // End of StampDiaryFragment