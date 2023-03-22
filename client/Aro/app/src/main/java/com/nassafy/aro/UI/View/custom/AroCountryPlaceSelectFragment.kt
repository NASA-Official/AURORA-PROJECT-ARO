package com.nassafy.aro.ui.view.custom

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import com.nassafy.aro.R
import com.nassafy.aro.data.dto.PlaceTest
import com.nassafy.aro.databinding.FragmentAroCountryPlaceSelectBinding
import com.nassafy.aro.ui.adapter.CountrySpinnerAdapter
import com.nassafy.aro.ui.view.BaseFragment

open class AroCountryPlaceSelectFragment : BaseFragment<FragmentAroCountryPlaceSelectBinding>(FragmentAroCountryPlaceSelectBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    } // End of onViewCreated

    fun initComposeView(placeList: MutableList<PlaceTest>, selectedPlaceList: MutableList<PlaceTest>) {
        binding.countryPlaceComposeview.apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                // In Compose world
                MaterialTheme {
                    var _selectedPlaceList by remember {
                        mutableStateOf(selectedPlaceList)
                    }
                    Column(
                        modifier = Modifier
                            .height(this.height.dp)
                    ) {
                        CountryPlaceChips(_selectedPlaceList)
                        Divider(
                            modifier = Modifier.height(2.dp),
                            color = Color.White
                        ) // End of Divider
                        CountryPlaceLazyColumn(placeList, _selectedPlaceList)
                    } // End of Column
                }
            }
        }
        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }
    } // End of initView

    fun initSpinner(countryList: ArrayList<String>) {
        val adapter =
            CountrySpinnerAdapter(requireContext(), R.layout.item_country_spinner, countryList)
        binding.selectCountryPlaceSpinner.adapter = adapter
        binding.selectCountryPlaceSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    // TODO Item Select Func
                    Log.d("ssafy", "$position item_selected!")
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            } // End of onItemSelectedListener
    } // End of initSpinner
}
//
//@Preview(showBackground = true)
//@Composable
//fun preview() {
//    Column(
//        modifier = Modifier
//            .height(200.dp)
//    ) {
//        CountryPlaceChips()
//        Divider(
//            modifier = Modifier.height(2.dp),
//            color = Color.White
//        )
//        CountryPlaceLazyColumn()
//    }
//}