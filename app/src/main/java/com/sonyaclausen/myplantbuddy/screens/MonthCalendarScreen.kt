package com.sonyaclausen.myplantbuddy.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MonthCalendarScreen() {
    ScreenContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Month Calendar",
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                colors = topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ){innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Text(text = "Month Calendar")
        }
    }

}

@Composable
@Preview
fun MonthCalendarScreenPreview() {
    MonthCalendarScreen()
}