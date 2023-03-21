package com.nassafy.aro.data.dto

data class LoginToken(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String
)
