package com.example.kilt.navigation

sealed class Screen(val route:String) {
    data object BlogPage:Screen(route = NavPath.BLOGPAGE.name)
    data object News:Screen(route = NavPath.NEWS.name)
    data object HomeDetails:Screen(route = "${NavPath.HOMEDETAILS.name}/{id}")
    data object ChooseCityPage:Screen(route = NavPath.CHOOSECITYPAGE.name)
    data object LoginPage:Screen(route = NavPath.LOGIN.name)
    data object EnterFourCodePage:Screen(route = NavPath.ENTERFOURCODEPAGE.name)
    data object EnterSixCodePage:Screen(route = NavPath.ENTERSIXCODEPAGE.name)
    data object RegistrationPage:Screen(route = NavPath.REGISTRATIONPAGE.name)
    data object OwnerPage:Screen(route = NavPath.OWNERPAGE.name)
    data object AgencyPage:Screen(route = NavPath.AGENCYPAGE.name)
    data object AuthenticatedProfileScreen:Screen(route = NavPath.AUTHENTICATEDPROFILESCREEN.name)
}