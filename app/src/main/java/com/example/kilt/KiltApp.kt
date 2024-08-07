package com.example.kilt

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.kilt.navigation.BottomNavigationItem
import com.example.kilt.navigation.NavPath
import com.example.kilt.navigation.BottomNavigationScreen
import com.example.kilt.navigation.Screen
import com.example.kilt.screens.blog.BlogPage
import com.example.kilt.screens.blog.News
import com.example.kilt.screens.favorite.Favorite
import com.example.kilt.screens.home.HomePage
import com.example.kilt.screens.profile.ProfileScreen
import com.example.kilt.screens.saleandrent.SaleAndRent


@Composable
fun KiltApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            if (currentRoute != "BlogPage" && currentRoute!= "News") {
                BottomNavigationBar(navController)
            }
        },
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavigationScreen.HomePage.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavigationScreen.HomePage.route) { HomePage(navController) }
            composable(BottomNavigationScreen.SaleAndRent.route) { SaleAndRent() }
            composable(BottomNavigationScreen.Favorites.route) { Favorite() }
            composable(BottomNavigationScreen.Profile.route) { ProfileScreen() }
            composable(Screen.BlogPage.route) { BlogPage(navController) }
            composable(Screen.News.route){ News(navController)}
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

    Box {
        NavigationBar(
            modifier = Modifier
                .height(105.dp)
                .border(border = BorderStroke(width = 2.dp, color = Color(0xffCCD2E3))),
            containerColor = Color.White,
            tonalElevation = 20.dp
        ) {
            screens.forEach { screen ->
                val selected = currentRoute == screen.route
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
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
                            .size(if (selected) 105.dp else 100.dp)
                            .animateContentSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .align(Alignment.BottomCenter)
                .background(Color.Black)
        )
    }
}

fun bottomNavigationItems(): List<BottomNavigationItem> {
    return listOf(
        BottomNavigationItem(
            title = NavPath.HOME.name,
            selectedIcon = R.drawable.unselected_home_icon,
            unselectedIcon = R.drawable.unselected_home_icon,
        ),
        BottomNavigationItem(
            title = NavPath.SEARCH.name,
            selectedIcon = R.drawable.unselected_search_icon,
            unselectedIcon = R.drawable.unselected_search_icon,
        ),
        BottomNavigationItem(
            title = NavPath.FAVORITES.name,
            selectedIcon = R.drawable.unselected_favorite_icon,
            unselectedIcon = R.drawable.unselected_favorite_icon,
        ),
        BottomNavigationItem(
            title = NavPath.PROFILE.name,
            selectedIcon = R.drawable.unselected_profile_icon,
            unselectedIcon = R.drawable.unselected_profile_icon,
        ),
    )
}