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
        placeId: Int
    ) {
        val response = headerDiaryApi.getPlaceUserDiary(placeId)

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
        placeId: Int,
        newImageList: List<MultipartBody.Part?>,
        requestHashMap: HashMap<String, RequestBody>,
    ) {

        Log.d(TAG, "newImageList: $newImageList")

//        Log.d(TAG, "createPlaceDiary: ${dataType.get("data").toString()}")
//
//        dataType.forEach {
//            Log.d(TAG, "createPlaceDiary: ${it.key.toString()}")
//            Log.d(TAG, "createPlaceDiary: ${it.value.contentType()}")
//            Log.d(TAG, "createPlaceDiary: ${it.value.contentLength()}")
//            Log.d(
//                TAG, "createPlaceDiary: ${
//                    it.value.apply {
//
//                    }
//                }"
//            )
//        }


        val response = headerDiaryApi.createStampDiary(
            placeId,
            newImageList[0],
            requestHashMap
        )
        Log.d(TAG, "다이어리 생성 Response: $response")
        Log.d(TAG, "다이어리 생성 Response: ${response.body()} ")
        Log.d(TAG, "다이어리 생성 Response: ${response.code()}")

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
