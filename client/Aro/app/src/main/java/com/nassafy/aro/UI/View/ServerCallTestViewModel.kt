package com.nassafy.aro.ui.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nassafy.aro.domain.repository.TestRepository
import com.nassafy.aro.util.NetworkResult
import kotlinx.coroutines.launch

private const val TAG = "ServerCallTestViewModel_싸피"
class ServerCallTestViewModel : ViewModel() {

    // repository
    private val testRepository = TestRepository()

    // ======================== 서버 호출 테스트 ========================
    val getServerCallTestResponseLiveData: LiveData<NetworkResult<String>>
        get() = testRepository.getServerCallTestResponseLiveData

    suspend fun getServerCallTest() {
        Log.d(TAG, "getServerCallTest: 통신 viewModel 호출")
        viewModelScope.launch {
            testRepository.getServerCallTest()
        }
    } // End of getServerCallTest
} // End of TestViewModel