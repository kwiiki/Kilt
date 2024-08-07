package com.example.kilt.navigation

sealed class Screen(val route:String) {
    data object BlogPage:Screen(route = "BlogPage")
    data object News:Screen(route = "News")

}