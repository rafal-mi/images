package org.pinczow.images.data.db.entity

import androidx.room.Embedded
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class User(
    @SerialName("links")
    @Embedded
    val userLinks: UserLinks,
    val username: String
)
