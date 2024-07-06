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
import com.sonyaclausen.myplantbuddy.screens.ForgotLoginScreen
import com.sonyaclausen.myplantbuddy.screens.LogInScreen
import com.sonyaclausen.myplantbuddy.screens.MainScreen
import com.sonyaclausen.myplantbuddy.screens.MyPlantsScreen
import com.sonyaclausen.myplantbuddy.screens.PlantDetailScreen
import com.sonyaclausen.myplantbuddy.screens.ProfileScreen
import com.sonyaclausen.myplantbuddy.screens.RegisterScreen
import com.sonyaclausen.myplantbuddy.screens.SavedSearchesScreen
import com.sonyaclausen.myplantbuddy.screens.camera.AddPlantScreen
import com.sonyaclausen.myplantbuddy.screens.camera.CameraRecogScreen
import com.sonyaclausen.myplantbuddy.screens.camera.ChooseImageScreen
import com.sonyaclausen.myplantbuddy.screens.camera.GalleryScreen
import com.sonyaclausen.myplantbuddy.screens.camera.PlantMatchScreen
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
object ChooseImage

@Serializable
object Gallery

@Serializable
object PlantMatch

@Serializable
object AddPlant

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

@Serializable
object PlantDetail

@Serializable
object ForgotLogin


//https://developer.android.com/guide/navigation/design/nested-graphs#compose
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController, startDestination = Auth.toString(), modifier = modifier) {
        navigation(startDestination = Login.toString(), route = Auth.toString()) {
            composable(route = Register.toString()) {
                RegisterScreen(
                    onSignUpComplete = {
                        navController.navigate(route = Login.toString()) {
                            popUpTo(route = Login.toString()) {
                                inclusive = true
                            }
                        }
                    },
                    onCancelClick = { navController.navigate(route = Login.toString()) },
                )
            }
            composable(route = Login.toString()) {
                LogInScreen(
                    onLoginComplete = { navController.navigate(route = Main.toString()) },
                    onCreateClick = {
                        navController.navigate(route = Register.toString()) {
                            popUpTo(route = Register.toString()) {
                                inclusive = true
                            }
                        }

                    },
                    onForgotClick = {
                        navController.navigate(route = ForgotLogin.toString()) {
                            popUpTo(route = ForgotLogin.toString()) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(route = ForgotLogin.toString()) {
                ForgotLoginScreen(
                    onCancelClick = { navController.navigate(route = Login.toString()) },
                    onResetComplete = { navController.navigate(route = Login.toString()) }
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
                    onCardClick = { navController.navigate(route = PlantDetail.toString()) },
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
                    onCameraClick = {
                        navController.navigate(route = ChooseImage.toString()) {
                            popUpTo(route = ChooseImage.toString()) { inclusive = true }
                        }
                    },
                    onGalleryClick = {
                        navController.navigate(route = Gallery.toString()) {
                            popUpTo(route = Gallery.toString()) { inclusive = true }
                        }
                    }
                )
            }
            composable(route = ChooseImage.toString()) {
                ChooseImageScreen(
                    onBackPress = {
                        navController.navigate(route = Camera.toString()) {
                            popUpTo(route = Camera.toString()) { inclusive = true }
                        }
                    },
                    onChooseImageClick = {
                        navController.navigate(route = PlantMatch.toString()) {
                            popUpTo(route = PlantMatch.toString()) { inclusive = true }
                        }
                    }
                )
            }
            composable(route = Gallery.toString()) {
                GalleryScreen(
                    onBackPress = {
                        navController.navigate(route = Camera.toString()) {
                            popUpTo(route = Camera.toString()) { inclusive = true }
                        }
                    },
                    onImageClick = {
                        navController.navigate(route = ChooseImage.toString()) {
                            popUpTo(route = ChooseImage.toString()) { inclusive = true }
                        }
                    }
                )
            }
            composable(route = PlantMatch.toString()) {
                PlantMatchScreen(
                    onBackPress = {
                        navController.navigate(route = Camera.toString()) {
                            popUpTo(route = Camera.toString()) { inclusive = true }
                        }
                    },
                    onPlantClick = {
                        navController.navigate(route = AddPlant.toString()) {
                            popUpTo(route = AddPlant.toString()) { inclusive = true }
                        }
                    }
                )
            }
            composable(route = AddPlant.toString()) {
                AddPlantScreen(
                    onBackPress = {
                        navController.navigate(route = Camera.toString()) {
                            popUpTo(route = Camera.toString()) { inclusive = true }
                        }
                    },
                    onAddPlantClick = {
                        navController.navigate(route = MyPlants.toString()) {
                            popUpTo(route = MyPlants.toString()) { inclusive = true }
                        }
                    },
                    onCancelClick = {
                        navController.navigate(route = Home.toString()) {
                            popUpTo(route = Home.toString()) { inclusive = true }
                        }
                    }
                )
            }
            composable(route = CareBot.toString()) {
                CareBotScreen()
            }
            composable(route = Profile.toString()) {
                ProfileScreen(
                    onEditClick = { navController.navigate(route = EditProfile.toString()) },
                    onBackPress = {
                        navController.popBackStack()
                    })
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
                }, onBackPress = {
                    navController.navigate(route = Profile.toString()) {
                        popUpTo(route = Profile.toString()) { inclusive = true }
                    }
                })
            }
            //TODO: plant id
            composable(route = PlantDetail.toString()) {
                PlantDetailScreen(onBackPress = {
                    navController.navigate(route = MyPlants.toString()) {
                        popUpTo(route = MyPlants.toString()) { inclusive = true }
                    }
                })
            }
        }
    }
}