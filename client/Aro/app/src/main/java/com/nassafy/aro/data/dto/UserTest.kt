package com.nassafy.aro.data.dto

data class UserTest(
    val auroraPlaces: List<String>,
    val auroraService: Boolean,
    val email: String,
    val meteorPlaces: List<String>,
    val meteorService: Boolean,
    val nickname: String,
    val password: String,
    val alarm: Boolean = true
)