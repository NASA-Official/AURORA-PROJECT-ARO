package com.nassafy.aro.domain.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MyPageApi {
    @POST("api/members/changenickname/{nickname}")
    suspend fun changeNickname(
        @Path("nickname") nickname: String
    ): Response<Unit>



}