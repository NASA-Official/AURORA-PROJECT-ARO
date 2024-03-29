package com.nassafy.aro.ui.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nassafy.aro.data.dto.UserData
import com.nassafy.aro.data.dto.UserWholeData
import com.nassafy.aro.domain.repository.MainRepository
import com.nassafy.aro.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MainActivityViewModel_sdr"

@HiltViewModel

class MainActivityViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {
    private val _userWholeData = MutableLiveData<UserWholeData>()
    val userWholeData: LiveData<UserWholeData>
        get() = _userWholeData

    fun setUserWholeData(setNewUserData: UserWholeData) {
        _userWholeData.value = setNewUserData
    } // End of userWholeData


    var nickname: String = ""
    var email: String = ""
    var alarmOption: Boolean = false
    var auroraDisplayOption: Boolean = true
    var cloudDisplayOption: Boolean = true
    var auroraServiceEnabled: Boolean = false
    var meteorShowerServiceEnabled: Boolean = false

    val logoutNetworkResultLiveData get() = mainRepository.logoutNetworkResultLiveData
    val userInfo: LiveData<NetworkResult<UserData>> get() = mainRepository.userInfo
    val getAlarmOptionNetworkResultLiveData get() = mainRepository.getAlarmOptionNetworkResultLiveData
    val getAuroraOptionNetworkResultLiveData get() = mainRepository.getAuroraDisplayOptionNetworkResultLiveData
    val getCloudOptionNetworkResultLiveData get() = mainRepository.getCloudDisplayOptionNetworkResultLiveData

    fun logout(grantType: String, accessToken: String, refreshToken: String) {
        viewModelScope.launch {
            mainRepository.logout(grantType, accessToken, refreshToken)
        }
    }

    suspend fun getUserInfo(fcmToken: String) {
        viewModelScope.launch {
            mainRepository.getUserInfo(fcmToken)
        }
    } // End of getUserInfo

    suspend fun getAlarmOption() {
        viewModelScope.launch {
            mainRepository.getAlarmOption()
        }
    } // End of getAlarmOption

    suspend fun getAuroraDisplayOption() {
        viewModelScope.launch {
            mainRepository.getAuroraDisplayOption()
        }
    } // End of getAuroraDisplayOption

    suspend fun getCloudDisplayOption() {
        mainRepository.getCloudDisplayOption()
    } // End of getAuroraDisplayOption

} // End of MainActivityViewModel class
