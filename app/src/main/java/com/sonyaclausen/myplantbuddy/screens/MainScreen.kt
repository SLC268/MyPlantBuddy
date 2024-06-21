package com.sonyaclausen.myplantbuddy.screens

import android.content.Context
import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sonyaclausen.myplantbuddy.R
import java.util.Locale

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Home : Screen("home", R.string.home)
    object Calender : Screen("calender", R.string.calender)
    object Library : Screen("library", R.string.library)
    object More : Screen("more", R.string.more)
}

@Composable
fun MainScreen(onMyPlantsClick: () -> Unit, onCameraClick: () -> Unit, modifier: Modifier) {
    val items = listOf(
        Screen.Home,
        Screen.Calender,
        Screen.Library,
        Screen.More,
    )
    val context = LocalContext.current
    var selectedLocale by remember { mutableStateOf(Locale.getDefault()) }

    val navController = rememberNavController()
    Scaffold(modifier = modifier, bottomBar = {
        BottomAppBar {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            items.forEach { screen ->
                BottomNavigationItem(icon = {
                    Icon(
                        Icons.Filled.Favorite, contentDescription = null
                    )
                },
                    label = { Text(stringResource(screen.resourceId)) },
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
    }) { innerPadding ->
        NavHost(
            navController, startDestination = Screen.Home.route, modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onMyPlantsClick = onMyPlantsClick,
                    onCameraClick = onCameraClick,
                    modifier = modifier,
                )
            }
            composable(Screen.Library.route) { LibraryScreen() }
            composable(Screen.Calender.route) { WeekCalendarScreen() }
            composable(Screen.More.route) {
                MoreOptionsScreen(selectedLocale, onClick = {
                    selectedLocale = it
                    updateLocale(context = context, locale = it)
                })
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
