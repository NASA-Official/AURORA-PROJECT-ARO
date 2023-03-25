package com.nassafy.aro.ui.view.main.mypage.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageNavFavoriteRegisterViewModel @Inject constructor(): ViewModel() {
    val isAuroraSelected = false
    val isMeteorSelected = false
}