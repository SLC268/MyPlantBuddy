package com.sonyaclausen.myplantbuddy.screens.camera

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sonyaclausen.myplantbuddy.R
import com.sonyaclausen.myplantbuddy.generic.CameraPreviewScreen
import com.sonyaclausen.myplantbuddy.generic.multipleEventsCutter

@Composable
fun CameraRecogScreen(
    onBackPress: () -> Unit,
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit
) {
    ScreenContent(
        onBackPress = onBackPress,
        onCameraClick = onCameraClick,
        onGalleryClick = onGalleryClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    onBackPress: () -> Unit,
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    multipleEventsCutter { multipleEventsCutterManager ->
                        IconButton(onClick = onBackPress) {
                            Icon(
                                Icons.Outlined.ArrowBack,
                                contentDescription = stringResource(id = R.string.back_arrow)
                            )
                        }
                    }
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.camera_recog),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                CameraPreviewScreen(onImageCaptured = {})
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CameraButtons(
                    onCameraClick = onCameraClick,
                    onGalleryClick = onGalleryClick
                )
            }
        }
    }
}

@Composable
private fun CameraButtons(
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.weight(1f)) {
            Button(onClick = onGalleryClick) {
                Icon(
                    Icons.Outlined.Image,
                    contentDescription = stringResource(id = R.string.gallery_image)
                )
            }
        }
        Box(contentAlignment = Alignment.Center, modifier = Modifier.weight(1f)) {
            Button(onClick = onCameraClick) {
                Icon(
                    Icons.Outlined.CameraAlt,
                    contentDescription = stringResource(id = R.string.camera_click)
                )
            }
        }
        Box(modifier = Modifier.weight(1f)) { }
    }
}

@Preview
@Composable
fun CameraRecogScreenPreview() {
    CameraRecogScreen(onBackPress = {}, onCameraClick = {}, onGalleryClick = {})
}