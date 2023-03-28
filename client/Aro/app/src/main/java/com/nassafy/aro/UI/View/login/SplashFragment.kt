package com.nassafy.aro.ui.view.login

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.nassafy.aro.databinding.FragmentSplashBinding
import com.nassafy.aro.ui.view.BaseFragment
import com.nassafy.aro.util.SharedPreferencesUtil


class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    private lateinit var sharedPreferencesUtil: SharedPreferencesUtil // JWT 가져오기.

    // Context
    private lateinit var mContext: Context

    // viewModel
    private val SplashViewModel: SplashViewModel by viewModels()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    } // End of onAttach

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    } // End of onCreate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // JWT 토큰이 있는지 확인을 먼저하고,
        // 토큰이 있을 경우 서버에 보냄.

    } // End of onViewCreated


    /*
        토큰이 맞으면 화면을 전환함.
        AccessToken이 만료됬을 경우 RefreshToken을 줘서 다시 발급받음 RefreshToken도 만료됬으면 다시 로그인을 시킴
     */
    private fun changeViewMainActivity() {

    } // End of changeView

    private fun changeLoginView() {

    } // End of changeLoginView
} // End of SplashFragment
