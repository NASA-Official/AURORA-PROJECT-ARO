package com.nassafy.aro.ui.view.main.mypage

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.nassafy.aro.R
import com.nassafy.aro.databinding.FragmentAroServiceSelectBinding
import com.nassafy.aro.ui.view.BaseFragment
import com.nassafy.aro.ui.view.main.mypage.viewmodel.MyPageNavFavoriteRegisterViewModel

class MyPageServiceRegisterFragment: BaseFragment<FragmentAroServiceSelectBinding>(FragmentAroServiceSelectBinding::inflate) {

    private val myPageNavFavoriteRegisterViewModel: MyPageNavFavoriteRegisterViewModel by navGraphViewModels(R.id.nav_favorite_regist)

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