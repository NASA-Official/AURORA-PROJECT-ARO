package com.nassafy.aro.domain.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nassafy.aro.data.dto.Diary
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


    // ===================================== 명소별 유저 diary 데이터 가져오기 =====================================
    private val _getPlaceDiaryUserDataResponseLiveData = MutableLiveData<NetworkResult<Diary>>()
    val getPlaceDiaryUserDataResponseLiveData: LiveData<NetworkResult<Diary>>
        get() = _getPlaceDiaryUserDataResponseLiveData

    suspend fun getPlaceDiaryUserData(
        countryName: String,
        placeName: String,
        userId: Long
    ) {
        val response = headerDiaryApi.getPlaceUserDiary(countryName, placeName, userId)

        _getPlaceDiaryUserDataResponseLiveData.postValue(NetworkResult.Loading())

        if (response.isSuccessful && response.body() != null) {
            _getPlaceDiaryUserDataResponseLiveData.postValue(
                NetworkResult.Success(response.body()!!)
            )
        } else if (response.errorBody() != null) {
            _getPlaceDiaryUserDataResponseLiveData.postValue(
                NetworkResult.Error(
                    response.errorBody()!!.string()
                )
            )
        }
    } // End of getPlaceDiaryUserData


    // ====================================== 명소별 일지 작성 ======================================
    private val _createPlaceDiaryResponseLiveData = MutableLiveData<NetworkResult<Int>>()
    val createPlaceDiaryResponseLiveData: LiveData<NetworkResult<Int>>
        get() = _createPlaceDiaryResponseLiveData

    suspend fun createPlaceDiary(
        countryName: String,
        placeName: String,
        userId: Long,
        newImageList: List<MultipartBody.Part?>,
        data: HashMap<String, RequestBody>
    ) {
        val response = headerDiaryApi.createStampDiary(
            countryName, placeName, userId, newImageList, data
        )

        Log.d(TAG, "response: ${response.body()}")
        Log.d(TAG, "response: ${response}")

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
