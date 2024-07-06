package com.sonyaclausen.myplantbuddy.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
        WaterEvent(Date(), "Plant 2", 200),
        WaterEvent(Calendar.getInstance().apply { set(2024, 7, 11) }.time, "Plant 3", 200),
        WaterEvent(Date(), "Plant 4", 200),
        WaterEvent(Calendar.getInstance().apply { set(2024, 7, 22) }.time, "Plant 5", 200),
        WaterEvent(Date(), "Plant 6", 200),
        WaterEvent(date = Date(2024, Calendar.JULY, 16), "Plant 7", 200),
    )

    ScreenContent(events)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun ScreenContent(events: List<WaterEvent>) {
    val currentMonth = remember { mutableStateOf(Calendar.getInstance()) }
    val selectedDateEvents = remember { mutableStateOf<List<WaterEvent>>(listOf()) }
    val selectedDate = remember { mutableStateOf(-1) }

    val calendarRange = 1200
    val initialPage = calendarRange / 2

    val pagerState = rememberPagerState(initialPage = initialPage, pageCount = { calendarRange })
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.calendar),
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
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
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
                initialPage = initialPage,
                selectedDate = selectedDate
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
    selectedDate: MutableState<Int>
) {
    var expanded by remember { mutableStateOf(false) }
    val source = remember { MutableInteractionSource() }

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
                .padding(start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CalenderControls(onPreviousClick, onNextClick, page, monthName, year)

//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .animateContentSize()
//                    .height(if (expanded) 400.dp else 300.dp)
//                    .clickable(
//                        interactionSource = source,
//                        indication = null
//                    ) {
//                        expanded = !expanded
//                    }
//
//            ) {
                CalendarGrid(events, baseCalendar, selectedDateEvents, selectedDate)
//            }

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalendarGrid(
    events: List<WaterEvent>,
    currentMonth: Calendar,
    selectedDateEvents: MutableState<List<WaterEvent>>,
    selectedDate: MutableState<Int>
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
            val selected = selectedDate.value == day
            val displayCell = day > 0
            val textColor = if (!displayCell) Color.Transparent else if (selected) MaterialTheme.colorScheme.onPrimary else Color.Black
            val currentDay = if (!displayCell) "" else day.toString()

            val eventsForDay = events.filter { event ->
                val eventCalendar = Calendar.getInstance()
                eventCalendar.time = event.date

                eventCalendar.get(Calendar.DAY_OF_MONTH) == day &&
                        eventCalendar.get(Calendar.MONTH) == currentMonth.get(Calendar.MONTH)
            }

            val boxBackground =
                if (displayCell && selected) MaterialTheme.colorScheme.primary else
                    if (displayCell) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent

            if (displayCell) Card(
                onClick = {
                    selectedDate.value = day
                    selectedDateEvents.value = eventsForDay
                },
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = boxBackground,
                    contentColor = textColor
                ),
                modifier = Modifier
                    .padding(vertical = 6.dp, horizontal = 2.dp)
                    .fillMaxSize(),
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
                    if (eventsForDay.isNotEmpty()) {
                        Icon(
                            Icons.Outlined.WaterDrop,
                            contentDescription = stringResource(id = R.string.water_amount),
                            tint = if (selected) MaterialTheme.colorScheme.onPrimary else Color.Blue,
                            modifier = Modifier
                                .size(12.dp)
                                .padding(top = 2.dp)
                        )
                    } else {
                        Spacer(modifier = Modifier.size(12.dp))
                    }
                }
            }
        }
    }
    DisposableEffect(currentMonth) {
        onDispose {
            selectedDate.value = -1
            selectedDateEvents.value = listOf()
        }
    }
}

@Composable
private fun EventList(events: List<WaterEvent>) {
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    LazyColumn(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(events) { event ->
            WateringCard(
                date = dateFormat.format(event.date),
                plantName = event.plantName,
                waterAmount = event.waterAmount.toString()
            )
            Spacer(modifier = Modifier.padding(8.dp))
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
