package org.pinczow.images.feature.image.domain.model

import androidx.room.Embedded
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import org.pinczow.images.data.db.entity.ImageEntity
import org.pinczow.images.data.db.entity.Urls
import org.pinczow.images.data.db.entity.User

@Serializable
data class ImageModel(
    val id: String,
    val urls: Urls,
    val likes: Int,
    val user: User
    ) {
    constructor(that: ImageEntity) : this(
        id = that.id,
        urls = that.urls.copy(),
        likes = that.likes,
        user = that.user. copy()
    )
}
