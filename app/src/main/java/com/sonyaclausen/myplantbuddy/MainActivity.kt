package com.sonyaclausen.myplantbuddy

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.sonyaclausen.myplantbuddy.generic.CameraPreviewScreen
import com.sonyaclausen.myplantbuddy.ui.theme.MyPlantBuddyTheme

class MainActivity : ComponentActivity() {
    private val cameraPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Implement camera related  code
            } else {
                // Camera permission denied
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) -> {

            }
            else -> {
                cameraPermissionRequest.launch(Manifest.permission.CAMERA)
            }
        }

        enableEdgeToEdge()
        setContent {
            MyPlantBuddyTheme {
                AppNavHost(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

