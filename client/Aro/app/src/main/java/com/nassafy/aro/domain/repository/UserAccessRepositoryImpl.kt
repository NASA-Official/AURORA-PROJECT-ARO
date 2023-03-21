package com.nassafy.aro.domain.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nassafy.aro.data.dto.LoginToken
import com.nassafy.aro.data.dto.user.request.LoginRequest
import com.nassafy.aro.domain.api.UserAccessApi
import com.nassafy.aro.util.NetworkResult
import javax.inject.Inject

class UserAccessRepositoryImpl @Inject constructor(private val userAccessApi: UserAccessApi) : UserAccessRepository { // End of UserAccessRepositoryImpl

    private val _loginToken = MutableLiveData<NetworkResult<LoginToken>>()
    val loginToken: LiveData<NetworkResult<LoginToken>> get() = _loginToken

    override suspend fun loginByIdPassword(id: String, password: String) {
        val response = userAccessApi.login(LoginRequest(id, password))
        _loginToken.postValue(NetworkResult.Loading())

        try {
            when {
                response.isSuccessful -> {
                    _loginToken.postValue(NetworkResult.Success(
                        response.body()!!
                    )) // End of postValue
                } // End of response.isSuccessful
                response.errorBody() != null -> {
                    _loginToken.postValue(NetworkResult.Error(response.errorBody()!!.string()))
                } // End of response.errorBody
            } // End of when
        } catch (e: java.lang.Exception) {
            Log.e("ssafy", "getServerCallTest: ${e.message}")
        }
    } // End of loginByIdPassword

    override suspend fun validateEmialAuthCode(code: Int): Boolean {
        val response = userAccessApi.validateEmialAuthCode(code)
        Log.d("ssafy", response.toString())
        try {
            when {
                response.isSuccessful -> {
                    return true
                }
                response.errorBody() != null -> return false
            }
        } catch (e: java.lang.Exception) {
            Log.e("ssafy", "getServerCallTest: ${e.message}")
        }
        return false
    }

}