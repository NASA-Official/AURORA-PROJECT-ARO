package com.nassafy.aro.ui.view.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nassafy.aro.domain.repository.UserAccessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JoinEmailFragmentViewModel @Inject constructor(
    private val userAccessRepository: UserAccessRepository
): ViewModel() {

    val isEmailValidated: LiveData<Boolean>
        get() = userAccessRepository.isEmailValidated
    val isEmailAuthCodeValidated: LiveData<Boolean>
        get() = userAccessRepository.isEmailAuthCodeValidated
    fun validateEmail(email: String) {
        viewModelScope.launch {
            userAccessRepository.validateEmail(email)
        }
    }

    fun setIsEmailValidatedFalse() {
        userAccessRepository.setIsEmailValidatedFalse()
    }

    fun setisEmailAuthCodeValidatedFalse() {
        userAccessRepository.setisEmailAuthCodeValidatedFalse()
    }
    fun validateEmialAuthCode(email: String, code: String) {
        viewModelScope.launch {
            userAccessRepository.validateEmialAuthenticationCode(email, code)
        }
    }

}