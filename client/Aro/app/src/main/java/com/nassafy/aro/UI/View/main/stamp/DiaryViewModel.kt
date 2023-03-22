package com.nassafy.aro.ui.view.main.stamp

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nassafy.aro.data.dto.Diary
import com.nassafy.aro.domain.repository.DiaryRepository
import com.nassafy.aro.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.*
import javax.inject.Inject


private const val TAG = "DiaryViewModel_싸피"

@HiltViewModel
class DiaryViewModel @Inject constructor(private val diaryRepository: DiaryRepository) :
    ViewModel() {

    // diary viewModel Data
    private val _diaryData: Diary = Diary()
    val diaryData: Diary
        get() = _diaryData

    // 유저가 선택한 이미지
    private val _selectImageLiveData = MutableLiveData<Uri>()
    val selectImageLiveData: LiveData<Uri>
        get() = _selectImageLiveData


    // 선택한 이미지 리스트
    private val _selectImageListLiveData = MutableLiveData<LinkedList<Uri>>()
    val selectImageListLiveData: MutableLiveData<LinkedList<Uri>>
        get() = _selectImageListLiveData

    fun selectImageListAddImage(iamgeUri: Uri) {
        selectImageListLiveData.value!!.add(iamgeUri)
    }

    fun selectImageListRemoveImage(index: Int) {
        selectImageListLiveData.value!!.removeAt(index)
    }

    // ===================================== 명소별 유저 diary 데이터 가져오기 =====================================
    val getPlaceDiaryUserDataResponseLiveData: LiveData<NetworkResult<Diary>>
        get() = diaryRepository.getPlaceDiaryUserDataResponseLiveData

    suspend fun getPlaceDiaryUserData(
        countryName: String, placeName: String, userId: Long
    ) {
        viewModelScope.launch {
            diaryRepository.getPlaceDiaryUserData(countryName, placeName, userId)
        }
    }

    // ===================================== 명소별 다이어리 생성 =====================================
    val createPlaceDiaryResponseLiveData: LiveData<NetworkResult<Int>>
        get() = diaryRepository.createPlaceDiaryResponseLiveData

    suspend fun createPlaceDiary(
        countryName: String,
        placeName: String,
        userId: Long,
        deleteImageList: List<String>,
        newImageList: List<MultipartBody.Part?>,
        memo: String
    ) {
        var requestHashMap: HashMap<String, RequestBody> = HashMap()
        requestHashMap["memo"] = memo.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        requestHashMap["deleteImageList"] = deleteImageList.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())


        viewModelScope.launch {
            diaryRepository.createPlaceDiary(
                countryName, placeName, userId, newImageList, requestHashMap
            )
        }
    } // End of createPlaceDiary
} // End of DiaryViewModel

