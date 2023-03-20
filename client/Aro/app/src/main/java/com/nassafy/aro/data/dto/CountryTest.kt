package com.nassafy.aro.data.dto

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryTest @JvmOverloads constructor(
    var countryName: String?,
    var placeList: List<PlaceTest>?,
) : Parcelable