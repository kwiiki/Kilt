package com.example.kilt

import androidx.compose.animation.animateContentSize
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
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kilt.navigation.BottomNavigationScreen
import com.example.kilt.navigation.Screen
import com.example.kilt.screens.blog.BlogPage
import com.example.kilt.screens.blog.News
import com.example.kilt.screens.favorite.Favorite
import com.example.kilt.screens.home.HomePage
import com.example.kilt.screens.profile.ProfileScreen
import com.example.kilt.screens.searchpage.homedetails.HomeDetailsScreen
import com.example.kilt.screens.searchpage.SearchPage


@Composable
fun KiltApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            if (currentRoute != "BlogPage" && currentRoute!= "News" && currentRoute != "HomeDetails") {
                BottomNavigationBar(navController)
            }
        },
        modifier = Modifier.padding(bottom = 0.dp),
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavigationScreen.HomePage.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavigationScreen.HomePage.route) { HomePage(navController) }
            composable(BottomNavigationScreen.SaleAndRent.route) { SearchPage(navController) }
            composable(BottomNavigationScreen.Favorites.route) { Favorite() }
            composable(BottomNavigationScreen.Profile.route) { ProfileScreen() }
            composable(Screen.BlogPage.route) { BlogPage(navController) }
            composable(Screen.News.route){ News(navController)}
            composable(Screen.HomeDetails.route){ HomeDetailsScreen() }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val screens = listOf(
        BottomNavigationScreen.HomePage,
        BottomNavigationScreen.SaleAndRent,
        BottomNavigationScreen.Favorites,
        BottomNavigationScreen.Profile
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
                ) {
                    Image(
                        painter = if (selected) screen.selectedIcon() else screen.unselectedIcon(),
                        contentDescription = screen.route,
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(if (selected) 65.dp else 60.dp)
                            .animateContentSize(),
                        contentScale = ContentScale.Crop
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

