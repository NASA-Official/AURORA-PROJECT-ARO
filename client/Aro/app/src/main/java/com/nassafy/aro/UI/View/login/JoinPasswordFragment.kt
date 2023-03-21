package com.nassafy.aro.ui.view.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nassafy.aro.R
import com.nassafy.aro.databinding.FragmentJoinPasswordBinding
import com.nassafy.aro.ui.view.BaseFragment
import com.nassafy.aro.ui.view.login.viewmodel.LoginActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JoinPasswordFragment : BaseFragment<FragmentJoinPasswordBinding>(FragmentJoinPasswordBinding::inflate) {

    private val loginActivityViewModel: LoginActivityViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    } // End of onViewCreated

    private fun initView() {
        binding.nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_joinPasswordFragment_to_joinNicknameFragment)
        }
        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.joinPasswordTextfield.apply {
            editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(text: Editable?) {
                    when (passwordValidate(text.toString())) {
                        true -> {
                            error = ""
                        } // End of case 6
                        false -> {
                            error = getString(R.string.password_rule_fail_textview_text)
                        } // End of case else
                    } // End of text length
                } // End of afterTextChanged
            }) // End of addTextChangedListener
        }
        binding.verificationPasswordTextfield.apply {
            editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(text: Editable?) {
                    when (text.toString() == binding.joinPasswordTextfield.editText?.text.toString()) {
                        true -> {
                            error = ""
                        } // End of case true
                        false -> {
                            error = getString(R.string.password_validate_fail_textview_text)
                        } // End of case false
                    } // End of when
                } // End of afterTextChanged
            }) // End of addTextChangedListener
        }
    }

    private fun passwordValidate(text: String): Boolean {
        var result = false
        // TODO
        return result
    }

}