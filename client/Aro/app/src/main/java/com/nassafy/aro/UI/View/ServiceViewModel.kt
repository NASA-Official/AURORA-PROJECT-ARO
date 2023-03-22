package com.nassafy.aro.ui.view

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

abstract class ServiceViewModel: ViewModel() {
    abstract var isAuroraServiceSelected: Boolean
    abstract var isMeteorServiceSelected: Boolean
    abstract var selectedAuroraPlaces: List<Int>
    abstract var selectedMeteorPlaces: List<Int>
}