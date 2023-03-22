package com.nassafy.aro.domain.repository

import android.util.Log
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
        countryName: String,
        placeName: String,
        userId: Long,
        imageList: List<MultipartBody.Part?>,
        diaryContent: HashMap<String, RequestBody>

    ) {
        Log.d(TAG, "일기생성 Repositroy : 여기 까지오나? ")

        val response = headerDiaryApi.createStampDiary(
            countryName,
            placeName,
            userId,
            imageList,
            diaryContent
        )

        Log.d(TAG, "일기 생성 response: ${response}")
        Log.d(TAG, "일기 생성 response: ${response.body()}")
        Log.d(TAG, "일기 생성 response: ${response.code()}")

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
