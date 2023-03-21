package com.nassafy.aro.ui.view.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.nassafy.aro.data.dto.LoginToken
import com.nassafy.aro.domain.repository.UserAccessRepository
import com.nassafy.aro.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginActivityViewModel @Inject constructor(private val loginRepository: UserAccessRepository) :
    ViewModel() {

    var email: String = ""
    var password: String = ""
    var nickname: String = ""

    val loginToken: LiveData<NetworkResult<LoginToken>>
        get() = loginRepository.loginToken
    val isEmailValidated: LiveData<Boolean>
        get() = loginRepository.isEmailValidated
    val isEmailAuthCodeValidated: LiveData<Boolean>
        get() = loginRepository.isEmailAuthCodeValidated

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

}