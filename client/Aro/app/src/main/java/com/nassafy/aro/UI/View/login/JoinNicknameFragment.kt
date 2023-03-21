package com.nassafy.aro.ui.view.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nassafy.aro.R
import com.nassafy.aro.databinding.FragmentJoinNicknameBinding
import com.nassafy.aro.ui.view.custom.AroServiceSelectFragmentArgs
import com.nassafy.aro.ui.view.custom.AroServiceSelectFragmentDirections

class JoinNicknameFragment : Fragment() {


    private var _binding: FragmentJoinNicknameBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJoinNicknameBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {

        binding.nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_joinNicknameFragment_to_aroServiceSelectFragment)
        }
        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.joinNicknameIdTextfield.apply {
            editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(text: Editable?) {
                    when (text.toString().length in 3..10) {
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

}