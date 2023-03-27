package com.nassafy.aro.ui.view.main.stamp

import android.content.Context
import android.os.Bundle
import android.view.View
import com.nassafy.aro.databinding.FragmentStampValidateBinding
import com.nassafy.aro.ui.view.BaseFragment

private const val TAG = "StampValidateFragment_싸피"

class StampValidateFragment :
    BaseFragment<FragmentStampValidateBinding>(FragmentStampValidateBinding::inflate) {
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    } // End of onAttach

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 사진 찍어 가져오기
        binding.stampValidateButton.setOnClickListener {


        }
    } // End of onViewCreated
    

    companion object {
        private lateinit var photoPath: String
        private val INTENT_CODE = 1
        private val PERMISSION_CODE = 2
    }
} // End of StampValidateFragment class
