package com.sonyaclausen.myplantbuddy.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sonyaclausen.myplantbuddy.R

@Composable
fun ProfileScreen( onEditClick: () -> Unit) {
    ScreenContent(onEditClick = onEditClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(onEditClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back Icon",
                        modifier = Modifier.clickable { /*TODO*/ })
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.profile),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                colors = topAppBarColors(
                    containerColor = Color.Transparent
                ), actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.DeleteOutline,
                            contentDescription = "Delete Icon",
                            tint = Color.Red
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileDetails(
                modifier = Modifier.padding(innerPadding),
                onEditClick = onEditClick
            )
        }
    }
}

@Composable
private fun ProfileDetails(modifier: Modifier = Modifier, onEditClick: () -> Unit) {

    //TODO get actual data to fill fields
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(R.drawable.profile),
            contentDescription = "Profile Picture",
            modifier = Modifier.size(200.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(text = stringResource(id = R.string.username)) },
            modifier = Modifier
                .padding(4.dp)
                .width(250.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(text = stringResource(id = R.string.email)) },
            modifier = Modifier
                .padding(4.dp)
                .width(250.dp)
        )
        ShowHidePassword()
        Button(onClick = onEditClick) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Icon"
            )
        }
    }
}

@Composable
private fun ShowHidePassword() {
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(value = false) }

    OutlinedTextField(
        modifier = Modifier
            .padding(4.dp)
            .width(250.dp),
        value = password,
        onValueChange = { newText ->
            password = newText
        },
        label = {
            Text(text = stringResource(id = R.string.password))
        },
        placeholder = { Text(text = "Type password here") },
        visualTransformation = if (showPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { showPassword = !showPassword }) {
                Icon(
                    imageVector = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = if (showPassword) "Hide password" else "Show password"
                )
            }
        }
    )
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(onEditClick = {})
}