package org.pinczow.images.feature.image.presentation.images

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import org.pinczow.images.App.Companion.TAG
import org.pinczow.images.feature.image.presentation.images.components.ImagesTopBar
import org.pinczow.images.feature.image.presentation.images.components.ListContent
import org.pinczow.images.navigation.Screens

@OptIn(ExperimentalPagingApi::class)
@Composable
fun ImagesScreen(
    navController: NavHostController,
    viewModel: ImagesViewModel = hiltViewModel()
) {
    val getAll = viewModel.getAll.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            ImagesTopBar(
                onSearchClicked = {
                    navController.navigate(Screens.Search.route)
                }
            )
        },
        content = { paddingValues ->
            Log.i(TAG, "Padding values are $paddingValues")

            ListContent(
                items = getAll,
                onItemIconClick = { image ->
                    viewModel.toggleFavorite(image)
                }
            )
        }
    )

}