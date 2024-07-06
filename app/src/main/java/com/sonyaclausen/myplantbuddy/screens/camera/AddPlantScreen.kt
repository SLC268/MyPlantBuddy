package com.sonyaclausen.myplantbuddy.screens.camera

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sonyaclausen.myplantbuddy.R

@Composable
fun AddPlantScreen(
    onBackPress: () -> Unit,
    onAddPlantClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    ScreenContent(
        onBackPress = onBackPress,
        onAddPlantClick = onAddPlantClick,
        onCancelClick = onCancelClick
    )
}

@Composable
private fun ScreenContent(
    onBackPress: () -> Unit,
    onAddPlantClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.add_plant))
            Button(onClick = onBackPress) {
                Text(text = "Go back")
            }
            Button(onClick = onCancelClick) {
                Text(text = stringResource(id = R.string.cancel))

            }
            Button(onClick = onAddPlantClick) {
                Text(text = "Add Plant")

            }
        }
    }
}