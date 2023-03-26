package com.nassafy.aro.data.dto.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherMain(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
) : Parcelable