package com.nassafy.aro.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nassafy.aro.Application
import com.nassafy.aro.domain.api.StampApi
import com.nassafy.aro.util.NetworkResult

class StampRepository {
    // 일반 retrofit
    private val stampApi = Application.retrofit.create(StampApi::class.java)

    // 헤더가 담긴 retrofit
    private val headerStampApi = Application.retrofit.create(StampApi::class.java)

    // ================================= 테스트 통신 =================================


    // ================================= 유저별 국가 스탬프 데이터 가져오기 =================================
    // getUserStampDataGroupByCountry

    private val _getUserStampDataGroupByCountryResponseLiveData =
        MutableLiveData<NetworkResult<String>>()
    val getUserStampDataGroupByCountryResponseLiveData: LiveData<NetworkResult<String>>
        get() = _getUserStampDataGroupByCountryResponseLiveData

    suspend fun getUserStampDataGroupByCountry() {
        //val response = stampApi.

        _getUserStampDataGroupByCountryResponseLiveData.postValue(NetworkResult.Loading())

        when {

        }


    }  // End of getUserStampDataGroupByCountry


} // End of StampRepository class
