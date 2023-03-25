package com.nassafy.aro.domain.api

import com.google.gson.JsonObject
import com.nassafy.aro.data.dto.FavoriteList
import com.nassafy.aro.data.dto.UserTest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MyPageApi {
    @POST("api/members/changenickname/{nickname}")
    suspend fun changeNickname(
        @Path("nickname") nickname: String
    ): Response<Unit>

    @GET("api/members/service/all")
    suspend fun getSelectedService(): Response<UserTest>

    @GET("api/interest")
    suspend fun getFavoriteList(): Response<FavoriteList>

    @POST("api/members/service")
    suspend fun selectService(@Body requestBody: JsonObject): Response<Unit>


}