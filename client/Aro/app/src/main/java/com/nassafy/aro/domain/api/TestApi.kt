package com.nassafy.aro.domain.api

import retrofit2.Response
import retrofit2.http.GET

interface TestApi {

    @GET("/")
    suspend fun serveerCallTest(): Response<String>
} // End of TestApi inferface
