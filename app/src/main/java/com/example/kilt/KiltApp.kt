package com.example.kilt

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kilt.core.navigation.BottomNavigationScreen
import com.example.kilt.core.navigation.Screen
import com.example.kilt.presentation.addnewlisting.AddNewListing
import com.example.kilt.utills.otp.SmsViewModel
import com.example.kilt.presentation.choosecityinedit.ChooseCityInEdit
import com.example.kilt.presentation.choosecityinedit.viewmodel.ChooseCityInEditViewModel
import com.example.kilt.presentation.blog.BlogPage
import com.example.kilt.presentation.blog.News
import com.example.kilt.presentation.favorites.FavoritesScreen
import com.example.kilt.screens.home.HomePageContent
import com.example.kilt.presentation.userabout.UserAboutScreen
import com.example.kilt.presentation.editprofile.EditProfile
import com.example.kilt.presentation.editprofile.addnewimagebottomsheet.viewmodel.AddNewImageViewModel
import com.example.kilt.presentation.editprofile.addnewphonenumberbottomsheet.viewmodel.AddNewPhoneNumberViewModel
import com.example.kilt.presentation.editprofile.viewmodel.EditProfileViewModel
import com.example.kilt.presentation.favorites.viewmodel.FavoritesViewModel
import com.example.kilt.presentation.userabout.viewmodel.UserAboutViewModel
import com.example.kilt.screens.profile.registration.RegistrationForAgencyPage
import com.example.kilt.screens.profile.EnterFourCodePage
import com.example.kilt.screens.profile.EnterSixCodePage
import com.example.kilt.presentation.identification.IdentificationScreen
import com.example.kilt.screens.profile.login.LoginPage
import com.example.kilt.screens.profile.registration.RegistrationForOwnerPage
import com.example.kilt.presentation.profile.ProfileScreen
import com.example.kilt.presentation.profile.viewmodel.ProfileViewModel
import com.example.kilt.presentation.search.FiltersViewModel
import com.example.kilt.screens.profile.myannouncement.MyAnnouncementScreen
import com.example.kilt.screens.profile.notifications.NotificationsScreen
import com.example.kilt.screens.profile.registration.RegistrationPage
import com.example.kilt.presentation.search.SearchPage
import com.example.kilt.presentation.search.SearchResultsViewModel
import com.example.kilt.presentation.search.viewmodel.MapViewModel
import com.example.kilt.screens.searchpage.chooseCity.ChooseCityPage
import com.example.kilt.presentation.listing.HomeDetailsScreen
import com.example.kilt.presentation.choosecity.viewmodel.ChooseCityViewModel
import com.example.kilt.presentation.config.viewmodel.ConfigViewModel
import com.example.kilt.presentation.listing.viewmodel.ListingViewModel
import com.example.kilt.presentation.login.viewModel.AuthViewModel
import com.example.kilt.presentation.identification.viewmodel.IdentificationViewModel
import com.example.kilt.presentation.search.viewmodel.SearchViewModel


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun KiltApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val searchViewModel: SearchViewModel = hiltViewModel()
    val configViewModel: ConfigViewModel = hiltViewModel()
    val listingViewModel: ListingViewModel = hiltViewModel()
    val chooseCityViewModel: ChooseCityViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel()
    val identificationViewModel: IdentificationViewModel = hiltViewModel()
    val addNewPhoneNumberViewModel: AddNewPhoneNumberViewModel = hiltViewModel()
    val editProfileViewModel: EditProfileViewModel = hiltViewModel()
    val chooseCityInEditViewModel: ChooseCityInEditViewModel = hiltViewModel()
    val addNewImageViewModel: AddNewImageViewModel = hiltViewModel()
    val userAboutViewModel: UserAboutViewModel = hiltViewModel()
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val filtersViewModel: FiltersViewModel = hiltViewModel()
    val searchResultsViewModel: SearchResultsViewModel = hiltViewModel()
    val mapViewModel: MapViewModel = hiltViewModel()
    val favoritesViewModel: FavoritesViewModel = hiltViewModel()
    val smsViewModel: SmsViewModel = hiltViewModel()

    favoritesViewModel.getUserFavorites()


    val bottomBarRoutes = listOf(
        BottomNavigationScreen.HomePage.route,
        BottomNavigationScreen.SaleAndRent.route,
        BottomNavigationScreen.AddNewListing.route,
        BottomNavigationScreen.Favorites.route,
        BottomNavigationScreen.Profile.route,
    )

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomBarRoutes) {
                BottomNavigationBar(navController)
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .background(Color.Black)
                )
            }
        },
        modifier = Modifier.padding(bottom = 0.dp),
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavigationScreen.HomePage.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { fadeIn(animationSpec = tween(0)) },
            exitTransition = { fadeOut(animationSpec = tween(0)) }
        ) {
            composable(BottomNavigationScreen.HomePage.route) {
                HomePageContent(
                    navController,
                    searchViewModel = searchViewModel
                )
            }
            composable(BottomNavigationScreen.SaleAndRent.route) {
                SearchPage(
                    chooseCityViewModel = chooseCityViewModel,
                    configViewModel = configViewModel,
                    filtersViewModel = filtersViewModel,
                    searchResultsViewModel = searchResultsViewModel,
                    listingViewModel = listingViewModel,
                    mapViewModel = mapViewModel,
                    navController = navController
                )
            }
            composable(BottomNavigationScreen.Favorites.route) {
                FavoritesScreen(
                    navController,
                    favoritesViewModel,
                    listingViewModel,
                    configViewModel
                )
            }
            composable(BottomNavigationScreen.Profile.route) {
                ProfileScreen(
                    navController,
                    authViewModel,
                    profileViewModel
                )
            }
            composable(Screen.BlogPage.route) { BlogPage(navController) }
            composable(Screen.News.route) { News(navController) }
            composable(
                route = Screen.HomeDetails.route,
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")
                HomeDetailsScreen(configViewModel, navController, id)
            }
            composable(Screen.ChooseCityPage.route) {
                ChooseCityPage(
                    navController,
                    searchViewModel,
                    chooseCityViewModel,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
                )
            }
            composable(Screen.LoginPage.route) {
                LoginPage(
                    navController = navController,
                    authViewModel = authViewModel
                )
            }
            composable(Screen.EnterFourCodePage.route) {
                EnterFourCodePage(
                    navController,
                    authViewModel = authViewModel
                )
            }
            composable(Screen.RegistrationPage.route) {
                RegistrationPage(
                    navController,
                    authViewModel = authViewModel
                )
            }
            composable(Screen.OwnerPage.route) {
                RegistrationForOwnerPage(
                    navController,
                    authViewModel = authViewModel
                )
            }
            composable(Screen.AgencyPage.route) {
                RegistrationForAgencyPage(
                    navController,
                    authViewModel = authViewModel
                )
            }
            composable(Screen.EnterSixCodePage.route) {
                EnterSixCodePage(
                    navController,
                    authViewModel = authViewModel
                )
            }
            composable(Screen.EditProfile.route) {
                EditProfile(
                    navController,
                    authViewModel = authViewModel,
                    editProfileViewModel = editProfileViewModel,
                    addNewPhoneNumberViewModel = addNewPhoneNumberViewModel,
                    addNewImageViewModel = addNewImageViewModel
                )
            }
            composable(Screen.AddNewListingScreen.route) { AddNewListing(navController = navController) }
            composable(Screen.NotificationsScreen.route) { NotificationsScreen(navController) }
            composable(Screen.MyAnnouncementScreen.route) { MyAnnouncementScreen(navController) }
            composable(Screen.IdentificationScreen.route) {
                IdentificationScreen(
                    navController,
                    identificationViewModel = identificationViewModel
                )
            }
            composable(Screen.AgencyProfileScreen.route) {
                UserAboutScreen(
                    navController,
                    authViewModel = authViewModel,
                    userAboutViewModel = userAboutViewModel
                )
            }
            composable(Screen.ChooseCityInEdit.route) {
                ChooseCityInEdit(
                    navController,
                    chooseCityInEditViewModel,
                )
            }
        }
        val currentRoute = navBackStackEntry?.destination?.route
        Log.d("currentRoute", "KiltApp: $currentRoute")
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val screens = listOf(
        BottomNavigationScreen.HomePage,
        BottomNavigationScreen.SaleAndRent,
        BottomNavigationScreen.AddNewListing,
        BottomNavigationScreen.Favorites,
        BottomNavigationScreen.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Column {
        NavigationBar(
            modifier = Modifier
                .height(65.dp)
                .fillMaxWidth()
                .border(border = BorderStroke(width = 1.dp, color = Color(0xffCCD2E3))),
            containerColor = Color.White,
            windowInsets = WindowInsets(bottom = 0, top = 0)
        ) {
            screens.forEach { screen ->
                val selected = currentRoute == screen.route
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            brush = if (selected) {
                                Brush.horizontalGradient(
                                    colors = listOf(Color(0xFF0000ff), Color(0xFF1B278F))
                                )
                            } else {
                                Brush.horizontalGradient(
                                    colors = listOf(Color.White, Color.White)
                                )
                            }
                        )
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                    contentAlignment = Alignment.Center
                ){
                    Image(
                        painter = screen.unselectedIcon.invoke(),
                        contentDescription = screen.route,
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(55.dp)
                            .animateContentSize(),
                        contentScale = ContentScale.Crop,
                        colorFilter = ColorFilter.tint(if(selected) Color.White else Color.Gray)
                    )
                }
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
                .background(Color.Black)
        )
    }
}