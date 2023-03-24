package com.nassafy.aro.ui.view.main.mypage

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.nassafy.aro.R
import com.nassafy.aro.databinding.FragmentAroCountryPlaceSelectBinding
import com.nassafy.aro.ui.view.BaseFragment

class MyPageFavoriteRegisterFragment: BaseFragment<FragmentAroCountryPlaceSelectBinding>(FragmentAroCountryPlaceSelectBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserve()
        initView()
    }

    private fun initObserve() {
//        TODO("Not yet implemented")
    }

    private fun initView() {
        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.nextButton.apply {
            setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.complete_button))
            setOnClickListener {
                // todo
            }
        }
    }

}