package org.pinczow.images.navigation

sealed class Screens(val route: String) {
    object Images: Screens("images")
    object Search: Screens("search")
}

