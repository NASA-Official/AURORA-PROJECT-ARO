package com.nassafy.aro.domain.repository

import com.nassafy.aro.util.NetworkResult

interface UserAccessRepository {
    suspend fun loginByIdPassword(id: String, password: String)
    suspend fun validateEmialAuthCode(code: Int): Boolean
}