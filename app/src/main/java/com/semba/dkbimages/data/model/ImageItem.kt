package com.semba.dkbimages.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageItem (
    @SerialName("id") val id: Int,
    @SerialName("albumId") val albumId: Int,
    @SerialName("title") val title: String,
    @SerialName("url") val imageURL: String,
    @SerialName("thumbnailUrl") val thumbnailUrl: String,
        )


fun ImageItem.toModel() : ImageModel =
    ImageModel(
        id = this.id,
        albumId = this.albumId,
        title = this.title,
        imageURL = this.imageURL,
        thumbnailUrl = this.thumbnailUrl,
    )