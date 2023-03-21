package com.nassafy.aro.ui.view.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nassafy.aro.R
import com.nassafy.aro.databinding.FragmentJoinEmailBinding
import com.nassafy.aro.ui.view.BaseFragment
import com.nassafy.aro.ui.view.login.viewmodel.LoginActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class JoinEmailFragment : BaseFragment<FragmentJoinEmailBinding>(FragmentJoinEmailBinding::inflate) {

    private val loginActivityViewModel: LoginActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

    }

    private fun initView() {
        binding.nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_joinEmailFragment_to_joinPasswordFragment)
        } // End of nextButton.setOnClickListener
        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        } // End of cancelButton.setOnClickListener
        binding.verifyEmailTextview.setOnClickListener {
            //todo Verify Email Logic
            binding.verificationEmailCodeTextfield.isVisible=true
        } // End of verifyEmailTextview.setOnClickListener
        binding.verificationEmailCodeTextfield.apply {
            //Change verify Str
            val verifyMsg = "000000"
            editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(text: Editable?) {
                    when (text?.length) {
                        6 -> {
                            CoroutineScope(Dispatchers.Main).launch {
                                val result = async(Dispatchers.IO) {
                                    return@async loginActivityViewModel.validateEmialAuthCode(
                                        text.toString().toInt() ?: 0)
                                } // End of await
                                when (result.await()) {
                                    true -> {
                                        binding.verificationEmailCodeTextfield.error = ""
                                    } // End of case true
                                    false -> {
                                        binding.verificationEmailCodeTextfield.error =
                                            getString(R.string.email_validate_number_fail_textview_text)
                                    } // End of case false
                                } // End of when
                            }
                        } // End of case 6
                        else -> {
                            binding.verificationEmailCodeTextfield.error = ""
                        } // End of case else
                    } // End of text length
                } // End of afterTextChanged
            }) // End of addTextChangedListener
        } // End of verificationEmailCodeTextfield.apply
    } // End of initView

}