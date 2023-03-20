package com.nassafy.aro.ui.view.sign

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nassafy.aro.BuildConfig.*
import com.nassafy.aro.R
import com.nassafy.aro.databinding.FragmentSignInBinding
import com.nassafy.aro.ui.view.main.MainActivity
import com.nassafy.aro.util.githubLoginUri
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    private fun initView() {
        binding.signUpTextview.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpEmailFragment2)
        }
        binding.signInGithubImagebutton.setOnClickListener {
            githubLogin()
        }
        binding.signInGithubImagebutton.apply {
            naverLogin()
        }
        binding.signInButton.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    } // ENd of initView

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun githubLogin() {
        startActivity(Intent(Intent.ACTION_VIEW, githubLoginUri).apply {
            addCategory(Intent.CATEGORY_BROWSABLE)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    } // End of githubLogin
}