package com.nassafy.aro.data.dto

import okhttp3.MultipartBody

data class Diary @JvmOverloads constructor(
    var imageList: List<MultipartBody.Part?> = emptyList(),
    var diaryContent: String = "",
) : java.io.Serializable