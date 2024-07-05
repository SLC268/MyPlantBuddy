package com.sonyaclausen.myplantbuddy.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sonyaclausen.myplantbuddy.R
import com.sonyaclausen.myplantbuddy.generic.GridCard

@Composable
fun MyPlantsScreen(onBackPress: () -> Unit, modifier: Modifier, onCardClick: () -> Unit) {
    ScreenContent(onBackPress, onCardClick )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(onBackPress: () -> Unit, onCardClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.my_plants),
                        color = MaterialTheme.colorScheme.primary,
                    )
                }, colors = topAppBarColors(
                    containerColor = Color.Transparent
                ), navigationIcon = {
                    IconButton(
                        onClick = onBackPress,
                    ) {
                        Icon(
                            Icons.Outlined.ArrowBack, contentDescription = stringResource(
                                id = R.string.back_arrow
                            )
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            MyPlantsGrid(onCardClick)
        }
    }
}

@Composable
private fun MyPlantsGrid(
    onCardClick: () -> Unit
) {
    val image: Painter = painterResource(id = R.drawable.plant)

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(40.dp),
        verticalArrangement = Arrangement.spacedBy(34.dp)
    ) {
        items(20) {
            GridCard(image = image, title = "Rubber Plant", number = null, onCardClick)
        }
    }
}

//TODO: searchbar + filter + results


@Preview
@Composable
fun MyPlantsScreenPreview() {
    MyPlantsScreen({}, Modifier) { }
}