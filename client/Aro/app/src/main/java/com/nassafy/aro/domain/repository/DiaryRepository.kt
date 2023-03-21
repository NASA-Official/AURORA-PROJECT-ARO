package com.nassafy.aro.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nassafy.aro.domain.api.DiaryApi
import com.nassafy.aro.util.NetworkResult
import com.nassafy.aro.util.di.HeaderInterceptorApi
import com.nassafy.aro.util.di.WithoutHeaderInterceptorApi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject


private const val TAG = "DiaryRepository_싸피"

class DiaryRepository @Inject constructor(
    @WithoutHeaderInterceptorApi private val diaryApi: DiaryApi,
    @HeaderInterceptorApi private val headerDiaryApi: DiaryApi
) {


    // ====================================== 명소별 일지 작성 ======================================
    private val _createPlaceDiaryResponseLiveData = MutableLiveData<NetworkResult<Int>>()
    val createPlaceDiaryResponseLiveData: LiveData<NetworkResult<Int>>
        get() = _createPlaceDiaryResponseLiveData

    suspend fun createPlaceDiary(
        placeName: String,
        userId: Long,
        imageList: List<MultipartBody.Part?>,
        diaryContent: HashMap<String, RequestBody>

    ) {
        val response = diaryApi.createStampDiary(
            placeName, userId, imageList, diaryContent
        )

        _createPlaceDiaryResponseLiveData.postValue(NetworkResult.Loading())

        if (response.isSuccessful) {
            _createPlaceDiaryResponseLiveData.postValue(
                NetworkResult.Success(response.code())
            )
        } else if (response.errorBody() != null) {
            _createPlaceDiaryResponseLiveData.postValue(
                NetworkResult.Error(
                    response.errorBody()!!.string()
                )
            )
        }

    } // End of createPlaceDiary
} // End of DiaryRepository class
