package com.semba.dkbimages.data.remote

import com.semba.dkbimages.data.model.ImageItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET(Routes.IMAGES_ENDPOINT)
    suspend fun getImages(): Response<List<ImageItem>>

    @GET(Routes.IMAGE_DETAIL_ENDPOINT)
    suspend fun getImageDetail(@Path("id") id: Int): Response<ImageItem>
}