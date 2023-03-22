package com.nassafy.aro.domain.api

import com.google.gson.JsonObject
import com.nassafy.aro.data.dto.LoginToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface UserAccessApi {
    @POST("api/members/login")
    suspend fun login(
        @Body loginBody: JsonObject
    ): Response<LoginToken> // End of login

    @POST("api/accounts/codecheck")
    suspend fun validateEmailAuthenticationCode(
        @Body validateEmailAuthenticationCodeBody: JsonObject
    ): Response<Unit>

    @POST("api/accounts/emailcheck")
    suspend fun validateEmail(
        @Body validateEmailBody: JsonObject
    ): Response<Unit>

    @GET("/api/stamps/nations")
    suspend fun getCountryList(): Response<List<String>>



} // End of UserAccessApi