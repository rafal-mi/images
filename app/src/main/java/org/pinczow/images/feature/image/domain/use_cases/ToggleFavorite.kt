package org.pinczow.images.feature.image.domain.use_cases

import org.pinczow.images.feature.image.domain.model.ImageModel
import org.pinczow.images.feature.image.domain.repository.ImageRepository

class ToggleFavorite(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(image: ImageModel) =
        repository.toggleFavorite(image)
}