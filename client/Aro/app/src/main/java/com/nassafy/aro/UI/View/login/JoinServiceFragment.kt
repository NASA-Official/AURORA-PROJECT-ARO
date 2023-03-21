package com.nassafy.aro.ui.view.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nassafy.aro.R
import com.nassafy.aro.databinding.FragmentJoinServiceBinding
import com.nassafy.aro.ui.view.BaseFragment

class JoinServiceFragment : BaseFragment<FragmentJoinServiceBinding>(FragmentJoinServiceBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

    }

    private fun initView() {
        binding.nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_joinServiceFragment_to_joinFavoriteFragment)
        }
        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}