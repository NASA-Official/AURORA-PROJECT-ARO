package com.nassafy.aro.ui.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nassafy.aro.data.dto.PlaceItem
import com.nassafy.aro.data.dto.PlaceTest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

abstract class ServiceViewModel: ViewModel() {
    abstract var isAuroraServiceSelected: Boolean
    abstract var isMeteorServiceSelected: Boolean
    abstract val selectedAuroraPlaces: LiveData<MutableList<PlaceItem>>
    abstract val selectedMeteorPlaces: LiveData<MutableList<PlaceItem>>
    abstract fun selectAuroraPlace(placeItem: PlaceItem)
    abstract fun unSelectAuroraPlace(placeItem: PlaceItem)
}