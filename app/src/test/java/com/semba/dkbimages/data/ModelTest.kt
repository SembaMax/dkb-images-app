package com.semba.dkbimages.data

import com.semba.dkbimages.data.model.ImageItem
import com.semba.dkbimages.data.model.toModel
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ModelTest {

    @Test
    fun `map image entity to image model`() {
        val imageItem = ImageItem(
            id = 10,
            albumId = 10,
            title = "title",
            url = "imageUrl",
            thumbnailUrl = "thumbnailUrl"
        )

        val imageModel = imageItem.toModel()

        assertEquals(10, imageModel.id)
        assertEquals(10, imageModel.albumId)
        assertEquals("title", imageModel.title)
        assertEquals("imageUrl", imageModel.imageURL)
        assertEquals("thumbnailUrl", imageModel.thumbnailUrl)
    }
}