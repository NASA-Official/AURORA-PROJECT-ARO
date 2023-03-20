package com.nassafy.aro.ui.view.custom

import com.nassafy.aro.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nassafy.aro.databinding.FragmentAroCountryPlaceSelectBinding
import com.nassafy.aro.ui.adapter.CountrySpinnerAdapter

class AroCountryPlaceSelectFragment: Fragment() {
    private var _binding: FragmentAroCountryPlaceSelectBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAroCountryPlaceSelectBinding.inflate(inflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initSpinner()
    } // End of onViewCreated

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    } // End of onDestroyView

    private fun initView() {
        binding.countryPlaceComposeview.apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                // In Compose world
                MaterialTheme {
                    Column(modifier = Modifier
                        .height(this.height.dp)) {
                        CountryPlaceChips()
                        Divider(
                            modifier = Modifier.height(2.dp),
                            color = Color.White
                        ) // End of Divider
                        CountryPlaceLazyColumn()
                    } // End of Column
                }
            }
        }
//        binding.nextButton.setOnClickListener {
//        }
        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }
    } // End of initView

    fun initSpinner() {
        val countryPlaceList = arrayOf("place 1", "place 2", "place 3")
        val adapter = CountrySpinnerAdapter(requireContext(), R.layout.item_country_spinner, countryPlaceList)
        binding.selectCountryPlaceSpinner.adapter = adapter
        binding.selectCountryPlaceSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d("ssafy", "$position item_selected!")
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        } // End of onItemSelectedListener
    } // End of initSpinner
}

@Preview(showBackground = true)
@Composable
fun preview() {
    Column(modifier = Modifier
        .height(200.dp)) {
        CountryPlaceChips()
        Divider(
            modifier = Modifier.height(2.dp),
            color = Color.White
        )
        CountryPlaceLazyColumn()
    }
}