package com.nassafy.aro.ui.view.main.stamp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nassafy.aro.domain.repository.StampRepository
import com.nassafy.aro.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StampViewModel @Inject constructor(private val stampRepository: StampRepository) :
    ViewModel() {

    // ================================= 테스트 통신 =================================
    val getCountryTestResponseLiveData: LiveData<NetworkResult<List<String>>>
        get() = stampRepository.getCountryTestResponseLiveData

    suspend fun getCountryTest() {
        viewModelScope.launch {
            stampRepository.getCountryTest()
        }
    } // End of getCountryTest


    private val _countryList: MutableList<String> = arrayListOf()
    val countryList: List<String>
        get() = _countryList

    fun setCountryList(newCountryList: List<String>) {
        _countryList.clear()
        _countryList.addAll(newCountryList)
    } // End of setCountryList
    
    // ================================= 유저별 국가 스탬프 데이터 가져오기 =================================
    // getUserStampDataGroupByCountry

    val getUserStampDataGroupByCountryResponseLiveData: LiveData<NetworkResult<String>>
        get() = stampRepository.getUserStampDataGroupByCountryResponseLiveData

    suspend fun getUserStampDataGroupByCountry() {
        viewModelScope.launch {
            stampRepository.getUserStampDataGroupByCountry()
        }
    } // End of getUserStampDataGroupByCountry
} // End of StampViewModel class
