package com.nassafy.aro.ui.view.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

//Todo Change Dto
@Composable
fun ChipHasCancelButton(text: String) {
    Row(
        modifier = Modifier
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(16.dp)
            ),
    ) {
        Box(
            Modifier.height(40.dp), contentAlignment = Alignment.Center) {
            Text(
                text = text,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
        Box (Modifier.height(40.dp), contentAlignment = Alignment.Center) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Cancel",
                    tint = Color.Black,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
    }
}