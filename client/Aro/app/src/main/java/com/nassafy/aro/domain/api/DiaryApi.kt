package com.nassafy.aro.domain.api


import com.nassafy.aro.data.dto.Diary
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

private const val TAG = "DiaryApi_싸피"

interface DiaryApi {

    // 명소별 일기 가져오기
    // https://j8d106.p.ssafy.io/api/stamps/diary/미국/페어뱅크스/3
    @JvmSuppressWildcards
    @GET("/api/stamps/diary/{countryName}/{placeName}/{userId}")
    suspend fun getPlaceUserDiary(
        @Path("countryName") countryName: String,
        @Path("placeName") placeName: String,
        @Path("userId") userId: Long,
    ): Response<Diary>  // End of getPlaceUserDiary


    // 일지 개별 스템프 작성 및 수정
    // https://j8d106.p.ssafy.io/api/stamps/diary/미국/페어뱅크스/3
    @Multipart
    @JvmSuppressWildcards
    @POST("/api/stamps/diary/{countryName}/{placeName}/{userId}")
    suspend fun createStampDiary(
        @Path("countryName") countryName: String,
        @Path("placeName") placeName: String,
        @Path("userId") userId: Long,
        @Part newImageList: List<MultipartBody.Part?>,
        @PartMap data: HashMap<String, RequestBody>
    ): Response<Void>
} // End of DiaryApi interface
