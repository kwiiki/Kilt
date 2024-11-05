package com.example.kilt.navigation

import kotlin.math.round

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
    data object EditProfile:Screen(route = NavPath.EDITPROFILE.name)
    data object NotificationsScreen:Screen(route = NavPath.NOTIFICATIONSSCREEN.name)
    data object MyAnnouncementScreen:Screen(route = NavPath.MYANNOUNCEMENTSCREEN.name)
    data object IdentificationScreen:Screen(route = NavPath.IDENTIFICATIONSCREEN.name)
    data object AgencyProfileScreen:Screen(route = NavPath.AGENCYPROFILESCREEN.name)
    data object ChooseCityInEdit:Screen(route = NavPath.CHOOSECITYINEDIT.name)

    data object AddingAnnouncement:Screen(route = NavPath.ADDINGANNOUNCEMENTSCREEN.name)
}