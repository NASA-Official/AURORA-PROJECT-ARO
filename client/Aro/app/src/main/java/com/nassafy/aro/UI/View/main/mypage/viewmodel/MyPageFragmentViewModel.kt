package com.nassafy.aro.ui.view.main.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nassafy.aro.domain.repository.MyPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageFragmentViewModel @Inject constructor(private val myPageRepository: MyPageRepository) : ViewModel() {
    val nicknameLiveData get() = myPageRepository.nicknameLiveData

    suspend fun changeNickname(nickname: String) {
        viewModelScope.launch {
            myPageRepository.changeNickname(nickname = nickname)
        }
    }

}