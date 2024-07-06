package com.sonyaclausen.myplantbuddy.screens.camera

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.sonyaclausen.myplantbuddy.R

@Composable
fun CameraRecogScreen(onBackPress: () -> Unit, onCameraClick: () -> Unit, onGalleryClick: () -> Unit){
    ScreenContent(onBackPress = onBackPress, onCameraClick = onCameraClick, onGalleryClick = onCameraClick)
}

@Composable
private fun ScreenContent(onBackPress: () -> Unit, onCameraClick: () -> Unit, onGalleryClick: () -> Unit) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.camera_recog))
            Button(onClick = onBackPress) {
                Text(text = "Go back")
            }
            Button(onClick = onCameraClick) {
                Icon(
                    Icons.Outlined.CameraAlt,
                    contentDescription = stringResource(id = R.string.camera_click)
                )
            }
            Button(onClick = onGalleryClick) {
                Icon(
                    Icons.Outlined.Image,
                    contentDescription = stringResource(id = R.string.gallery_image)
                )
            }
        }
    }
}

@Preview
@Composable
fun CameraRecogScreenPreview() {
    CameraRecogScreen(onBackPress = {}, onCameraClick = {}, onGalleryClick = {})
}