package com.sonyaclausen.myplantbuddy.screens

import android.content.Context
import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sonyaclausen.myplantbuddy.R
import java.util.Locale

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Home : Screen("home", R.string.home, Icons.Outlined.Home)
    object Calender : Screen("calender", R.string.calender, Icons.Outlined.CalendarMonth)
    object Library : Screen("library", R.string.library, Icons.Outlined.LibraryBooks)
    object More : Screen("more", R.string.more, Icons.Outlined.MoreHoriz)
}

@Composable
fun MainScreen(
    onMyPlantsClick: () -> Unit,
    onCameraClick: () -> Unit,
    modifier: Modifier,
    onRouteClick: (String) -> Unit
) {
    val items = listOf(
        Screen.Home,
        Screen.Calender,
        Screen.Library,
        Screen.More,
    )
    val context = LocalContext.current
    var selectedLocale by remember { mutableStateOf(Locale.getDefault()) }

    val navController = rememberNavController()

    val activity = (LocalContext.current as? ComponentActivity)

    BackHandler {
        // Finish the activity to exit the app
        activity?.finish()
    }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            Column {
                Divider(color = MaterialTheme.colorScheme.primary, thickness = 1.dp)
                BottomAppBar(containerColor = Color.Transparent) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    items.forEach { screen ->
                        NavigationBarItem(
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = MaterialTheme.colorScheme.onSecondary
                            ),
                            icon = {
                                Icon(
                                    screen.icon,
                                    contentDescription = stringResource(id = screen.resourceId),
                                    tint = MaterialTheme.colorScheme.primary,

                                )
                            },
                            label = {
                                Text(
                                    stringResource(screen.resourceId),
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            })
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCameraClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ) {
                Icon(
                    Icons.Outlined.CameraAlt,
                    contentDescription = stringResource(id = R.string.add_plant)
                )
            }
        },
    ) { innerPadding ->
        NavHost(
            navController, startDestination = Screen.Home.route, modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onMyPlantsClick = onMyPlantsClick,
                    modifier = modifier,
                )
            }
            composable(Screen.Library.route) { LibraryScreen() }
            composable(Screen.Calender.route) { MonthCalendarScreen() }
            composable(Screen.More.route) {
                MoreOptionsScreen(selectedLocale, onClick = {
                    selectedLocale = it
                    updateLocale(context = context, locale = it)
                }, onRouteClick = onRouteClick)
            }
        }
    }
}


fun updateLocale(context: Context, locale: Locale) {
    val config = Configuration(context.resources.configuration)
    Locale.setDefault(locale)
    config.setLocale(locale)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
    context.applicationContext.createConfigurationContext(config)
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(
        onMyPlantsClick = {},
        onCameraClick = {},
        modifier = Modifier,
        onRouteClick = {}
    )
}
