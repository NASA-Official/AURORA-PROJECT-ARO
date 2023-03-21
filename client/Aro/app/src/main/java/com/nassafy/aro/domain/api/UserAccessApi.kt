package com.nassafy.aro.domain.api

import com.nassafy.aro.data.dto.LoginToken
import com.nassafy.aro.data.dto.user.request.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path


interface UserAccessApi {
    @POST("api/members/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginToken> // End of login

    @POST("api/accounts/codecheck/{code}")
    suspend fun validateEmialAuthCode(
        @Path("code") authCode: Int
    ): Response<Unit>
} // End of UserAccessApi