package com.semba.dkbimages.data

import com.semba.dkbimages.data.model.ApiResult
import com.semba.dkbimages.data.model.ImageModel
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun loadImages(): Flow<ApiResult<List<ImageModel>>>
    fun loadImageDetail(imageId: Int): Flow<ApiResult<ImageModel>>
}