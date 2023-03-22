package com.nassafy.aro.ui.view.custom

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nassafy.aro.R
import com.nassafy.aro.databinding.FragmentAroServiceSelectBinding
import com.nassafy.aro.ui.view.BaseFragment
import com.nassafy.aro.ui.view.ServiceViewModel
import com.nassafy.aro.ui.view.login.LoginActivity
import com.nassafy.aro.ui.view.login.viewmodel.LoginActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AroServiceSelectFragment: BaseFragment<FragmentAroServiceSelectBinding>(FragmentAroServiceSelectBinding::inflate) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_aroServiceSelectFragment_to_aroCountryPlaceSelectFragment)
        }
        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}