package com.nassafy.aro.domain.api

import com.google.gson.JsonObject
import com.nassafy.aro.data.dto.LoginToken
import com.nassafy.aro.data.dto.PlaceItem
import com.nassafy.aro.data.dto.UserTest
import retrofit2.Response
import retrofit2.http.*


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

    @GET("api/stamps/nations")
    suspend fun getCountryList(): Response<List<String>>

    @GET("api/stamps/signup/{nation}")
    suspend fun getPlaceList(
        @Path("nation") nation: String
    ): Response<List<PlaceItem>>

    @POST("api/accounts/signup")
    suspend fun join(
        @Body user: UserTest
    ): Response<Unit>

} // End of UserAccessApi