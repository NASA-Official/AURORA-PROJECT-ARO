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

private const val TAG = "AuroraViewModel_sdr"
class AuroraViewModel : ViewModel() {
    private var _clickedLocation = MutableLiveData<LatLng>()
    val clickedLocation: LiveData<LatLng>
        get() = _clickedLocation

    fun setClickedLocation(location: LatLng) {
        Log.d(TAG, "setClickedLocation: 2 $location")
        _clickedLocation.value = location
    }

    fun polylineClickAction(polyline: Polyline, map: GoogleMap, location: LatLng, kp: Float) {
        viewModelScope.launch {
            Log.d(TAG, "polylineClickActionC: ${clickedLocation.value}")
            Log.d(TAG, "polylineClickActionL: $location")
            if (clickedLocation.value != null) {
                Log.d(TAG, "polylineClickAction: 4")
                polyline.addInfoWindow(map, location, "KP 지수", "$kp")
            }
        }
    }

}