package com.nassafy.aro.ui.view.main.mypage

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.navigation.fragment.findNavController
import com.nassafy.aro.R
import com.nassafy.aro.data.dto.UserTest
import com.nassafy.aro.databinding.FragmentAroServiceSelectBinding
import com.nassafy.aro.ui.view.BaseFragment

class MyPageServiceRegisterFragment: BaseFragment<FragmentAroServiceSelectBinding>(FragmentAroServiceSelectBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.serviceSelectLaterGroup.isGone = true
        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_myPageServiceRegisterFragment_to_myPageFavoriteRegisterFragment)
        }
    }


}