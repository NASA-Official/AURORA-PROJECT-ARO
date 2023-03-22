package com.nassafy.aro.data.dto

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaceTest @JvmOverloads constructor(
    var placeName: String?,
    var placeImage: Int,
    var placeExplanation: String?,
    var placeDiary: PlaceDiaryTest?,
    var isChecked: Boolean? = false
) : Parcelable { // End of PlaceTest
}