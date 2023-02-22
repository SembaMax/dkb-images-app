package com.semba.dkbimages.compose.navigation

import timber.log.Timber

const val IMAGE_ID_ARG = "imageId"

fun String.withArgs(args: Map<String, String>): String {
    var result = this
    args.forEach { (key, value) ->
        result = result.replace("{$key}", value)
    }
    Timber.d("Detail screen route with args: $result")
    return result
}