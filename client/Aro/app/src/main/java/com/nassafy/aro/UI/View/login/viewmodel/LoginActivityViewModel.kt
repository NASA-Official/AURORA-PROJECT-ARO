package com.nassafy.aro.ui.view.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.nassafy.aro.data.dto.LoginToken
import com.nassafy.aro.data.dto.PlaceItem
import com.nassafy.aro.data.dto.UserTest
import com.nassafy.aro.domain.repository.UserAccessRepository
import com.nassafy.aro.ui.view.ServiceViewModel
import com.nassafy.aro.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginActivityViewModel @Inject constructor(
    private val userAccessRepository: UserAccessRepository
) : ServiceViewModel() {

    var email: String = ""
    var password: String = ""
    var nickname: String = ""
    override var isAuroraServiceSelected: Boolean = false
    override var isMeteorServiceSelected: Boolean = false
    override val selectedAuroraPlaces: LiveData<MutableList<PlaceItem>> get() = userAccessRepository.selectedAuraraPlaceListLiveData
    override val selectedMeteorPlaces: LiveData<MutableList<PlaceItem>> get() = userAccessRepository.selectedMeteorPlaceListLiveData

    val loginToken: LiveData<NetworkResult<LoginToken>>
        get() = userAccessRepository.loginToken
    val isEmailValidated: LiveData<Boolean>
        get() = userAccessRepository.isEmailValidated
    val isEmailAuthCodeValidated: LiveData<Boolean>
        get() = userAccessRepository.isEmailAuthCodeValidated
    val countryListLiveData: LiveData<NetworkResult<List<String>>>
        get() = userAccessRepository.countryListLiveData
    val placeListLiveData: LiveData<NetworkResult<List<PlaceItem>>>
        get() = userAccessRepository.placeListLiveData
    val userJoinNetworkResultLiveData: LiveData<NetworkResult<Unit>>
        get() = userAccessRepository.userJoinNetworkResultLiveData

    suspend fun loginByIdPassword(email: String, password: String) {
        viewModelScope.launch {
            userAccessRepository.loginByIdPassword(email, password)
        }
    }

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

    fun getCountryList() {
        viewModelScope.launch {
            userAccessRepository.getCountryList()
        }
    }

    fun getPlaceList(nation: String) {
        viewModelScope.launch {
            userAccessRepository.getPlaceList(nation)
        }
    }

    fun join(user: UserTest) {
        viewModelScope.launch {
            userAccessRepository.join(user)
        }
    }

    override fun selectAuroraPlace(place: PlaceItem) {
        viewModelScope.launch {
            userAccessRepository.selectAuroraPlace(place)
        }
    }

    override fun unSelectAuroraPlace(place: PlaceItem) {
        viewModelScope.launch {
            userAccessRepository.unSelectAuroraPlace(place)
        }
    }

    fun clearSelectedService() {
        userAccessRepository
    }

    fun clearSelectedAuroraPlaceList() {
        userAccessRepository.clearSelectedAuroraPlaceList()
    }

    fun clearSelectedMeteorPlaceList() {
        userAccessRepository.clearSelectedMeteorPlaceList()
    }

}