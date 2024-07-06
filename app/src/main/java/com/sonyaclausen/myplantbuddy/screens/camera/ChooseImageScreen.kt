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
import androidx.compose.ui.tooling.preview.Preview
import com.sonyaclausen.myplantbuddy.R

@Composable
fun ChooseImageScreen(onBackPress: () -> Unit, onChooseImageClick: () -> Unit){
    ScreenContent(onBackPress = onBackPress, onChooseImageClick = onChooseImageClick)
}

@Composable
private fun ScreenContent(onBackPress: () -> Unit, onChooseImageClick: () -> Unit){
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.choose_image))
            Button(onClick = onBackPress) {
                Text(text = "Go back")
            }
            Button(onClick = onChooseImageClick) {
                Text(text = "Choose Image")

            }
        }
    }
}

@Preview
@Composable
fun ChooseImageScreenPreview(){
    ChooseImageScreen(onBackPress = {}, onChooseImageClick = {})
}