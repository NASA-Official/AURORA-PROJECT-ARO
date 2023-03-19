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
import com.nassafy.aro.BuildConfig.GITHUB_CLIENT_ID
import com.nassafy.aro.R
import com.nassafy.aro.databinding.FragmentSignInBinding
import com.nassafy.aro.util.githubLoginUri

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

    private fun initView() {
        binding.signUpTextview.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpEmailFragment2)
        }
        binding.signInGithubImagebutton.setOnClickListener {
            githubLogin()
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