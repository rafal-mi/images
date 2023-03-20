package org.pinczow.images.feature.image.domain.use_cases

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.pinczow.images.feature.image.domain.model.ImageModel
import org.pinczow.images.feature.image.domain.repository.ImageRepository

class GetAll(
    private val repository: ImageRepository
) {
    operator fun invoke(): Flow<PagingData<ImageModel>> =
        repository.getAll()
}