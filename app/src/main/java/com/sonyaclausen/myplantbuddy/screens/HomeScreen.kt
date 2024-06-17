package com.sonyaclausen.myplantbuddy.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(onMyPlantsClick: () -> Unit, modifier: Modifier) {
    Column {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Home screen")
            Button(onClick = onMyPlantsClick) {
                Text(text = "Go to my plants")
            }
        }
    }
}
