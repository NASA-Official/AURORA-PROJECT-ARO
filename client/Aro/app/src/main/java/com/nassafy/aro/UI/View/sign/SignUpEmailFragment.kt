package com.nassafy.aro.ui.view.sign

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.nassafy.aro.R
import com.nassafy.aro.databinding.FragmentSignInBinding
import com.nassafy.aro.databinding.FragmentSignUpEmailBinding

class SignUpEmailFragment : Fragment() {

    private var _binding: FragmentSignUpEmailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpEmailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

    }

    private fun initView() {
        binding.nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_signUpEmailFragment2_to_signUpPasswordFragment)
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
                            when (text.toString() == verifyMsg) {
                                true -> {
                                    binding.verificationEmailCodeTextfield.error = ""
                                } // End of case true
                                false -> {
                                    binding.verificationEmailCodeTextfield.error =
                                        getString(R.string.email_validate_number_fail_textview_text)
                                } // End of case false
                            } // End of when
                        } // End of case 6
                        else -> {
                            binding.verificationEmailCodeTextfield.error = ""
                        } // End of case else
                    } // End of text length
                } // End of afterTextChanged
            }) // End of addTextChangedListener
        } // End of verificationEmailCodeTextfield.apply
    } // End of initView

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    } // End of onDestroyView

}