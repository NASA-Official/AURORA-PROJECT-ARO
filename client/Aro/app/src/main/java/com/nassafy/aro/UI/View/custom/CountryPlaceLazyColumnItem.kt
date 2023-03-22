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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nassafy.aro.R
import com.nassafy.aro.data.dto.PlaceItem
import com.nassafy.aro.ui.view.ServiceViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun CountryPlaceLazyColumnItem(place: PlaceItem, selectedPlaceList: MutableList<PlaceItem>, viewModel: ServiceViewModel) {
    //TODO change isSelected to DTO's boolean type var

    var isSelected by remember { mutableStateOf(false) }

    DisposableEffect(place) {
        isSelected = selectedPlaceList.contains(place)
        onDispose {  }
    }
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
                        viewModel.selectAuroraPlace(place)
                    }
                    false -> {
                        viewModel.unSelectAuroraPlace(place)
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
                GlideImage( // CoilImage, FrescoImage
                    imageModel = { place.stamp },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    ),
                    modifier = Modifier
                        .weight(2f),
                    // shows an error text if fail to load an image.
                    failure = {
                        Text(text = "image request failed.")
                    })

                Column(
                    modifier = Modifier
                        .weight(7f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //TODO change text
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = place.placeName, fontSize = 20.sp,
                            color = when (isSelected) {
                                false -> colorResource(id = R.color.dark_gray)
                                true -> colorResource(id = R.color.light_dark_gray)
                            } // end of when
                        ) // End of Text
                    } // End of Box
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = place.description,
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