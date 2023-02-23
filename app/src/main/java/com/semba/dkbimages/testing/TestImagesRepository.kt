package com.semba.dkbimages.testing

import com.semba.dkbimages.data.Repository
import com.semba.dkbimages.data.model.ApiResult
import com.semba.dkbimages.data.model.ImageItem
import com.semba.dkbimages.data.model.ImageModel
import com.semba.dkbimages.data.model.toModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class TestImagesRepository: Repository {

    private val imagesFlow: MutableSharedFlow<List<ImageItem>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)


    private var shouldSuccess = true

    override fun loadImages(): Flow<ApiResult<List<ImageModel>>> {
        return if (shouldSuccess)
            imagesFlow.map { images -> ApiResult.Success(data = images.map { it.toModel()}) }
        else
            flow { emit(ApiResult.Failure(errorCode = -1)) }
    }

    override fun loadImageDetail(imageId: Int): Flow<ApiResult<ImageModel>> {
        return if (shouldSuccess)
            imagesFlow.map { images -> ApiResult.Success(data = images.first().toModel()) }
        else
            flow { emit(ApiResult.Failure(errorCode = -1)) }
    }

    fun setImageItems(images: List<ImageItem>)
    {
        imagesFlow.tryEmit(images)
    }

    fun setIsSuccessful(isSuccessful: Boolean)
    {
        shouldSuccess = isSuccessful
    }
}