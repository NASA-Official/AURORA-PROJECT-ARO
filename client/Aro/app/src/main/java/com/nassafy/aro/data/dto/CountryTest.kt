package com.nassafy.aro.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryTest @JvmOverloads constructor(
    var placeId: Int,
    var stampImage: String,
    var isVisited: Boolean
) : Parcelable