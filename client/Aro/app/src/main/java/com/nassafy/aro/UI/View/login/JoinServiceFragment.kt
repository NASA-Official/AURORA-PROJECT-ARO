package com.nassafy.aro.ui.view.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.nassafy.aro.R
import com.nassafy.aro.databinding.FragmentAroServiceSelectBinding
import com.nassafy.aro.ui.view.BaseFragment
import com.nassafy.aro.ui.view.login.viewmodel.LoginActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JoinServiceFragment : BaseFragment<FragmentAroServiceSelectBinding>(FragmentAroServiceSelectBinding::inflate) {

    private val loginActivityViewModel: LoginActivityViewModel by activityViewModels()

    override fun onResume() {
        super.onResume()
        binding.auroraServiceCardview.isSelected = loginActivityViewModel.isAuroraServiceSelected
        binding.meteorServiceCardview.isSelected = loginActivityViewModel.isMeteorServiceSelected
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.nextButton.setOnClickListener {
            loginActivityViewModel.apply {
                isAuroraServiceSelected = binding.auroraServiceCardview.isSelected
                isMeteorServiceSelected = binding.meteorServiceCardview.isSelected
            }
            findNavController().navigate(R.id.action_joinServiceFragment_to_joinCountryPlaceSelectFragment)
        }
        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}