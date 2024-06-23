package com.sonyaclausen.myplantbuddy

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.sonyaclausen.myplantbuddy.screens.CareBotScreen
import com.sonyaclausen.myplantbuddy.screens.CommunityScreen
import com.sonyaclausen.myplantbuddy.screens.EditProfileScreen
import com.sonyaclausen.myplantbuddy.screens.LogInScreen
import com.sonyaclausen.myplantbuddy.screens.MainScreen
import com.sonyaclausen.myplantbuddy.screens.MyPlantsScreen
import com.sonyaclausen.myplantbuddy.screens.ProfileScreen
import com.sonyaclausen.myplantbuddy.screens.SavedSearchesScreen
import com.sonyaclausen.myplantbuddy.screens.camera.CameraRecogScreen
import kotlinx.serialization.Serializable

@Serializable
object Auth

// Routes
@Serializable
object Login

@Serializable
object Register

// Route for nested graph
@Serializable
object Main

// Routes inside nested graph
@Serializable
object Home

@Serializable
object MyPlants

@Serializable
object Camera

@Serializable
object MoreOptions

@Serializable
object Profile

@Serializable
object Community

@Serializable
object SavedSearches

@Serializable
object CareBot

@Serializable
object EditProfile


//https://developer.android.com/guide/navigation/design/nested-graphs#compose
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController, startDestination = Auth.toString(), modifier = modifier) {
        navigation(startDestination = Login.toString(), route = Auth.toString()) {
//            composable(route = Register.toString()) {
//                RegisterScreen(
//                    onSignUpComplete = { navController.navigate(route = Main.toString()) },
//                    modifier = modifier
//                )
//            }
            composable(route = Login.toString()) {
                LogInScreen(
                    onLoginComplete = { navController.navigate(route = Main.toString()) },
                    modifier = modifier
                )
            }
        }
        navigation(startDestination = Home.toString(), route = Main.toString()) {
            composable(route = Home.toString()) {
                MainScreen(onMyPlantsClick = { navController.navigate(route = MyPlants.toString()) },
                    onCameraClick = { navController.navigate(route = Camera.toString()) },
                    modifier = modifier,
                    onRouteClick = {
                        when (it) {
                            CareBot.toString() -> navController.navigate(route = CareBot.toString())
                            Profile.toString() -> navController.navigate(route = Profile.toString())
                            Community.toString() -> navController.navigate(route = Community.toString())
                            SavedSearches.toString() -> navController.navigate(route = SavedSearches.toString())
                        }
                    })
            }
            composable(route = MyPlants.toString()) {
                MyPlantsScreen(
                    onBackPress = {
                        navController.navigate(route = Home.toString()) {
                            popUpTo(route = Home.toString()) { inclusive = true }
                        }
                    },
                    modifier = modifier,
                )
            }
            composable(route = Camera.toString()) {
                CameraRecogScreen(
                    onBackPress = {
                        navController.navigate(route = Home.toString()) {
                            popUpTo(route = Home.toString()) { inclusive = true }
                        }
                    },
                    modifier = modifier,
                )
            }
            composable(route = CareBot.toString()) {
                CareBotScreen()
            }
            composable(route = Profile.toString()) {
                ProfileScreen( onEditClick = { navController.navigate(route = EditProfile.toString()) })
            }
            composable(route = Community.toString()) {
                CommunityScreen()
            }
            composable(route = SavedSearches.toString()) {
                SavedSearchesScreen()
            }
            composable(route = EditProfile.toString()) {
                EditProfileScreen(onCancelClick = {
                    navController.navigate(route = Profile.toString()) {
                        popUpTo(route = Profile.toString()) { inclusive = true }
                    }
                })
            }
        }
    }
}