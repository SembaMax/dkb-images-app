package com.semba.dkbimages.data

import com.semba.dkbimages.util.Utils
import com.semba.dkbimages.data.di.apiService
import com.semba.dkbimages.data.model.ApiResult
import com.semba.dkbimages.data.remote.datasource.NetworkDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RemoteDataSourceTest {

    lateinit var mockWebServer: MockWebServer

    private val api by lazy {
        apiService(baseUrl = mockWebServer.url("/"), OkHttpClient())
    }

    lateinit var remoteDataSource: NetworkDataSource

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        remoteDataSource = NetworkDataSource(api)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `get images list`() = runTest {
        val response = MockResponse()
            .setBody(Utils.imagesJsonAsString())
            .setResponseCode(200)

        mockWebServer.enqueue(response)

        val result = api.getImages()

        assert(result.body()?.size == 10)
    }

    @Test
    fun `get images with success result`() = runTest {
        val response = MockResponse()
            .setBody(Utils.imagesJsonAsString())
            .setResponseCode(200)

        mockWebServer.enqueue(response)

        val result = remoteDataSource.fetchImages()

        assert(result is ApiResult.Success && result.data?.size == 10)
    }

    @Test
    fun `get images with failure result`() = runTest {
        val response = MockResponse()
            .setBody("")
            .setResponseCode(400)

        mockWebServer.enqueue(response)

        val result = remoteDataSource.fetchImages()

        assert(result is ApiResult.Failure && result.errorCode == 400)
    }

}