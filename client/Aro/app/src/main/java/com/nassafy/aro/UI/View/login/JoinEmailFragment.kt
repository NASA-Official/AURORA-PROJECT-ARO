package com.nassafy.aro.ui.view.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nassafy.aro.R
import com.nassafy.aro.databinding.FragmentJoinEmailBinding
import com.nassafy.aro.ui.view.BaseFragment
import com.nassafy.aro.ui.view.login.viewmodel.LoginActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class JoinEmailFragment :
    BaseFragment<FragmentJoinEmailBinding>(FragmentJoinEmailBinding::inflate) {

    private val loginActivityViewModel: LoginActivityViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
        initView()
    } // End of onViewCreated

    override fun onResume() {
        super.onResume()
        setNextButtonAvailable(false)
    } // End of onResume

    private fun initObserve() {
        initEmailValidateObserve()
    } // End of initObserve

    private fun setNextButtonAvailable(isAvalable: Boolean) {
        binding.nextButton.isSelected = isAvalable
        binding.nextButton.isEnabled = isAvalable
        binding.nextButton.isClickable = isAvalable
    } // End of setNextButtonAvalable

    private fun initEmailValidateObserve() {
        loginActivityViewModel.isEmailValidated.observe(this.viewLifecycleOwner) {
            when (it) {
                true -> {
                    binding.joinEmailIdTextfield.error = null
                    binding.verificationEmailCodeTextfield.isVisible = true
                }
                false -> {
                    binding.joinEmailIdTextfield.error =
                        getString(R.string.join_already_exist_email_text)
                    binding.verificationEmailCodeTextfield.isVisible = false
                }
            } // End of when
        } // End of isEmailValidated.observe
        loginActivityViewModel.isEmailAuthCodeValidated.observe(this.viewLifecycleOwner) {
            when (it) {
                true -> {
                    binding.verificationEmailCodeTextfield.error = null
                    when (loginActivityViewModel.isEmailValidated.value) {
                        true -> {
                            setNextButtonAvailable(true)
                        }
                        else -> {
                            setNextButtonAvailable(false)
                        }
                    } // End of when
                } // End of true
                false -> {
                    setNextButtonAvailable(false)
                    binding.verificationEmailCodeTextfield.error =
                        getString(R.string.email_validate_number_fail_textview_text)
                } // End of false
            } // End of when
        } // End of isEmailValidated.observe
    } // End of initEmailValidateObserve

    private fun initView() {
        binding.nextButton.apply {
            isEnabled = false
            setOnClickListener {
                loginActivityViewModel.email = binding.joinEmailIdTextfield.editText?.text.toString()
                findNavController().navigate(R.id.action_joinEmailFragment_to_joinPasswordFragment)
            } // End of nextButton.setOnClickListener
        } // End of nextButton.apply
        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        } // End of cancelButton.setOnClickListener
        binding.verifyEmailTextview.setOnClickListener {
            Log.d("ssafy", "clicked")
            CoroutineScope(Dispatchers.IO).launch {
                loginActivityViewModel.validateEmail(binding.joinEmailIdTextfield.editText?.text.toString())
            }
        } // End of verifyEmailTextview.setOnClickListener

        binding.joinEmailIdTextfield.apply {
            editText?.setText(loginActivityViewModel.email)
            editText?.addTextChangedListener {
                object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun afterTextChanged(p0: Editable?) {
                        loginActivityViewModel.setIsEmailValidatedFalse()
                    }
                }
            } // End of addTextChangedListener
        } // End of joinEmailIdTextfield.apply

        binding.verificationEmailCodeTextfield.apply {
            editText?.setText("")
            editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(text: Editable?) {
                    when (text?.length) {
                        6 -> {
                            CoroutineScope(Dispatchers.IO).launch {
                                loginActivityViewModel.validateEmialAuthCode(
                                    binding.joinEmailIdTextfield.editText?.text.toString(),
                                    text.toString()
                                ) // End of return
                            } // End of CoroutineScope
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