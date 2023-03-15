package com.nassafy.aro.data.dto

import android.os.Parcel
import android.os.Parcelable


data class Country @JvmOverloads constructor(
    var countryName: String?,
    var placeName : String?,
    var image: Int,
    var countryDetail: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(countryName)
        parcel.writeString(placeName)
        parcel.writeInt(image)
        parcel.writeString(countryDetail)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Country> {
        override fun createFromParcel(parcel: Parcel): Country {
            return Country(parcel)
        }

        override fun newArray(size: Int): Array<Country?> {
            return arrayOfNulls(size)
        }
    }
}