package com.semba.dkbimages.data.model

sealed class ApiResult<T> (
    val data: T? = null,
    val errorCode: Int? = null
)
{
    class Success<T>(data: T) : ApiResult<T>(data = data, errorCode = null)
    class Failure<T>(errorCode: Int?) : ApiResult<T>(data = null, errorCode = errorCode)
}
