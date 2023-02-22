package com.semba.dkbimages.data.remote.datasource

import com.semba.dkbimages.data.model.ApiResult
import com.semba.dkbimages.data.model.ImageModel

interface RemoteDataSource {
    suspend fun fetchImages(): ApiResult<List<ImageModel>>
    suspend fun fetchImageDetail(imageId: Int): ApiResult<ImageModel>
}