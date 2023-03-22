package com.nassafy.aro.domain.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.nassafy.aro.data.dto.LoginToken
import com.nassafy.aro.domain.api.UserAccessApi
import com.nassafy.aro.util.NetworkResult
import javax.inject.Inject

class UserAccessRepository @Inject constructor(private val userAccessApi: UserAccessApi) { // End of UserAccessRepository

    private val _loginToken = MutableLiveData<NetworkResult<LoginToken>>()
    val loginToken: LiveData<NetworkResult<LoginToken>> get() = _loginToken

    private val _isEmailValidated = MutableLiveData<Boolean>()
    val isEmailValidated get() = _isEmailValidated
    private val _isEmailAuthCodeValidated = MutableLiveData<Boolean>()
    val isEmailAuthCodeValidated get() = _isEmailAuthCodeValidated

    private val _countryListLiveData = MutableLiveData<NetworkResult<List<String>>>()
    val countryListLiveData get() = _countryListLiveData

    fun setIsEmailValidatedFalse() {
        _isEmailValidated.postValue(false)
    }
    fun setisEmailAuthCodeValidatedFalse() {
        _isEmailAuthCodeValidated.postValue(false)
    }

    suspend fun loginByIdPassword(email: String, password: String) {
        val response = userAccessApi.login(JsonObject().apply {
            addProperty("email", email)
            addProperty("password", password)
        })
        _loginToken.postValue(NetworkResult.Loading())

        try {
            when {
                response.isSuccessful -> {
                    _loginToken.postValue(
                        NetworkResult.Success(
                            response.body()!!
                        )
                    ) // End of postValue
                } // End of response.isSuccessful
                response.errorBody() != null -> {
                    _loginToken.postValue(NetworkResult.Error(response.errorBody()!!.string()))
                } // End of response.errorBody
            } // End of when
        } catch (e: java.lang.Exception) {
            Log.e("ssafy", "getServerCallTest: ${e.message}")
        }
    } // End of loginByIdPassword

    suspend fun validateEmialAuthenticationCode(email: String, code: String) {
        val response = userAccessApi.validateEmailAuthenticationCode(JsonObject().apply {
            addProperty("email", email)
            addProperty("code", code)
        })
        Log.d("ssafy", response.toString())
        try {
            when {
                response.isSuccessful -> {
                    Log.d("ssafy", "validate Code!")
                    _isEmailAuthCodeValidated.postValue(true)
                }
                response.errorBody() != null -> _isEmailAuthCodeValidated.postValue(false)
            }
        } catch (e: java.lang.Exception) {
            Log.e("ssafy", "getServerCallTest: ${e.message}")
        } // End of try-catch
    } // End of validateEmialAuthenticationCode

    suspend fun validateEmail(email: String) {
        val response = userAccessApi.validateEmail(JsonObject().apply {
            addProperty("email", email)
        })
        try {
            when {
                response.isSuccessful -> _isEmailValidated.postValue(true)
                response.errorBody() != null -> _isEmailValidated.postValue(false)
            }
        } catch (e: java.lang.Exception) {
            Log.e("ssafy", "getServerCallTest: ${e.message}")
        } // End of try-catch
    } // End of validateEmail

    suspend fun getCountryList() {
        val response = userAccessApi.getCountryList()
        _countryListLiveData.postValue(NetworkResult.Loading())
        try {
            when {
                response.isSuccessful -> {
                    _countryListLiveData.postValue(NetworkResult.Success(
                        response.body()!!
                    ))
                }
                response.errorBody() != null -> _countryListLiveData.postValue(NetworkResult.Error(response.errorBody().toString()))
            }
        } catch (e: java.lang.Exception) {
            Log.e("ssafy", "getServerCallTest: ${e.message}")
        }
    } // End of getNations
}