package com.semba.dkbimages.data.remote.datasource

import com.semba.dkbimages.data.model.ApiResult
import com.semba.dkbimages.data.model.ImageModel
import com.semba.dkbimages.data.model.toModel
import com.semba.dkbimages.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class NetworkDataSource @Inject constructor(private val apiService: ApiService): RemoteDataSource {

    override suspend fun fetchImages(): ApiResult<List<ImageModel>> = withContext(Dispatchers.IO) {
        try {
            val result = apiService.getImages()
            if (result.isSuccessful) {
                Timber.d("Http request is successful. Items size = ${result.body()?.size}")
                val items = result.body()?.map { it.toModel() } ?: emptyList()
                ApiResult.Success(items)
            } else {
                val errorCode = result.code()
                Timber.d("Http request is failed. ErrorCode is = $errorCode")
                ApiResult.Failure(errorCode)
            }
        } catch (e: Exception) {
            Timber.d("Error while fetching all images = ${e.message}")
            ApiResult.Failure(-1)
        }
    }

    override suspend fun fetchImageDetail(imageId: Int): ApiResult<ImageModel> = withContext(Dispatchers.IO) {
        try {
            val result = apiService.getImageDetail(imageId)
            if (result.isSuccessful && result.body() != null) {
                Timber.d("Http request is successful. Item fetched with id ${imageId}}")
                val item = result.body()!!.toModel()
                ApiResult.Success(item)
            } else {
                val errorCode = result.code()
                Timber.d("Http request is failed. ErrorCode is = $errorCode")
                ApiResult.Failure(errorCode)
            }
        } catch (e: Exception) {
            Timber.d("Error while fetching image detail = ${e.message}")
            ApiResult.Failure(500)
        }
    }
}