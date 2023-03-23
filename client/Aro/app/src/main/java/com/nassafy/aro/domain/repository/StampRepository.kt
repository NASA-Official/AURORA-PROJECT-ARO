package com.nassafy.aro.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nassafy.aro.data.dto.CountryTest
import com.nassafy.aro.data.dto.StampTest
import com.nassafy.aro.domain.api.StampApi
import com.nassafy.aro.util.NetworkResult
import com.nassafy.aro.util.di.HeaderInterceptorApi
import com.nassafy.aro.util.di.WithoutHeaderInterceptorApi
import javax.inject.Inject

// provideStampApi
private const val TAG = "StampRepository_싸피"

class StampRepository @Inject constructor(
    @WithoutHeaderInterceptorApi private val stampApi: StampApi,
    @HeaderInterceptorApi private val headerStampApi: StampApi
) {



    // ==================================== 전체 국가 리스트 가져오기 ====================================
    private val _getAllNationListResponseLiveData = MutableLiveData<NetworkResult<List<String>>>()
    val getAllNationListResponseLiveData: LiveData<NetworkResult<List<String>>>
        get() = _getAllNationListResponseLiveData

    suspend fun getAllNationList() {
        val response = stampApi.getAllNationList()

        _getAllNationListResponseLiveData.postValue(NetworkResult.Loading())

        when {
            response.isSuccessful && response.body() != null -> {
                _getAllNationListResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
            }
            response.errorBody() != null -> {
                _getAllNationListResponseLiveData.postValue(
                    NetworkResult.Error(
                        response.errorBody()!!.string()
                    )
                )
            }
        }
    } // End of getAllNationList

    // ================================= 국가별 해당 데이터와 유저 데이터 가져오기 =================================
    private val _getCountryStampDataResponseLiveData = MutableLiveData<NetworkResult<CountryTest>>()
    val getCountryStampDataResponseLiveData: LiveData<NetworkResult<CountryTest>>
        get() = _getCountryStampDataResponseLiveData

    suspend fun getCountryStampData() {
        val response = headerStampApi.getCountryStampData()

        _getCountryStampDataResponseLiveData.postValue(NetworkResult.Loading())

        when {
            response.isSuccessful && response.body() != null -> {
                _getCountryStampDataResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
            }
            response.errorBody() != null -> {
                _getCountryStampDataResponseLiveData.postValue(
                    NetworkResult.Error(
                        response.errorBody()!!.string()
                    )
                )
            }
        }


    } // End of _getCountryStampData

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
