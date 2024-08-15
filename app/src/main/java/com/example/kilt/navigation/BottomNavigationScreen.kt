package com.example.kilt.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.kilt.R

sealed class BottomNavigationScreen(
    val route: String, val unselectedIcon: @Composable () -> Painter,
    val selectedIcon: @Composable () -> Painter
) {
    data object Profile : BottomNavigationScreen(
        route = NavPath.PROFILE.name,
        unselectedIcon = { painterResource(id = R.drawable.unselected_profile_icon) },
        selectedIcon = { painterResource(id = R.drawable.selected_profile_icon) }
    )

    data object HomePage : BottomNavigationScreen(
        route = NavPath.HOME.name,
        unselectedIcon = { painterResource(id = R.drawable.unselected_home_icon) },
        selectedIcon = { painterResource(id = R.drawable.home_selected_icon) }

    )

    data object Favorites : BottomNavigationScreen(
        route = NavPath.FAVORITES.name,
        unselectedIcon = { painterResource(id = R.drawable.unselected_favorite_icon) },
        selectedIcon = { painterResource(id = R.drawable.selected_favorite_icon) }
    )

    data object SaleAndRent : BottomNavigationScreen(
        route = NavPath.SEARCH.name,
        unselectedIcon = { painterResource(id = R.drawable.unselected_search_icon) },
        selectedIcon = { painterResource(id = R.drawable.selected_search_icon) }
    )
}