package com.nassafy.aro.ui.view.main.stamp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nassafy.aro.domain.repository.StampRepository
import com.nassafy.aro.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StampHomeViewModel @Inject constructor(private val stampRepository: StampRepository) :
    ViewModel() {

    /*
        처음에 국가 DTO를 가져와서 국가이름이랑, 이미지를 List를 받아온다면?
     */

    // ==================================== 전체 국가 리스트 가져오기 ====================================
    val getAllNationListResponseLiveData: LiveData<NetworkResult<List<String>>>
        get() = stampRepository.getAllNationListResponseLiveData

    suspend fun getAllNationList() {
        viewModelScope.launch {
            stampRepository.getAllNationList()
        }
    } // End of getAllNationList

    // 국가를 Key로 해당 국가 데이터와 유저 데이터 가져오기


} // End of StampHomeViewModel class
