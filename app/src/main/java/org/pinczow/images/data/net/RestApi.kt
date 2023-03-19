package org.pinczow.images.data.net

import org.pinczow.images.data.Resource
import org.pinczow.images.data.db.entity.ImageEntity
import org.pinczow.images.feature.image.domain.model.ImageModel

interface RestApi {
    suspend fun getImages(): Resource<List<ImageModel>>
    suspend fun search(): SearchResponse
}