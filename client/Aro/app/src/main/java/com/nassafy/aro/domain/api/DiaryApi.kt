package com.nassafy.aro.domain.api


import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

private const val TAG = "DiaryApi_싸피"

interface DiaryApi {
    // 일지 개별 스템프 기록하기
    // https://j8d106.p.ssafy.io/api/stamps/1/diary/3
    @Multipart
    @JvmSuppressWildcards
    @POST("/api/stamps/{placeName}/diary/{userId}")
    suspend fun createStampDiary(
        placeName: String,
        userId: Long,
        @Part imageList: List<MultipartBody.Part?>,
        @PartMap diaryContent: HashMap<String, RequestBody>
    ): Response<Void>

} // End of DiaryApi interface
