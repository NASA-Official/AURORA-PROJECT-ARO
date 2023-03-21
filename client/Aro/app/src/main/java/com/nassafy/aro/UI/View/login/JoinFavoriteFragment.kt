package com.nassafy.aro.ui.view.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nassafy.aro.databinding.FragmentJoinFavoriteBinding
import com.nassafy.aro.ui.view.BaseFragment

class JoinFavoriteFragment : BaseFragment<FragmentJoinFavoriteBinding>(FragmentJoinFavoriteBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.nextButton.setOnClickListener {
        }
        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}