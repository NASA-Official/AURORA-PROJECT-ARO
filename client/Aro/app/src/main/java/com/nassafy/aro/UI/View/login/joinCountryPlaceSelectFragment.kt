package com.nassafy.aro.ui.view.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.nassafy.aro.data.dto.PlaceTest
import com.nassafy.aro.ui.view.custom.AroCountryPlaceSelectFragment
import com.nassafy.aro.ui.view.login.viewmodel.LoginActivityViewModel
import com.nassafy.aro.util.NetworkResult
import com.nassafy.aro.util.showSnackBarMessage
import kotlinx.coroutines.*

class joinCountryPlaceSelectFragment : AroCountryPlaceSelectFragment() {


    private val loginActivityViewModel: LoginActivityViewModel by activityViewModels()

    private val placeList = mutableListOf<PlaceTest>(
        PlaceTest("a", "1", 1, null, null),
        PlaceTest("b", "2", 2, null, null),
        PlaceTest("c", "3", 3, null, null)
    )
    //선택 된 리스트의 경우 Set으로 써도 되나?
    private val selectedPlaceList = mutableListOf<PlaceTest>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            val result: Deferred<Int> = async {
                loginActivityViewModel.getCountryList()
                0
            }
            result.await()
        }

        initObserve()
        initView()
    }

    private fun initObserve() {
        loginActivityViewModel.countryListLiveData.observe(this.viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    Log.d(
                        "ssafy_pcs", "getCountryTestResponseLiveData: ${it.data}"
                    )
                    initSpinner(it.data as ArrayList<String>)
                }
                is NetworkResult.Error -> {
                    requireView().showSnackBarMessage("서버 통신 에러 발생")
                }
                is NetworkResult.Loading -> {
                    //TODO Loading
                    Log.d(
                        "ssafy_pcs", "로딩 중.."
                    )
                }
            }
        }
    }

    private fun initView() {
        initComposeView(placeList, selectedPlaceList)
    } // End of initView
}