package org.pinczow.images.feature.image.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.pinczow.images.data.Resource
import org.pinczow.images.feature.image.domain.model.ImageModel

interface ImageRepository {
    fun getAll(): Flow<PagingData<ImageModel>>
}