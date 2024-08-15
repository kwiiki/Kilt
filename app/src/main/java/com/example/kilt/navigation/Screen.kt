package com.example.kilt.navigation

sealed class Screen(val route:String) {
    data object BlogPage:Screen(route = NavPath.BLOGPAGE.name)
    data object News:Screen(route = NavPath.NEWS.name)
    data object HomeDetails:Screen(route = NavPath.HOMEDETAILS.name)

}