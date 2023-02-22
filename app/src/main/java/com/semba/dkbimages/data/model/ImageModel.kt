package com.semba.dkbimages.data.model

data class ImageModel (
    val id: Int,
    val albumId: Int,
    val title: String,
    val imageURL: String,
    val thumbnailUrl: String,
        )
{
    companion object {
        fun empty() = ImageModel(0,0,"","","")
    }
}