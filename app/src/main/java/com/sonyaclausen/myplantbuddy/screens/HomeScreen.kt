package com.sonyaclausen.myplantbuddy.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.sonyaclausen.myplantbuddy.R
import com.sonyaclausen.myplantbuddy.generic.GridCard


@Composable
fun HomeScreen(
    onMyPlantsClick: () -> Unit, modifier: Modifier
) {
    ScreenContent(
        onMyPlantsClick = onMyPlantsClick, modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    onMyPlantsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.home),
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
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
        ) {
            Column(modifier = Modifier.padding(vertical = 24.dp)) {
                Row {
                    Text(
                        text = stringResource(id = R.string.home_greeting),
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        color = MaterialTheme.colorScheme.primary,

                        )
                }
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary,
                    thickness = 3.dp
                )
            }

            CardGrid()

            MyPlantsBar(
                onClick = onMyPlantsClick
            )

            Box(contentAlignment = Alignment.Center) {
                WaterStreakAnimation()
                WaterStreakText(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 30.dp)
                )
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyPlantsBar(onClick: () -> Unit) {
    Card(
        modifier = Modifier.padding(top = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ), onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.my_plants), fontWeight = FontWeight.Bold)
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = stringResource(id = R.string.to_my_plants)
                )
            }

        }
    }
}

@Composable
private fun CardGrid() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(40.dp),
    ) {
        //TODO: get data from viewmodel for numbers
        item {
            GridCard(
                image = Icons.Outlined.CalendarMonth,
                title = stringResource(id = R.string.today),
                number = 3,
                onClick = {}
            )
        }
        item {
            GridCard(
                image = Icons.Outlined.Schedule,
                title = stringResource(id = R.string.scheduled),
                number = 5,
                onClick = {}
            )
        }
    }
}


@Composable
private fun WaterStreakAnimation() {
    //TODO: add logic to switch between blue and red streaks
    val selected = remember { mutableStateOf(true) }
    val compositionBlue by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.water_streak_blue)
    )

    val compositionRed by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.water_streak_red)
    )

    LottieAnimation(
        composition = if (selected.value) compositionBlue else compositionRed,
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .clickable {
                selected.value = !selected.value
            },
        iterations = LottieConstants.IterateForever,
    )
}

@Composable
private fun WaterStreakText(modifier: Modifier = Modifier) {
    val waterings = 3 //TODO: get from viewmodel
    Text(
        text = stringResource(id = R.string.watering_message, waterings),
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = modifier
    )
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onMyPlantsClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()
    )
}
