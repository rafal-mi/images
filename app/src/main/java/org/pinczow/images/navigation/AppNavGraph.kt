package org.pinczow.images.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.ExperimentalPagingApi
import org.pinczow.images.feature.image.presentation.images.ImagesScreen
import org.pinczow.images.feature.image.presentation.images.SearchScreen

@OptIn(ExperimentalPagingApi::class)
@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Images.route
    ) {
        composable(route = Screens.Images.route){
            ImagesScreen(navController = navController)
        }
        composable(route = Screens.Search.route){
            SearchScreen(navController = navController)
        }
    }

}
