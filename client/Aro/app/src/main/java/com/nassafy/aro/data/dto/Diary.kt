package com.nassafy.aro.data.dto

import java.util.*

data class Diary @JvmOverloads constructor(
    var imageList: LinkedList<String> = LinkedList(),
    var memeo: String = "",
    var nation: String? = "",
    var placeName: String? = "",
    var description: String? = "",
) : java.io.Serializable