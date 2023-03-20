package org.pinczow.images.feature.image.presentation.images

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.pinczow.images.feature.image.domain.model.ImageModel
import org.pinczow.images.feature.image.domain.repository.ImageRepository
import org.pinczow.images.feature.image.domain.use_cases.ImageUseCases
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val useCases: ImageUseCases
) : ViewModel() {
    val getAll = useCases.getAll()

    fun toggleFavorite(image: ImageModel) {
        viewModelScope.launch {
            useCases.toggleFavorite(image)
        }
    }
}