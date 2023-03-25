package com.nassafy.aro.domain.repository

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.nassafy.aro.data.dto.FavoriteList
import com.nassafy.aro.data.dto.UserTest
import com.nassafy.aro.domain.api.MyPageApi
import com.nassafy.aro.util.NetworkResult
import com.nassafy.aro.util.setNetworkResult
import javax.inject.Inject

class MyPageRepository @Inject constructor(private val myPageApi: MyPageApi) {

    private val _nicknameLiveData = MutableLiveData<NetworkResult<Unit>>()
    val nicknameLiveData get() = _nicknameLiveData

    private val _selectedServiceNetworResultLiveData = MutableLiveData<NetworkResult<UserTest>>()
    val selectedServiceNetworResultLiveData get() = _selectedServiceNetworResultLiveData

    private val _favoriteListNetworkResultLiveData = MutableLiveData<NetworkResult<FavoriteList>>()
    val favoriteListNetworkResultLiveData get() = _favoriteListNetworkResultLiveData

    private val _selectServiceNetworkresultLiveData = MutableLiveData<NetworkResult<Unit>>()
    val selectServiceNetworkresultLiveData get() = _selectServiceNetworkresultLiveData

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

    suspend fun getFavoriteList() {
        val response = myPageApi.getFavoriteList()
        Log.d("ssafy/changeNickname", response.toString())
        _favoriteListNetworkResultLiveData.postValue(NetworkResult.Loading())
        try {
            when {
                response.isSuccessful -> {
                    _favoriteListNetworkResultLiveData.postValue(NetworkResult.Success(
                        response.body()!!
                    ))

                }
                response.errorBody() != null -> {
                    _favoriteListNetworkResultLiveData.postValue(NetworkResult.Error(response.errorBody()!!.string()))
                }
            } // End of when
        } catch (e: java.lang.Exception) {
            Log.e("ssafy", "getServerCallTest: ${e.message}")
        } // End of try-catch
    } // End of changeNickname

    suspend fun getSelectedServiceList() {
        val response = myPageApi.getSelectedService()
        _selectedServiceNetworResultLiveData.postValue(NetworkResult.Loading())
        try {
            when {
                response.isSuccessful -> {
                    selectedServiceNetworResultLiveData.postValue(
                        NetworkResult.Success(
                            response.body()!!
                        )
                    )
                }
                response.errorBody() != null -> {
                    selectedServiceNetworResultLiveData.postValue(NetworkResult.Error(response.errorBody()!!.string()))
                }
            } // End of when
        } catch (e: java.lang.Exception) {
            Log.e("ssafy", "getServerCallTest: ${e.message}")
        } // End of try-catch
    }

    suspend fun selectService(auroraService: Boolean, meteorService: Boolean) {
        val response = myPageApi.selectService(
            JsonObject().apply {
                addProperty("auroraService", auroraService)
                addProperty("meteorService", meteorService)
            }
        )
        _selectServiceNetworkresultLiveData.setNetworkResult(response)
    } // End of selectService


} // End of MyPageRepository