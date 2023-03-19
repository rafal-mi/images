package org.pinczow.images.feature.image.presentation.images

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import org.pinczow.images.App.Companion.TAG
import org.pinczow.images.feature.image.presentation.images.components.ListContent
import org.pinczow.images.feature.image.presentation.search.SearchViewModel
import org.pinczow.images.feature.image.presentation.search.components.SearchWidget

@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchQuery by viewModel.searchQuery
    val searchedImages = viewModel.searchedImages.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            SearchWidget(
                text = searchQuery,
                onTextChange = {
                    viewModel.updateSearchQuery(query = it)
                },
                onSearchClicked = {
                    viewModel.searchHeroes(query = it)
                },
                onCloseClicked = {
                    navController.popBackStack()
                }
            )
        },
        content = { paddingValues ->
            Log.i(TAG, "Padding values are $paddingValues")

            ListContent(
                items = searchedImages,
                onItemIconClick = {}
            )
        }
    )
}

