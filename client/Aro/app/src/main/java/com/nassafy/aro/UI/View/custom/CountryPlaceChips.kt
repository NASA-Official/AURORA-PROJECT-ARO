package com.nassafy.aro.ui.view.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CountryPlaceChips() {
    LazyRow(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth(1f)
    ) {
        //TODO change items
        items(30) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(8.dp)
                    .height(40.dp)
            ) {
                ChipHasCancelButton("Chip $it")
            }

        } // End of items
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultCountryPlaceChipsPreview() {
    CountryPlaceChips()
}