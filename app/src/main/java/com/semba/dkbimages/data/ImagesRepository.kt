package com.semba.dkbimages.data

import com.semba.dkbimages.data.model.ApiResult
import com.semba.dkbimages.data.model.ImageModel
import com.semba.dkbimages.data.remote.datasource.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ImagesRepository @Inject constructor(private val remoteDataSource: RemoteDataSource): Repository {

    override fun loadImages(): Flow<ApiResult<List<ImageModel>>> = flow {
        val result = remoteDataSource.fetchImages()
        emit(result)
    }

    override fun loadImageDetail(imageId: Int): Flow<ApiResult<ImageModel>> = flow {
        val result = remoteDataSource.fetchImageDetail(imageId)
        emit(result)
    }
}