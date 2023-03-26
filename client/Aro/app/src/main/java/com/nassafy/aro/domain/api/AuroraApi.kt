package com.nassafy.aro.domain.api

import com.nassafy.aro.data.dto.PlaceItem
import retrofit2.Response
import retrofit2.http.GET

interface AuroraApi {
    // https://j8d106.p.ssafy.io/api/

    @GET("/api/attractions/all")
    suspend fun getAllPlaces() : Response<List<PlaceItem>>


}