package com.sonyaclausen.myplantbuddy

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.sonyaclausen.myplantbuddy.screens.LogInScreen
import com.sonyaclausen.myplantbuddy.screens.MainScreen
import com.sonyaclausen.myplantbuddy.screens.MyPlantsScreen
import com.sonyaclausen.myplantbuddy.screens.RegisterScreen
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

//https://developer.android.com/guide/navigation/design/nested-graphs#compose
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController, startDestination = Auth.toString(), modifier = modifier) {
        navigation(startDestination = Register.toString(), route = Auth.toString()) {
            composable(route = Register.toString()) {
                RegisterScreen(
                    onSignUpComplete = { navController.navigate(route = Main.toString()) },
                    modifier = modifier
                )
            }
            composable(route = Login.toString()) {
                LogInScreen(
                    onLoginComplete = { navController.navigate(route = Main.toString()) },
                    modifier = modifier
                )
            }
        }
        navigation(startDestination = Home.toString(), route = Main.toString()) {
            composable(route = Home.toString()) {
                MainScreen(
                    onMyPlantsClick = { navController.navigate(route = MyPlants.toString()) },
                    onCameraClick = { navController.navigate(route = Camera.toString()) },
                    modifier = modifier,
                )
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
        }
    }
}