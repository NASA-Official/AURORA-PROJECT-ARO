package com.nassafy.aro.data.dto.user.request

data class LoginRequest(
    val email: String,
    val password: String
)

data class JoinRequestDto(
    val email: String
)
