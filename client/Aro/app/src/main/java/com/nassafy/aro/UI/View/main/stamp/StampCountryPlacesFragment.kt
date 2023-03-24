package com.nassafy.aro.ui.view.main.stamp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import androidx.viewpager2.widget.ViewPager2
import com.nassafy.aro.R
import com.nassafy.aro.databinding.FragmentStampCountryPlacesBinding
import com.nassafy.aro.ui.view.BaseFragment
import com.nassafy.aro.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

private const val TAG = "StampHomeFragment_싸피"

@AndroidEntryPoint
class StampCountryPlacesFragment :
    BaseFragment<FragmentStampCountryPlacesBinding>(FragmentStampCountryPlacesBinding::inflate) {
    private lateinit var mContext: Context

    // viewPager
    private lateinit var countryPlaceViewPager: CountryPlaceViewPagerAdapter

    // Fragment ViewModel
    private val stampCountryPlaceViewModel: StampCountryPlaceViewModel by viewModels()

    // navigationViewModel
    private val stampHomeNavViewModel: StampNavViewModel by navGraphViewModels(R.id.nav_stamp_diary)


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    } // End of onAttach

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUserPlaceDataGroupByCountryResponseLiveDataObserve()

        // View가져오기.
        CoroutineScope(Dispatchers.IO).launch {
            val def: Deferred<Int> = async {
                initViewGetData()
                1
            }

            def.await()
            initEventListeners()
        }
    } // End of onViewCreated

    private suspend fun initViewGetData() {
        /*
            stampHomeNavViewModel 에서 선택된 국가를 가져와서 통신
         */
        Log.d(TAG, "initViewGetData: ${stampHomeNavViewModel.selectedCountry}")
        stampCountryPlaceViewModel.setSelectedCountry(stampCountryPlaceViewModel.selectedCountry)

        CoroutineScope(Dispatchers.IO).launch {
            stampCountryPlaceViewModel.getUserPlaceDataGroupByCountry()
        }
    } // End of initViewGetData

    private fun initEventListeners() {
    } // End of initEventListeners

    private fun initViewPagerAdapter() {
        countryPlaceViewPager = CountryPlaceViewPagerAdapter(
            stampHomeNavViewModel.selectedCountry,
            stampHomeNavViewModel.userCountryPlaceDataList
        )
        binding.stampCountryCustomViewpager2.apply {
            adapter = countryPlaceViewPager
            ViewPager2.ORIENTATION_HORIZONTAL
        }

        countryPlaceViewPager.setItemClickListener(object :
            CountryPlaceViewPagerAdapter.ItemClickListener {
            override fun writeDiaryButtonClick(position: Int) {
                // 해당 명소에 해당하는 일기 데이터를 가져옴.
                Navigation.findNavController(binding.stampCountryCustomViewpager2.findViewById(R.id.stamp_country_place_write_diary_button))
                    .navigate(
                        R.id.action_stampCountryPlacesFragment_to_stampDiaryFragment,
                    )
            }

            // 검증 버튼 클릭 이벤트
            override fun validateButtonclick(position: Int) {
                Log.d(TAG, "validateButtonclick: 검증 버튼 클릭")
            } // End of validateButtonclick
        })
    } // End of initViewPagerAdapter

    private fun getUserPlaceDataGroupByCountryResponseLiveDataObserve() {
        stampCountryPlaceViewModel.getUserPlaceDataGroupByCountryResponseLiveData.observe(this.viewLifecycleOwner) {
            // 통신 성공시 ViewPagerAdapter 연결

            when (it) {
                is NetworkResult.Success -> {
                    stampHomeNavViewModel.setUserCountryPlaceDataList(it.data!!)

                    //setUserCountryPlaceDataList
                    initViewPagerAdapter()
                }

                is NetworkResult.Error -> {

                }

                is NetworkResult.Loading -> {

                }
            }
        }
    } // End of getUserPlaceDataGroupByCountryResponseLiveDataObserve

} // End of StampHomeFragment class