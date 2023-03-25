package com.nassafy.aro.ui.view.main.mypage

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.nassafy.aro.R
import com.nassafy.aro.databinding.FragmentAroServiceSelectBinding
import com.nassafy.aro.ui.view.BaseFragment
import com.nassafy.aro.ui.view.main.mypage.viewmodel.MyPageNavFavoriteRegisterViewModel
import com.nassafy.aro.ui.view.main.mypage.viewmodel.MyPageServiceRegisterFragmentViewModel
import com.nassafy.aro.util.NetworkResult
import com.nassafy.aro.util.showSnackBarMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyPageServiceRegisterFragment: BaseFragment<FragmentAroServiceSelectBinding>(FragmentAroServiceSelectBinding::inflate) {

    private val myPageServiceRegisterFragementViewModel: MyPageServiceRegisterFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: MyPageServiceRegisterFragmentArgs by navArgs()
        myPageServiceRegisterFragementViewModel.auroraService = args.auroraService
        myPageServiceRegisterFragementViewModel.meteorService = args.meteorService

        initObserve()
        initView()
    }

    private fun initObserve() {
        myPageServiceRegisterFragementViewModel.selectServiceNetworkresultLiveData.observe(this.viewLifecycleOwner) {
            when(it) {
                is NetworkResult.Success -> {
                    requireView().showSnackBarMessage(getString(R.string.service_modify_success_text))
                    when (myPageServiceRegisterFragementViewModel.auroraService) {
                        true -> {
                            findNavController().navigate(R.id.action_myPageServiceRegisterFragment_to_myPageFavoriteRegisterFragment)
                        }
                        false -> {
                            when (myPageServiceRegisterFragementViewModel.meteorService) {
                                true -> {
//                                     TODO
                                }
                                false -> {
                                    findNavController().navigate(R.id.action_myPageServiceRegisterFragment_to_myPageFragment)
                                }
                            }
                        }
                    }
                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Loading -> {

                }
            }
        }
    }

    private fun initView() {
        binding.serviceSelectLaterGroup.isGone = true
        binding.auroraServiceCardview.isSelected = myPageServiceRegisterFragementViewModel.auroraService
        binding.meteorServiceCardview.isSelected = myPageServiceRegisterFragementViewModel.meteorService

        //커스텀 뷰는 setOnClickListener가 안먹힘;;; //TODO 나아아중에 개선
//        binding.auroraServiceCardview.setOnClickListener {
//            Log.d("ssafy1", "why didn't be clicked?")
//        }

        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.nextButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                myPageServiceRegisterFragementViewModel.auroraService = binding.auroraServiceCardview.getIsSelected()
                myPageServiceRegisterFragementViewModel.meteorService = binding.meteorServiceCardview.getIsSelected()
                myPageServiceRegisterFragementViewModel.selectService(
                    myPageServiceRegisterFragementViewModel.auroraService,
                    myPageServiceRegisterFragementViewModel.meteorService
                )
            }
        }
    }


}