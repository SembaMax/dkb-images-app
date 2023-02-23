package com.semba.dkbimages.util

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.semba.dkbimages.data.model.ImageItem
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import org.json.JSONArray
import java.io.InputStreamReader


object Utils {

    fun imagesJsonAsString(): String {
        val resourceAsStream = javaClass.classLoader?.getResourceAsStream("ImagesResponse.json")
        val reader = InputStreamReader(resourceAsStream)
        return reader.use { it.readText() }
    }

    fun imagesJsonAsItems(): List<ImageItem> {
        val gson = GsonBuilder().create()
        val jsonString = imagesJsonAsString()
        return gson.fromJson(jsonString, object : TypeToken<List<ImageItem>>(){}.type)
    }

}