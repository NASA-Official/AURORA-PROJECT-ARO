package com.nassafy.aro.ui.view.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nassafy.aro.data.dto.LoginToken
import com.nassafy.aro.data.dto.UserTest
import com.nassafy.aro.domain.repository.UserAccessRepository
import com.nassafy.aro.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    private val userAccessRepository: UserAccessRepository
): ViewModel() {

    val loginToken: LiveData<NetworkResult<LoginToken>>
        get() = userAccessRepository.loginToken

    suspend fun loginByIdPassword(email: String, password: String) {
        viewModelScope.launch {
            userAccessRepository.loginByIdPassword(email, password)
        }
    }
}