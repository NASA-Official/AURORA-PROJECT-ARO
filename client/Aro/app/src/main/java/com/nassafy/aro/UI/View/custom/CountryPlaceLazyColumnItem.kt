package com.nassafy.aro.ui.view.custom

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nassafy.aro.R
import com.nassafy.aro.data.dto.PlaceTest


@Composable
fun CountryPlaceLazyColumnItem(place: PlaceTest, selectedPlaceList: MutableList<PlaceTest>) {
    //TODO change isSelected to DTO's boolean type var
    var isSelected by remember { mutableStateOf(false) }
    //TODO change order to DTO
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(100.dp)
            .clickable {
                isSelected = !isSelected
                when (isSelected) {
                    true -> {
                        selectedPlaceList.add(place)
                    }
                    false -> {
                        selectedPlaceList.remove(place)
                    }
                }
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        )
    ) {
        Column(
            Modifier
                .fillMaxWidth(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxHeight(0.95f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    //TODO change painter
                    painter = painterResource(id = com.nassafy.aro.R.drawable.green_logo),
                    contentDescription = "A country place stamp",
                    modifier = Modifier
                        .weight(2f)
                ) // End of Image
                Column(
                    modifier = Modifier
                        .weight(7f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //TODO change text
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "Place", fontSize = 20.sp,
                            color = when (isSelected) {
                                false -> colorResource(id = R.color.dark_gray)
                                true -> colorResource(id = R.color.light_dark_gray)
                            } // end of when
                        ) // End of Text
                    } // End of Box
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "description",
                            fontSize = 12.sp,
                            color = when (isSelected) {
                                false -> colorResource(id = R.color.dark_gray)
                                true -> colorResource(id = R.color.light_dark_gray)
                            }
                        )
                    } // End of Box
                } // End of Column
                Icon(
                    imageVector = Icons.Outlined.Done,
                    contentDescription = "checked",
                    tint = colorResource(id = R.color.light_dark_gray),
                    modifier = Modifier
                        .alpha(if (isSelected) 1f else 0f)
                )
            } // End of Row
            Spacer(modifier = Modifier.height(4.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(2.dp),
                color = colorResource(id = R.color.main_app_color_light),
            )
        } // End of Column
    } // End of Card
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultCountryPlaceLazyColumnItemPreview() {
//    CountryPlaceLazyColumnItem(order = 1)
//}