package com.sonyaclausen.myplantbuddy

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@Serializable
object Profile

@Serializable
object FriendsList

@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier, navController = navController, startDestination = Profile.toString()
    ) {
        composable(route = Profile.toString()) {
            ProfileScreen(
                onNavigateToFriends = { navController.navigate(route = FriendsList.toString()) },
            )
        }
        composable(route = FriendsList.toString()) {
            FriendsListScreen(onBackPress = { navController.popBackStack() })
        }
    }
}

@Composable
fun ProfileScreen(
    onNavigateToFriends: () -> Unit,
) {
    Button(onClick = onNavigateToFriends) {
        Text(text = "See friends list")
    }
}

@Composable
fun FriendsListScreen(
    onBackPress: () -> Unit,
) {
    Button(onClick = onBackPress) {
        Text(text = "Go back")
    }
}