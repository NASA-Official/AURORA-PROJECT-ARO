package com.nassafy.aro.ui.view.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nassafy.aro.data.dto.UserTest
import com.nassafy.aro.domain.repository.UserAccessRepository
import com.nassafy.aro.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JoinCountryPlaceSelectFragmentViewModel @Inject constructor(
    private val userAccessRepository: UserAccessRepository
): ViewModel(){
    val userJoinNetworkResultLiveData: LiveData<NetworkResult<Unit>>
        get() = userAccessRepository.userJoinNetworkResultLiveData

    fun join(user: UserTest) {
        viewModelScope.launch {
            userAccessRepository.join(user)
        }
    }
}