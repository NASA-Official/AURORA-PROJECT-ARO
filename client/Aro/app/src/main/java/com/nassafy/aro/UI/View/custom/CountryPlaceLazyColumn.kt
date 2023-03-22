package com.nassafy.aro.ui.view.custom

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.nassafy.aro.data.dto.PlaceItem
import com.nassafy.aro.ui.view.ServiceViewModel

@Composable
fun CountryPlaceLazyColumn(placeList: List<PlaceItem>, selectedPlaceList: MutableList<PlaceItem>, viewModel: ServiceViewModel ) {
    LazyColumn (){
        // TODO Change items DTO List
        items(placeList) {
            CountryPlaceLazyColumnItem(it, selectedPlaceList, viewModel)
        }
//
//        items(placeList) {
//            CountryPlaceLazyColumnItem(, selectedPlaceList)
//            Log.d("ssafy", "$it")
//        }
    }
}
