package com.nassafy.aro.ui.view.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.nassafy.aro.databinding.ActivitySignBinding

class SignActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val uri = intent?.data

        Log.d("ssafy/sign_in/scheme", "${uri?.scheme}")

        // Oauth 로그인 인지 아닌지 확인
        when(uri?.scheme.toString()) {
            "aro-github" -> { // 깃허브
                val code = uri?.getQueryParameter("code")
                // TODO Delete Log
                Log.d("ssafy/auth/github/code", "$code")
                //TODO Get Github AccessToken, To use Retrofit
            }
            else -> {}
        } // End of when

    } // End of onNewIntent

}