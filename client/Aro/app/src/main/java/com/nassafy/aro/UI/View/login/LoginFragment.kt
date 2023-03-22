package com.nassafy.aro.ui.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nassafy.aro.Application
import com.nassafy.aro.BuildConfig.*
import com.nassafy.aro.R
import com.nassafy.aro.databinding.FragmentLoginBinding
import com.nassafy.aro.ui.view.BaseFragment
import com.nassafy.aro.ui.view.main.MainActivity
import com.nassafy.aro.ui.view.login.viewmodel.LoginActivityViewModel
import com.nassafy.aro.util.NetworkResult
import com.nassafy.aro.util.githubLoginUri
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val loginActivityViewModel: LoginActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Application.sharedPreferencesUtil.apply {
            when(getUserAccessToken()) {
                "" -> {}
                else -> { //토큰이 있으면
                    // TODO 자동로그인
                    startMainActivity()
                } // End of else
            } // End of when
        } // End of apply
    } // End of onCreate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserve()
        initView()

    }

    private fun naverLogin() {
        val oAuthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                // 네이버 로그인 API 호출 성공 시 유저 정보를 가져온다
                NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
                    override fun onSuccess(result: NidProfileResponse) {
                        val naverAccessToken = NaverIdLoginSDK.getAccessToken()

                        Log.d("ssafy/auth/naver", "naverAccessToken: $naverAccessToken")

                        // 성공으로 AccessToken이 넘어왔을 때, 통신 시작.
                        CoroutineScope(Dispatchers.IO).launch {
                            //TODO retrofit
//                            tokenViewModel.postNaverAccessToken(naverAccessToken.toString())
                        }
                        //TODO retrofit
//                        email = result.profile?.email.toString()
                    }

                    override fun onError(errorCode: Int, message: String) {
                        //
                    }

                    override fun onFailure(httpStatus: Int, message: String) {
                        //
                    }
                })
            }

            override fun onError(errorCode: Int, message: String) {
                val naverAccessToken = NaverIdLoginSDK.getAccessToken()
                Log.e("ssafy/login/naver", "naverAccessToken : $naverAccessToken")
            }

            override fun onFailure(httpStatus: Int, message: String) {
                //
            }
        }


        NaverIdLoginSDK.initialize(requireContext(), NAVER_CLIENT_ID, NAVER_CLIENT_SECRET, "ARO")
        NaverIdLoginSDK.authenticate(requireContext(), oAuthLoginCallback)
    } // End of naverLogin

    private fun initObserve() {
        initJoinObserve()
    } // End of initObserve

    private fun startMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun initJoinObserve() {
        loginActivityViewModel.loginToken.observe(this.viewLifecycleOwner) {
            when(it) {
                is NetworkResult.Success -> {
                    val data = it.data
                    Application.sharedPreferencesUtil.addUserAccessToken(data?.accessToken ?: "" )
                    Application.sharedPreferencesUtil.addUserRefreshToken(data?.refreshToken ?: "" )

//                    startMainActivity()
                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Loading -> {

                }
            }
        }
    }

    private fun initView() {
        binding.joinTextview.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_joinEmailFragment)
        }
        binding.loginNaverImagebutton.setOnClickListener {
            naverLogin()
        }
        binding.loginGithubImagebutton.setOnClickListener {
            githubLogin()
        }
        binding.loginButton.setOnClickListener {

            // TODO loginByIdPassword
            CoroutineScope(Dispatchers.IO).launch {
                loginActivityViewModel.loginByIdPassword(
                    binding.loginEmailIdEdittext.text.toString(),
                    binding.loginPasswordEdittext.text.toString()
                )
            }

        }
    } // ENd of initView

    fun githubLogin() {
        startActivity(Intent(Intent.ACTION_VIEW, githubLoginUri).apply {
            addCategory(Intent.CATEGORY_BROWSABLE)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    } // End of githubLogin
}