package com.nassafy.aro.ui.view.custom

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.nassafy.aro.data.dto.PlaceItem
import com.nassafy.aro.ui.view.ServiceViewModel

@Composable
fun CountryPlaceLazyColumn(placeList: List<PlaceItem>, selectedPlaceList: MutableList<PlaceItem>, viewModel: ServiceViewModel ) {

    var allImageLoadedState by remember { mutableStateOf(mutableStateListOf<Boolean>()) }
    DisposableEffect(key1 = allImageLoadedState, key2 = placeList) {
        Log.d("ssafy_pcs", "list_changed")
        when (allImageLoadedState.size == placeList.size) {
            true -> {
                Log.d("ssafy_pcs", "all image loaded")
            }
            false -> {

            }
        }
        onDispose {  }
    }

    LazyColumn (
        Modifier.fillMaxWidth(0.9f)
            ){
        // TODO Change items DTO List
        items(placeList) {
            CountryPlaceLazyColumnItem(it, selectedPlaceList, viewModel, allImageLoadedState)
        }
//
//        items(placeList) {
//            CountryPlaceLazyColumnItem(, selectedPlaceList)
//            Log.d("ssafy", "$it")
//        }
    }
}
