package com.nassafy.aro.domain.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nassafy.aro.data.dto.UserTest
import com.nassafy.aro.domain.api.MainApi
import com.nassafy.aro.util.NetworkResult
import javax.inject.Inject

class MainRepository @Inject constructor(private val mainApi: MainApi){

    private val _userInfo = MutableLiveData<NetworkResult<UserTest>>()
    val userInfo: LiveData<NetworkResult<UserTest>> get() = _userInfo

    suspend fun getUserInfo() {
        val response = mainApi.getUserInfo()
        _userInfo.postValue(NetworkResult.Loading())
        Log.d("ssafy_memberInfo", response.toString())
        Log.d("ssafy_memberInfo/header", response.headers().toString())
        try {
            when {
                response.isSuccessful -> {
                    _userInfo.postValue(
                        NetworkResult.Success(
                            response.body()!!
                        )
                    ) // End of postValue
                } // End of response.isSuccessful
                response.errorBody() != null -> {
                    _userInfo.postValue(NetworkResult.Error(response.errorBody()!!.string()))
                } // End of response.errorBody
            } // End of when
        } catch (e: java.lang.Exception) {
            Log.e("ssafy", "getServerCallTest: ${e.message}")
        }
    }

}