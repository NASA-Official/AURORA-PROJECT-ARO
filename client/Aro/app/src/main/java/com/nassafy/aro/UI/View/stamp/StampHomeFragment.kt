package com.nassafy.aro.ui.view.stamp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.nassafy.aro.R
import com.nassafy.aro.Util.showToastMessage
import com.nassafy.aro.databinding.FragmentStampHomeBinding

private const val TAG = "StampHomeFragment_싸피"

class StampHomeFragment : Fragment() {
    private lateinit var mContext: Context
    private lateinit var binding: FragmentStampHomeBinding
    private lateinit var stampCountryPlaceCustomView: StampCountryPlaceCustomView
    private var countryPlace: String = ""

    private lateinit var makeDiaryButton : AppCompatButton

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    } // End of onAttach

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            countryPlace = "fdse"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStampHomeBinding.inflate(inflater, container, false)
        return binding.root
    } // End of onCreateView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stampCountryPlaceCustomView = binding.stampCountryPlaceCustomViewLayout
        makeDiaryButton = stampCountryPlaceCustomView.findViewById<AppCompatButton>(R.id.stamp_country_custom_make_diary_button)

        makeDiaryButton.setOnClickListener {
            mContext.showToastMessage("버튼 클릭됨")
        }

    } // End of onViewCreated

    companion object {
        private const val COUNTRY_PLACE = "CountryPlace"

        fun newInstance(string: String) = StampHomeFragment().apply {
            arguments = Bundle().apply {
                putString(COUNTRY_PLACE, string)
            }
        }
    }

} // End of StampHomeFragment class