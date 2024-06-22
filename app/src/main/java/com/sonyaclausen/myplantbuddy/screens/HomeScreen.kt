package com.sonyaclausen.myplantbuddy.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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


@Composable
fun HomeScreen(
    onMyPlantsClick: () -> Unit,
    onCameraClick: () -> Unit,
    modifier: Modifier
) {
    ScreenContent(
        onMyPlantsClick = onMyPlantsClick,
        onCameraClick = onCameraClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(
    onMyPlantsClick: () -> Unit,
    onCameraClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.home))
                },
                colors = topAppBarColors(
                    containerColor = Color.Transparent,
                )

            )
        },
        floatingActionButton = {
            Button(
                onClick = onCameraClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = MaterialTheme.shapes.large,
                contentPadding = PaddingValues(16.dp)
            ) {
                Icon(Icons.Outlined.CameraAlt, contentDescription = "Add plant")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column {
                Row {
                    Text(
                        text = stringResource(id = R.string.home_greeting),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary
                    )

                }
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary,
                    thickness = 3.dp
                )
            }
            /*
            TODO get actual data and two cards
             */
            val list = listOf(
                "Today",
                "Scheduled"
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                list.forEach {
                    TodayAndScheduledCards()
                }

            }

            MyPlantsBar(
                onClick = onMyPlantsClick
            )

            WaterStreakAnimation()
            WaterStreakText()
        }

    }
}

@Composable
private fun TodayAndScheduledCards() {
    Card(
        modifier = Modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row() {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.CalendarToday, contentDescription = "Today")
                }
                Text(text = "XX")
            }
            Text(text = "Today")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyPlantsBar(onClick: () -> Unit) {
    Card(
        modifier = Modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.my_plants), fontWeight = FontWeight.Bold)
                Icon(Icons.Default.ArrowForward, contentDescription = "To My plants")
            }

        }
    }
}

@Composable
private fun WaterStreakAnimation() {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.water_streak)
    )

    LottieAnimation(
        composition = composition,
        modifier = Modifier.fillMaxWidth(),
        iterations = LottieConstants.IterateForever
    )
}

@Composable
private fun WaterStreakText() {
    val waterings = 3
    Text(
        text = stringResource(id = R.string.watering_message, waterings),
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
    )
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onMyPlantsClick = { /*TODO*/ },
        onCameraClick = {},
        modifier = Modifier.fillMaxWidth()
    )
}
