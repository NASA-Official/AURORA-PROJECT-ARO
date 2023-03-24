package com.nassafy.aro.data.dto

import com.google.gson.annotations.SerializedName

data class PlaceItem(
    @SerializedName("attractionName") val placeName: String,
    @SerializedName("attraction_id") val placeId: Int,
    @SerializedName("description") val description: String,
    @SerializedName("stamp") val stamp: String,
    var isChecked: Boolean = false
)