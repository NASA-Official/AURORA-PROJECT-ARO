package com.nassafy.aro.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaceItem(
    @SerializedName("attractionName") val placeName: String,
    @SerializedName("attractionId") val placeId: Int,
    @SerializedName("description") val description: String,
    @SerializedName("stamp") val stamp: String,
    @SerializedName("mapImage") val mapImage : String,
    @SerializedName("latitude") val latitude : Float,
    @SerializedName("longitude") val longitude : Float,
    val interest: Boolean = false,
    val interestId: Long = 0
) : Parcelable