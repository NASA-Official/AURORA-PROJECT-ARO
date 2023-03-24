package com.nassafy.aro.domain.api

import retrofit2.Response
import retrofit2.http.GET

interface MyPageApi {
    @GET("/")
    suspend fun serverCallTest(): Response<String>
}