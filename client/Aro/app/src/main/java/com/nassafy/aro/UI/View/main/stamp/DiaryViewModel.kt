package com.nassafy.aro.ui.view.main.stamp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nassafy.aro.data.dto.Diary
import com.nassafy.aro.domain.repository.DiaryRepository
import com.nassafy.aro.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

private const val TAG = "DiaryViewModel_싸피"

@HiltViewModel
class DiaryViewModel @Inject constructor(private val diaryRepository: DiaryRepository) :
    ViewModel() {

    // diary viewModel Data
    private val _diaryData: Diary = Diary()
    val diaryData: Diary
        get() = _diaryData


    // ===================================== 명소별 다이어리 생성 =====================================
    val createPlaceDiaryResponseLiveData: LiveData<NetworkResult<Int>>
        get() = diaryRepository.createPlaceDiaryResponseLiveData

    suspend fun createPlaceDiary(
        placeName: String,
        userId: Long,
        diaryData: Diary,
    ) {
        val requestHashMap: HashMap<String, RequestBody> = HashMap()

        requestHashMap["memo"] =
            diaryData.diaryContent.toRequestBody("multipart/form-data".toMediaTypeOrNull())

        viewModelScope.launch {
            diaryRepository.createPlaceDiary(
                placeName, userId, diaryData.imageList, requestHashMap
            )
        }
    } // End of createPlaceDiary
} // End of DiaryViewModel
