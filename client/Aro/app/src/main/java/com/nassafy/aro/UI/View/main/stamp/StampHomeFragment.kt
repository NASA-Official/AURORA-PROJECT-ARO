package com.nassafy.aro.ui.view.main.stamp

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.nassafy.aro.R
import com.nassafy.aro.databinding.FragmentStampHomeBinding
import com.nassafy.aro.ui.adapter.CountrySpinnerAdapter
import com.nassafy.aro.ui.view.BaseFragment
import com.nassafy.aro.util.NetworkResult
import com.nassafy.aro.util.showSnackBarMessage
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*


class StampHomeFragment :
    BaseFragment<FragmentStampHomeBinding>(FragmentStampHomeBinding::inflate) {
    private lateinit var mContext: Context

    // viewModel
    private val stampViewModel: StampViewModel by viewModels()


    // ArrayAdapter
    private lateinit var arrayAdapter: ArrayAdapter<String>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    } // End of onAttach

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUserStampDataGroupByCountryResponseLiveDataObserve()

        CoroutineScope(Dispatchers.IO).launch {
            val result: Deferred<Int> = async {
                stampViewModel.getUserStampDataGroupByCountry()
                0
            }

            result.await()
        }

        /*
                로직 : 가장 처음에 토큰과 회원UUID를 통해서 전체 국가리스트를 가져옴.
                해당 유저의 아이슬란드 데이터를 가져옴.
                아이슬란드의 지도 데이터를 가장 먼저 가져옴.

                spinner에 선택한 텍스트를 기반으로 해당 국가의 유저 스탬프 데이터를 가져옴.
         */

        initSpinner()

        Picasso.get().load(R.drawable.idaho_test_image3).fit().centerCrop()
            .into(binding.stampHomeImageview)
    } // End of onViewCreated

    private fun initSpinner() {
        val countryList: ArrayList<String> = arrayListOf("아이슬란드", "미국")
        arrayAdapter = CountrySpinnerAdapter(mContext, R.layout.item_country_spinner, countryList)
        binding.stampHomeSpinner.adapter = arrayAdapter

        binding.stampHomeSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    requireView().showSnackBarMessage(
                        parent!!.getItemAtPosition(position).toString()
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    return
                }
            }
    } // End of initSpinner

    private fun getUserStampDataGroupByCountryResponseLiveDataObserve() {
        stampViewModel.getUserStampDataGroupByCountryResponseLiveData.observe(this.viewLifecycleOwner) {
            binding.stampHomeProgressbar.visibility = View.GONE
            binding.stampHomeProgressbar.isVisible = false


            when (it) {
                is NetworkResult.Success -> {
                    initSpinner()
                }

                is NetworkResult.Error -> {
                    requireView().showSnackBarMessage("서버 통신 에러 발생")
                }

                is NetworkResult.Loading -> {
                    binding.stampHomeProgressbar.visibility = View.VISIBLE
                    binding.stampHomeProgressbar.isVisible = true
                }
            }

        }
    }

} // End of StampHomeFragment class
