package com.nassafy.aro.domain.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nassafy.aro.Application
import com.nassafy.aro.domain.api.TestApi
import com.nassafy.aro.util.NetworkResult

private const val TAG = "TestRepository_싸피"

class TestRepository {
    private val testApi = Application.retrofit.create(TestApi::class.java)

    // 토큰 헤더가 담긴 Retrofit
    private val testHeaderApi = Application.retrofit.create(TestApi::class.java)

    // 테스트 통신 LiveData
    private val _getServerCallTestResponseLiveData = MutableLiveData<NetworkResult<String>>()
    val getServerCallTestResponseLiveData: LiveData<NetworkResult<String>>
        get() = _getServerCallTestResponseLiveData

    // 테스트 통신
    suspend fun getServerCallTest() {
        val response = testApi.serveerCallTest()
        Log.d(TAG, "response.code : ${response.code()}")
        Log.d(TAG, "response.message: ${response.message()}")
        Log.d(TAG, "response.headers: ${response.headers()}")
        Log.d(TAG, "response.body: ${response.body()}")

        _getServerCallTestResponseLiveData.postValue(NetworkResult.Loading())

        try {
            when {
                response.isSuccessful -> {
                    _getServerCallTestResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
                }
                response.errorBody() != null -> {
                    _getServerCallTestResponseLiveData.postValue(
                        NetworkResult.Error(
                            response.errorBody()!!.string()
                        )
                    )
                }
                else -> {
                    _getServerCallTestResponseLiveData.postValue(
                        NetworkResult.Error(
                            response.headers().toString(),
                            response.message().toString(),
                        )
                    )
                }
            }
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "getServerCallTest: ${e.message}")
        }


    } // End of getServerCallTest
} // End of TestRepository class