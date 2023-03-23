package com.nassafy.aro.ui.view.aurora

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline
import com.nassafy.aro.R
import com.nassafy.aro.util.addInfoWindow
import kotlinx.coroutines.launch

class AuroraViewModel : ViewModel() {
    private var _clickedLocation = MutableLiveData<LatLng>()
    val clickedLocation: LiveData<LatLng>
        get() = _clickedLocation

    fun setClickedLocation(location: LatLng) {
        _clickedLocation.value = location
    } // End of setClickedLocation

} // End of AuroraViewModel