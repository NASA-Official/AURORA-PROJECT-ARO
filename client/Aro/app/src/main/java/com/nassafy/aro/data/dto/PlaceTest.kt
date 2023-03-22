package com.nassafy.aro.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaceTest @JvmOverloads constructor(
    var countryName: String?,
    var placeName: String?,
    var placeImage : Int,
    var placeExplanation: String?,
    var placeDiary: PlaceDiaryTest?,
) : Parcelable { // End of PlaceTest
}