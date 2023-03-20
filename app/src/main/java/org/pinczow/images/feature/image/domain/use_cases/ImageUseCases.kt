package org.pinczow.images.feature.image.domain.use_cases

data class ImageUseCases(
    val getAll: GetAll,
    val search: Search,
    val toggleFavorite: ToggleFavorite
)
