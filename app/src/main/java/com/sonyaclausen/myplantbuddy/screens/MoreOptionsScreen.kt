package com.sonyaclausen.myplantbuddy.screens


//import androidx.compose.ui.text.intl.Locale
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.util.Locale

@Composable
fun MoreOptionsScreen(selectedLocale: Locale, onClick: (Locale) -> Unit) {
    val list = listOf(Locale("da"), Locale("en"))

    Scaffold(topBar = {
        Text(
            text = "More screen (current language ${selectedLocale.toLanguageTag()})"
        )
    }) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(it)
        ) {
            list.forEach {
                Button(onClick = {
                    onClick(it)
                }) {
                    Text(text = it.toLanguageTag())
                }
            }
        }
    }
}