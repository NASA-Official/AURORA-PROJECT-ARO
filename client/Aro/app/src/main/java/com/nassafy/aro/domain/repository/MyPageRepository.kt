package com.nassafy.aro.domain.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.nassafy.aro.domain.api.MyPageApi
import com.nassafy.aro.util.NetworkResult
import javax.inject.Inject

class MyPageRepository @Inject constructor(private val myPageApi: MyPageApi) {

    private val _nicknameLiveData = MutableLiveData<NetworkResult<Unit>>()
    val nicknameLiveData get() = _nicknameLiveData

    suspend fun changeNickname(nickname: String) {
        val response = myPageApi.changeNickname(nickname = nickname)
        Log.d("ssafy/changeNickname", response.toString())
        _nicknameLiveData.postValue(NetworkResult.Loading())
        try {
            when {
                response.isSuccessful -> {
                    _nicknameLiveData.postValue(
                        NetworkResult.Success(
                            response.body()!!
                        )
                    )
                }
                response.errorBody() != null -> {
                    _nicknameLiveData.postValue(NetworkResult.Error(response.errorBody()!!.string()))
                }
            } // End of when
        } catch (e: java.lang.Exception) {
            Log.e("ssafy", "getServerCallTest: ${e.message}")
        } // End of try-catch
    } // End of changeNickname

} // End of MyPageRepository