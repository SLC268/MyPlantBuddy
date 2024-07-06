package com.sonyaclausen.myplantbuddy.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.sonyaclausen.myplantbuddy.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotLoginScreen(onCancelClick: () -> Unit, onResetComplete: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.forgot_password),
                        color = MaterialTheme.colorScheme.primary
                    )
                }, colors = topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PlantsWateringAnimation()
            Spacer(modifier = Modifier.size(16.dp))
            ForgottenDetails()
            ForgottenButtons(onCancelClick = onCancelClick, onResetComplete = onResetComplete)
        }

    }
}

@Composable
private fun ForgottenDetails() {
    //TODO get actual data to fill fields an verification
    Text(text = stringResource(id = R.string.reset_password_instructions))
    Spacer(modifier = Modifier.size(10.dp))
    OutlinedTextField(
        value = "",
        onValueChange = {},
        label = { Text(text = stringResource(id = R.string.email)) },
        modifier = Modifier
            .padding(4.dp)
            .width(250.dp)
    )
}

@Composable
private fun ForgottenButtons(onCancelClick: () -> Unit, onResetComplete: () -> Unit) {
    //TODO add verification
    Row(modifier = Modifier.padding(10.dp)) {
        Button(onClick = onCancelClick) {
            Text(text = stringResource(id = R.string.cancel))

        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(onClick = onResetComplete) {
            Text(text = stringResource(id = R.string.send_reset_email))

            //TODO: toast/dialog that returns to login screen
        }
    }
}

@Composable
private fun PlantsWateringAnimation() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.watering_plants)
    )

    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier
            .padding(4.dp)
            .size(250.dp)
    )
}

@Preview
@Composable
fun ForgotLoginScreenPreview() {
    ForgotLoginScreen(onCancelClick = {}, onResetComplete = {})
}