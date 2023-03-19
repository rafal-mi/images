package org.pinczow.images.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.pinczow.images.feature.image.domain.model.ImageModel

@Entity(tableName = "image")
data class ImageEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @Embedded
    val urls: Urls,
    val likes: Int,
    @Embedded
    val user: User,
//    @Transient
//    val favorite: Boolean = false
) {
    constructor(that: ImageModel) : this(
        id = that.id,
        urls = that.urls.copy(),
        likes = that.likes,
        user = that.user. copy()
    )
}
