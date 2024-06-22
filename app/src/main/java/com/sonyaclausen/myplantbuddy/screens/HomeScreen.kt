package com.sonyaclausen.myplantbuddy.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Schedule
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
        modifier = modifier,
        waterToday = 3,
        waterScheduled = 5
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(
    onMyPlantsClick: () -> Unit,
    onCameraClick: () -> Unit,
    modifier: Modifier = Modifier,
    waterToday: Int,
    waterScheduled: Int
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.home),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                colors = topAppBarColors(
                    containerColor = Color.Transparent
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
            TodayAndScheduledCards(waterToday = waterToday, waterScheduled = waterScheduled)

            MyPlantsBar(
                onClick = onMyPlantsClick
            )

            WaterStreakAnimation()
            WaterStreakText()
        }

    }
}

//@Composable
//private fun TodayAndScheduledCards(totalWaterings: Int, titleId: Int, modifier: Modifier = Modifier) {
//    val title = stringResource(id = titleId)
//    val iconCondition =
//        if (title == stringResource(id = R.string.today)) Icons.Default.CalendarToday else Icons.Default.Schedule
//
//    Card(
//        modifier = Modifier,
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.secondaryContainer,
//            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
//        )
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(4.dp),
//            verticalArrangement = Arrangement.SpaceBetween,
//        ) {
//            Row {
//                CircleBackgroundIcon(
//                    icon = iconCondition,
//                    modifier = Modifier
//                        .fillMaxWidth(0.4f)
//                        .aspectRatio(1f)
//                        .background(
//                            MaterialTheme.colorScheme.onPrimaryContainer,
//                            shape = CircleShape
//                        )
//                )
//                Text(
//                    text = totalWaterings.toString(),
//                    fontWeight = FontWeight.Medium,
//                    style = MaterialTheme.typography.titleLarge
//                )
//            }
//            Text(
//                text = title,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp),
//                textAlign = TextAlign.Center,
//                fontWeight = FontWeight.Normal,
//                style = MaterialTheme.typography.titleLarge
//            )
//        }
//    }
//}
//
//@Composable
//private fun ShowTSCards(
//    modifier: Modifier = Modifier,
//    waterToday: Int,
//    waterScheduled: Int
//) {
//    val list = listOf(
//        R.string.today to waterToday,
//        R.string.scheduled to waterScheduled
//    )
//
//    Row(
//        modifier = modifier,
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        list.forEachIndexed { index, (titleId, totalWaterings) ->
//            TodayAndScheduledCards(
//                totalWaterings = totalWaterings,
//                titleId = titleId,
//                modifier = Modifier
//                    .weight(1f)
//                    .aspectRatio(1f)
//            )
//            if (index == 0) {
//                Spacer(Modifier.width(25.dp))
//            }
//        }
//    }
//
//}

@Composable
private fun TodayAndScheduledCards(
    waterToday: Int,
    waterScheduled: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Today card
        Card(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    CircleBackgroundIcon(
                        icon = Icons.Default.CalendarToday,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .background(
                                MaterialTheme.colorScheme.onPrimaryContainer,
                                shape = CircleShape
                            )
                        //fillMaxWidth(0.4f)
                    )
                    Text(
                        text = waterToday.toString(),
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                Row {
                    Text(
                        text = stringResource(id = R.string.today),
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(8.dp)
                    )
                }

            }
        }

        Spacer(modifier = Modifier.width(25.dp))

        // Scheduled card
        Card(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    CircleBackgroundIcon(
                        icon = Icons.Default.Schedule,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .background(
                                MaterialTheme.colorScheme.onPrimaryContainer,
                                shape = CircleShape
                            )
                            .fillMaxWidth(0.4f)
                    )
                    Text(
                        text = waterScheduled.toString(),
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                Row {
                    Text(
                        text = stringResource(id = R.string.scheduled),
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
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
                .padding(8.dp),
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
private fun CircleBackgroundIcon(icon: ImageVector, modifier: Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.fillMaxSize(0.5f) // Icon takes 50% of the Box size
        )
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
