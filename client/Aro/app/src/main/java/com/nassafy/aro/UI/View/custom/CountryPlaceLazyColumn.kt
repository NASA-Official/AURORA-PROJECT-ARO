package com.nassafy.aro.ui.view.custom

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nassafy.aro.data.dto.PlaceTest

@Composable
fun CountryPlaceLazyColumn(placeList: MutableList<PlaceTest>, selectedPlaceList: MutableList<PlaceTest> ) {
    LazyColumn (){
        // TODO Change items DTO List
        items(placeList) {
            CountryPlaceLazyColumnItem(it, selectedPlaceList)
        }
//
//        items(placeList) {
//            CountryPlaceLazyColumnItem(, selectedPlaceList)
//            Log.d("ssafy", "$it")
//        }
    }
}
