package org.pinczow.images.feature.image.presentation.images

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.pinczow.images.feature.image.domain.model.ImageModel
import org.pinczow.images.feature.image.domain.repository.ImageRepository
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val repository: ImageRepository
) : ViewModel() {
    val getAll = repository.getAll()

    fun toggleFavorite(image: ImageModel) {
        viewModelScope.launch {
            repository.toggleFavorite(image)
        }
    }
}