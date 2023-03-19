package org.pinczow.images.feature.image.presentation.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.pinczow.images.feature.image.domain.model.ImageModel
import org.pinczow.images.feature.image.domain.repository.ImageRepository
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: ImageRepository
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _searchedImages = MutableStateFlow<PagingData<ImageModel>>(PagingData.empty())
    val searchedImages = _searchedImages

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun searchHeroes(query: String) {
        viewModelScope.launch {
            repository.search(query = query).cachedIn(viewModelScope).collect {
                _searchedImages.value = it
            }
        }
    }
}