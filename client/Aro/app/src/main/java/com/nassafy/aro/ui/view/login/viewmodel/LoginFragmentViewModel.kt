package com.nassafy.aro.ui.view.login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nassafy.aro.data.dto.GithubAccessTokenResponse
import com.nassafy.aro.data.dto.TokenResponse
import com.nassafy.aro.domain.repository.UserAccessRepository
import com.nassafy.aro.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    private val userAccessRepository: UserAccessRepository
) : ViewModel() {

    val loginToken: LiveData<NetworkResult<TokenResponse>>
        get() = userAccessRepository.loginToken

    val userSnsLoginNetworkResultLiveData get() = userAccessRepository.userSnsLoginNetworkResultLiveData

    suspend fun loginByIdPassword(providerType: String, email: String, password: String?) {
        viewModelScope.launch {
            userAccessRepository.loginByIdPassword(providerType, email, password)
        }
    }

    suspend fun snsLogin(providerType: String, accessToken: String) {
        val job = viewModelScope.launch {
            userAccessRepository.snsLogin(providerType, accessToken)
        }
        job.join()
    }

    suspend fun getAccessToken(code: String): String {
        var networkResult: NetworkResult<GithubAccessTokenResponse> = NetworkResult.Loading()
        val job = viewModelScope.launch {
            networkResult = userAccessRepository.getAccessToken(code)
        }
        job.join()
        var accessToken = ""
        when (networkResult) {
            is NetworkResult.Success -> {
                accessToken = networkResult.data!!.access_token
            }
            is NetworkResult.Error -> {}
            is NetworkResult.Loading -> {}
        }
        return accessToken
    }

}