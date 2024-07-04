package com.sonyaclausen.myplantbuddy.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sonyaclausen.myplantbuddy.R
import com.sonyaclausen.myplantbuddy.generic.WateringCard
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun MonthCalendarScreen() {
    //TODO: remove when actual data comes in
    val events = listOf(
        WaterEvent(Date(), "Plant 1", 100),
        WaterEvent(Date(), "Plant 2", 200)
    )

    ScreenContent(events)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun ScreenContent(events: List<WaterEvent>) {
    val currentMonth = remember { mutableStateOf(Calendar.getInstance()) }
    val selectedDateEvents = remember { mutableStateOf<List<WaterEvent>>(listOf()) }

    val calendarRange = 1200
    val initialPage = calendarRange / 2

    val pagerState = rememberPagerState(initialPage = initialPage, pageCount = { calendarRange })
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.calender),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                colors = topAppBarColors(
                    containerColor = Color.Transparent
                ),
                actions = {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(
                                    initialPage
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(text = stringResource(id = R.string.today))
                    }

                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            CalendarWithEvents(
                events = events,
                onPreviousClick = { page ->
                    coroutineScope.launch {
                        pagerState.scrollToPage(
                            page - 1
                        )
                    }
                },
                onNextClick = { page ->
                    coroutineScope.launch {
                        pagerState.scrollToPage(
                            page + 1
                        )
                    }
                },
                pagerState = pagerState,
                currentMonth = currentMonth,
                selectedDateEvents = selectedDateEvents,
                initialPage = initialPage
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CalendarWithEvents(
    events: List<WaterEvent>,
    onNextClick: (Int) -> Unit,
    onPreviousClick: (Int) -> Unit,
    pagerState: PagerState,
    currentMonth: MutableState<Calendar>,
    selectedDateEvents: MutableState<List<WaterEvent>>,
    initialPage: Int,
) {
    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = pagerState,
        verticalAlignment = Alignment.Top

    ) { page ->
        val baseCalendar = currentMonth.value.clone() as Calendar
        val monthOffset = page - initialPage
        baseCalendar.add(Calendar.MONTH, monthOffset)
        val monthName =
            baseCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
        val year = baseCalendar.get(Calendar.YEAR)


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CalenderControls(onPreviousClick, onNextClick, page, monthName, year)

            CalendarGrid(events, baseCalendar, selectedDateEvents)

            EventList(selectedDateEvents.value)
        }
    }
}

@Composable
private fun CalenderControls(
    onPreviousClick: (Int) -> Unit,
    onNextClick: (Int) -> Unit,
    page: Int,
    monthName: String,
    year: Int
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onPreviousClick.invoke(page) }) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.prev_month),
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Text(
            text = "$monthName $year",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = { onNextClick.invoke(page) }) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = stringResource(id = R.string.next_month),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun CalendarGrid(
    events: List<WaterEvent>,
    currentMonth: Calendar,
    selectedDateEvents: MutableState<List<WaterEvent>>
) {
    val daysInMonth = currentMonth.getActualMaximum(Calendar.DAY_OF_MONTH)
    val firstDayOfMonth = currentMonth.clone() as Calendar

    firstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1)

    val startingDayOfWeek = firstDayOfMonth.get(Calendar.DAY_OF_WEEK) - 1
    val days = (1..daysInMonth).toList()
    val paddingDaysBefore = List(startingDayOfWeek) { -1 }
    val paddingDaysAfter = List((7 - (startingDayOfWeek + daysInMonth) % 7) % 7) { -1 }

    val allDays = paddingDaysBefore + days + paddingDaysAfter
    LazyVerticalGrid(columns = GridCells.Fixed(7)) {
        items(allDays.size) { index ->
            val day = allDays[index]
            val textColor = if (day <= 0) Color.Transparent else Color.Black
            val currentDay = if (day <= 0) "" else day.toString()

            val eventsForDay = events.filter { event ->
                val eventCalendar = Calendar.getInstance()
                eventCalendar.time = event.date
                eventCalendar.get(Calendar.DAY_OF_MONTH) == day && eventCalendar.get(
                    Calendar.MONTH
                ) == currentMonth.get(
                    Calendar.MONTH
                )
            }

            // Display dots for each event
            val eventDots = buildString {
                repeat(eventsForDay.size) {
                    append("• ")
                }
            }

            Box(
                modifier = Modifier
                    .padding(vertical = 6.dp, horizontal = 2.dp)
                    .background(
                        color = if (day > 0) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        selectedDateEvents.value = eventsForDay
                    }
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 6.dp)
                ) {
                    Text(
                        text = currentDay,
                        color = textColor,
                    )
                    Text(
                        text = eventDots,
                        color = Color.Blue,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
private fun EventList(events: List<WaterEvent>) {
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        events.forEach { event ->
            WateringCard(
                date = dateFormat.format(event.date),
                plantName = event.plantName,
                waterAmount = event.waterAmount.toString()
            )
        }
    }
}

data class WaterEvent(
    val date: Date,
    val plantName: String,
    val waterAmount: Int
)

@Preview
@Composable
fun MonthCalendarScreenPreview() {
    MonthCalendarScreen()
}

