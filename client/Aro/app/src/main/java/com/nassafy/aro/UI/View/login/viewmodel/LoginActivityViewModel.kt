package com.nassafy.aro.ui.view.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.nassafy.aro.data.dto.LoginToken
import com.nassafy.aro.domain.repository.UserAccessRepository
import com.nassafy.aro.ui.view.ServiceViewModel
import com.nassafy.aro.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginActivityViewModel @Inject constructor(
    private val loginRepository: UserAccessRepository
) : ServiceViewModel() {

    var email: String = ""
    var password: String = ""
    var nickname: String = ""
    override var isAuroraServiceSelected: Boolean = false
    override var isMeteorServiceSelected: Boolean = false
    override var selectedAuroraPlaces: List<Int> = mutableListOf()
    override var selectedMeteorPlaces: List<Int> = mutableListOf()

    val loginToken: LiveData<NetworkResult<LoginToken>>
        get() = loginRepository.loginToken
    val isEmailValidated: LiveData<Boolean>
        get() = loginRepository.isEmailValidated
    val isEmailAuthCodeValidated: LiveData<Boolean>
        get() = loginRepository.isEmailAuthCodeValidated
    val countryListLiveData: LiveData<NetworkResult<List<String>>>
        get() = loginRepository.countryListLiveData

    suspend fun loginByIdPassword(email: String, password: String) {
        viewModelScope.launch {
            loginRepository.loginByIdPassword(email, password)
        }
    }

    fun validateEmail(email: String) {
        viewModelScope.launch {
            loginRepository.validateEmail(email)
        }
    }

    fun setIsEmailValidatedFalse() {
        loginRepository.setIsEmailValidatedFalse()
    }

    fun setisEmailAuthCodeValidatedFalse() {
        loginRepository.setisEmailAuthCodeValidatedFalse()
    }

    fun validateEmialAuthCode(email: String, code: String) {
        viewModelScope.launch {
            loginRepository.validateEmialAuthenticationCode(email, code)
        }
    }

    fun getCountryList() {
        viewModelScope.launch {
            loginRepository.getCountryList()
        }
    }

}