package com.nassafy.aro.ui.view.custom

import android.util.Log
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CountryPlaceLazyColumn() {
    LazyColumn (){
        // TODO Change items DTO List
        items(30) {
            CountryPlaceLazyColumnItem(order = it)
            Log.d("ssafy", "$it")
        }
    }
}