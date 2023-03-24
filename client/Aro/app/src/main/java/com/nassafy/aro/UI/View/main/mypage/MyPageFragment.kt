package com.nassafy.aro.ui.view.main.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nassafy.aro.R
import com.nassafy.aro.databinding.FragmentMyPageBinding
import com.nassafy.aro.ui.view.BaseFragment

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.myFavoriteAddImageButton.setOnClickListener {
            findNavController().navigate(R.id.action_myPageFragment_to_myPageServiceRegisterFragment)
        }
        binding.serviceModifyButton.setOnClickListener {
            findNavController().navigate(R.id.action_myPageFragment_to_myPageServiceRegisterFragment)
        }
    }

}