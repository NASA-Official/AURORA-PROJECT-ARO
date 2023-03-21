package com.nassafy.aro.ui.view.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nassafy.aro.data.dto.LoginToken
import com.nassafy.aro.domain.repository.UserAccessRepositoryImpl
import com.nassafy.aro.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginActivityViewModel @Inject constructor(private val loginRepository: UserAccessRepositoryImpl): ViewModel() {

    val loginToken: LiveData<NetworkResult<LoginToken>>
        get() = loginRepository.loginToken

    suspend fun loginByIdPassword(id: String, password: String) {
        viewModelScope.launch {
            loginRepository.loginByIdPassword(id, password)
        }
    }

    suspend fun validateEmialAuthCode(code: Int): Boolean {
        var result = false
        viewModelScope.launch {
            result = loginRepository.validateEmialAuthCode(code)
        }
        return result
    }

}