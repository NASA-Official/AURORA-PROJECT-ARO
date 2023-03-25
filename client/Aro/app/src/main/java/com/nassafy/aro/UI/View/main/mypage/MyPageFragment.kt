package com.nassafy.aro.ui.view.main.mypage

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nassafy.aro.R
import com.nassafy.aro.databinding.FragmentMyPageBinding
import com.nassafy.aro.ui.view.BaseFragment
import com.nassafy.aro.ui.view.dialog.OkDialog
import com.nassafy.aro.ui.view.main.MainActivityViewModel
import com.nassafy.aro.ui.view.main.mypage.viewmodel.MyPageFragmentViewModel
import com.nassafy.aro.ui.view.main.mypage.viewmodel.MyPageNavFavoriteRegisterViewModel
import com.nassafy.aro.util.NetworkResult
import com.nassafy.aro.util.showSnackBarMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::inflate) {

    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    private val myPageFragmentViewModel: MyPageFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
        initView()
    } // End of onViewCreated

    private fun initObserve() {
        myPageFragmentViewModel.nicknameLiveData.observe(this.viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    mainActivityViewModel.nickname = binding.userNicknameEdittext.text.toString()
                    binding.nicknameChangeSaveAlertImagebutton.isVisible = true
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(500)
                        binding.nicknameChangeSaveAlertImagebutton.isGone = true
                    }
                }
                is NetworkResult.Error -> {
                    requireView().showSnackBarMessage("닉네임 재설정에 실패했습니다.")
                }
                is NetworkResult.Loading -> {

                }
            } // End of when
        } // End of nicknameLiveData.observe
    } // End of initObserve

    private fun initView() {
        binding.userNicknameEdittext.setText(mainActivityViewModel.nickname)
        binding.userEmailTextview.text = mainActivityViewModel.email
        binding.nicknameChangeImagebutton.apply {
            setOnClickListener {
                when (isSelected) {
                    true -> {
                        val afterChangedNickname = binding.userNicknameEdittext.text.toString()
                        when (mainActivityViewModel.nickname != afterChangedNickname) {
                            true -> {
                                when(afterChangedNickname.length in 3..10) {
                                    true -> {
                                        CoroutineScope(Dispatchers.IO).launch {
                                            myPageFragmentViewModel.changeNickname(binding.userNicknameEdittext.text.toString())
                                        }
                                    }
                                    false -> {
                                        val okDialog = OkDialog(
                                            getString(R.string.my_page_nickname_modify_error_dialog_title_textview_text),
                                            getString(R.string.my_page_nickname_modify_error_dialog_inform_textview_text),
                                            getString(R.string.dialog_confirm_button_text)
                                        )
                                        okDialog.show(
                                            childFragmentManager, "OkDialog"
                                        )
                                        binding.userNicknameEdittext.setText(mainActivityViewModel.nickname)
                                    }
                                } // End of afterChangedNickname.length in 3..10
                            }
                            false -> {}
                        }  // End of when(mainActivityViewModel.nickname != binding.userNicknameEdittext.text.toString())
                    } // End of isSelected == true
                    false -> {

                    } // End of isSelected == false
                } // End of when(isSelected)
                isSelected = !isSelected
                binding.userNicknameEdittext.isEnabled = isSelected
            }
        } // End of initView
        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        } // End of cancelButton.setOnClickListener
        binding.serviceRegistButton.setOnClickListener {
            findNavController().navigate(R.id.action_myPageFragment_to_myPageServiceRegisterFragment)
        } // End of serviceRegistButton.setOnClickListener
        binding.myFavoriteAddImageButton.setOnClickListener {
            findNavController().navigate(R.id.action_myPageFragment_to_myPageServiceRegisterFragment)
        } // End of myFavoriteAddImageButton.setOnClickListener
        binding.serviceModifyButton.setOnClickListener {
            findNavController().navigate(R.id.action_myPageFragment_to_myPageServiceRegisterFragment)
        } // End of serviceModifyButton.setOnClickListener
    } // End of initVIew
}