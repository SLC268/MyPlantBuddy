package com.sonyaclausen.myplantbuddy.screens.calendar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun MonthCalendarScreen() {
    //TODO: remove when actual data comes in
    val events = listOf(
        Event(Date(), "Event 1"),
        Event(Date(), "Event 2")
    )

    ScreenContent(events)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(events: List<Event>) {
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
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            CalendarWithEvents(events = events)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CalendarWithEvents(events: List<Event>) {
    val currentMonth = remember { mutableStateOf(Calendar.getInstance()) }
    val selectedDateEvents = remember { mutableStateOf<List<Event>>(listOf()) }

    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = rememberPagerState(initialPage = 0,pageCount = { Int.MAX_VALUE / 2 }),
        verticalAlignment = Alignment.Top
    ) { page ->
        val month = currentMonth.value.clone() as Calendar
        month.add(Calendar.MONTH, page - Int.MAX_VALUE / 2)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display month and year
            val monthName = month.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
            val year = month.get(Calendar.YEAR)
            Text(
                text = "$monthName $year",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            CalenderControls(currentMonth, page)

            CalendarGrid(events, month, selectedDateEvents)

            EventList(selectedDateEvents.value)
        }
    }
}

@Composable
private fun CalenderControls(currentMonth: MutableState<Calendar>, currentPage: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = {
            currentMonth.value.add(Calendar.MONTH, -1)
        }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Previous Month")
        }
        IconButton (onClick = {
            currentMonth.value.add(Calendar.MONTH, 1)
        }) {
            Icon(Icons.Default.ArrowForward, contentDescription = "Next Month")
        }
    }
}

@Composable
private fun CalendarGrid(events: List<Event>, currentMonth: Calendar, selectedDateEvents: MutableState<List<Event>>) {
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
            val currentDay = if (day <= 0) "" else day.toString()            // Determine the events for this day
            val eventsForDay = events.filter { event ->
                val eventCalendar = Calendar.getInstance()
                eventCalendar.time = event.date
                eventCalendar.get(Calendar.DAY_OF_MONTH) == day && eventCalendar.get(Calendar.MONTH) == currentMonth.get(
                    Calendar.MONTH
                )
            }

            // Display dots for each event
            val eventDots = buildString {
                repeat(eventsForDay.size) {
                    append("• ")
                }
            }

            // Display the day and event dots
            Column(
                modifier = Modifier
                    .padding(2.dp)
                    .clickable {
                        // On day click, update selected date events
                        selectedDateEvents.value = eventsForDay
                    }, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = currentDay,
                    color = textColor,
                )
                Text(
                    text = eventDots,
                    color = Color.Blue,
                )
            }
        }
    }
}

@Composable
private fun EventList(events: List<Event>) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        events.forEach { event ->
            Text(text = event.description)
        }
    }
}



data class Event(val date: Date, val description: String)

@Preview
@Composable
fun MonthCalendarScreenPreview() {
    MonthCalendarScreen()
}

