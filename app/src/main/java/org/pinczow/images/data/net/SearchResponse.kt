package org.pinczow.images.data.net

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.pinczow.images.data.db.entity.ImageEntity
import org.pinczow.images.feature.image.domain.model.ImageModel

@Serializable
data class SearchResponse(
    @SerialName("results")
    val images: List<ImageModel>
    )
