package com.nassafy.aro.domain.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


interface StampApi {

    // ======================================= 테스트 api =======================================
    @GET("/api/stamps/nations")
    suspend fun getCountryTest() : Response<List<String>>








    // ======================================= 테스트 api =======================================

    @JvmSuppressWildcards
    @GET("/test")
    suspend fun getUserStampGroupByCountryStampData(userId: String, country: String): Response<Void>

    // 유저별 국가 스탬프 데이터 가져오기

} // End of StampApi