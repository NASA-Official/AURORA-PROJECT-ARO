package com.nassafy.aro.ui.view.login

import android.content.res.Resources.Theme
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.nassafy.aro.Application
import com.nassafy.aro.R
import com.nassafy.aro.data.dto.PlaceItem
import com.nassafy.aro.data.dto.UserTest
import com.nassafy.aro.databinding.FragmentAroCountryPlaceSelectBinding
import com.nassafy.aro.ui.adapter.CountrySpinnerAdapter
import com.nassafy.aro.ui.view.BaseFragment
import com.nassafy.aro.ui.view.custom.AroCountryPlaceSelectFragment
import com.nassafy.aro.ui.view.custom.CountryPlaceChips
import com.nassafy.aro.ui.view.custom.CountryPlaceLazyColumn
import com.nassafy.aro.ui.view.login.viewmodel.LoginActivityViewModel
import com.nassafy.aro.util.NetworkResult
import com.nassafy.aro.util.showSnackBarMessage
import kotlinx.coroutines.*

class JoinCountryPlaceSelectFragment : BaseFragment<FragmentAroCountryPlaceSelectBinding>(
    FragmentAroCountryPlaceSelectBinding::inflate) {


    private val loginActivityViewModel: LoginActivityViewModel by activityViewModels()
    private var spinnerList = arrayListOf<String>()
    private lateinit var adapter: CountrySpinnerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.sign_up_button))
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

    private fun initView() {
        initSpinner(spinnerList)
        binding.nextButton.setOnClickListener {
            loginActivityViewModel.apply {
                join(UserTest(
                    email = email,
                    password = password,
                    nickname = nickname,
                    alarm = true,
                    auroraService = isAuroraServiceSelected,
                    auroraPlaces = selectedAuroraPlaces.value?.map { it.placeName } ?: emptyList(),
                    meteorService = isMeteorServiceSelected,
                    meteorPlaces = selectedMeteorPlaces.value?.map { it.placeName } ?: emptyList(),
                ))
            }
        }
    }

    fun initSpinner(countryList: ArrayList<String>) {
        adapter =
            CountrySpinnerAdapter(requireContext(), R.layout.item_country_spinner, countryList)
        binding.selectCountryPlaceSpinner.adapter = adapter
        binding.selectCountryPlaceSpinner.onItemSelectedListener =
            object: AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                    Log.d("ssafy_pcs", parent?.getItemAtPosition(position).toString())
                    loginActivityViewModel.getPlaceList(parent?.getItemAtPosition(position).toString())
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }// End of onItemSelectedListener
    } // End of initSpinner

    private fun initObserve() {
        loginActivityViewModel.countryListLiveData.observe(this.viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    Log.d(
                        "ssafy_pcs", "getCountryTestResponseLiveData: ${it.data}"
                    )
                    spinnerList.clear()
                    spinnerList.addAll(it.data!!)
                    adapter.notifyDataSetChanged()
                    binding.selectCountryPlaceSpinner.adapter = adapter
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
        } // End of countryListLiveData.observe

        loginActivityViewModel.placeListLiveData.observe(this.viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    Log.d("ssafy/p1", it.data.toString())
                    Log.d("ssafy/p2", loginActivityViewModel.selectedAuroraPlaces.value.toString())
                    initComposeView(it.data!!, loginActivityViewModel.selectedAuroraPlaces.value!!)
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

        loginActivityViewModel.selectedAuroraPlaces.observe(this.viewLifecycleOwner) { selectedAuroraPlaces ->
            when (loginActivityViewModel.placeListLiveData.value!!) {
                 is NetworkResult.Success<List<PlaceItem>> -> {
                    initComposeView(loginActivityViewModel.placeListLiveData.value!!.data!!, selectedAuroraPlaces)
                }
                is NetworkResult.Error<*> -> {
                    requireView().showSnackBarMessage("서버 통신 에러 발생")
                }
                is NetworkResult.Loading<*> -> {
                    //TODO Loading
                    Log.d(
                        "ssafy_pcs", "로딩 중.."
                    )
                }
            }
        }


        loginActivityViewModel.userJoinNetworkResultLiveData.observe(this.viewLifecycleOwner) { selectedAuroraPlaces ->
            when (loginActivityViewModel.placeListLiveData.value!!) {
                is NetworkResult.Success<List<PlaceItem>> -> {
                    requireView().showSnackBarMessage("회원가입 성공!")
                    findNavController().navigate(R.id.action_joinCountryPlaceSelectFragment_to_loginFragment)
                }
                is NetworkResult.Error<*> -> {
                    requireView().showSnackBarMessage("서버 통신 에러 발생")
                }
                is NetworkResult.Loading<*> -> {
                    //TODO Loading
                    Log.d(
                        "ssafy_pcs", "로딩 중.."
                    )
                }
            }
        }

    }
    private fun initComposeView(placeList: List<PlaceItem>, selectedAuroraPlaceList: MutableList<PlaceItem>) {
        binding.countryPlaceComposeview.apply {
            // Dispose of the Composition when the view's LifecycleOwner

            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {

                // In Compose world
                MaterialTheme {
                    Column(
                        modifier = Modifier
                            .height(this.height.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CountryPlaceChips(selectedAuroraPlaceList , loginActivityViewModel)
                        Divider(
                            modifier = Modifier
                                .height(2.dp)
                                .padding(horizontal = 24.dp),
                            color = Color.White
                        ) // End of Divider
                        CountryPlaceLazyColumn(placeList, selectedAuroraPlaceList, loginActivityViewModel)
                    } // End of Column
                }
            }
        }
        binding.cancelButton.setOnClickListener {
            loginActivityViewModel.isAuroraServiceSelected = false
            loginActivityViewModel.isMeteorServiceSelected = false
            loginActivityViewModel.clearSelectedAuroraPlaceList()
            findNavController().popBackStack()
        }
    } // End of initView
}