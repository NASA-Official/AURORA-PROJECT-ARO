package com.nassafy.aro.domain.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nassafy.aro.Application
import com.nassafy.aro.data.dto.StampTest
import com.nassafy.aro.domain.api.StampApi
import com.nassafy.aro.util.NetworkResult

private const val TAG = "StampRepository_싸피"

class StampRepository {
    // 일반 retrofit
    private val stampApi = Application.retrofit.create(StampApi::class.java)

    // 헤더가 담긴 retrofit
    private val headerStampApi = Application.retrofit.create(StampApi::class.java)

    // ================================= 테스트 통신 =================================
    private val _getCountryTestResponseLiveData = MutableLiveData<NetworkResult<List<String>>>()
    val getCountryTestResponseLiveData: LiveData<NetworkResult<List<String>>>
        get() = _getCountryTestResponseLiveData

    suspend fun getCountryTest() {
        val response = stampApi.getCountryTest()
        Log.d(TAG, "getCountryTest: ${response.body()}")

        _getCountryTestResponseLiveData.postValue(NetworkResult.Loading())

        when {
            response.isSuccessful && response.body() != null -> {
                _getCountryTestResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
            }
            response.errorBody() != null -> {
                _getCountryTestResponseLiveData.postValue(
                    NetworkResult.Error(
                        response.errorBody()!!.string()
                    )
                )
            }
        }

    } // End of getCountryTest


    private val _getStampTestResponseLiveData = MutableLiveData<NetworkResult<List<StampTest>>>()
    val getStampTestResponseLiveData: LiveData<NetworkResult<List<StampTest>>>
        get() = _getStampTestResponseLiveData

    suspend fun getStampsTest() {

    } // End of getStampsTest


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
