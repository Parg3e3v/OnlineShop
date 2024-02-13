package com.parg3v.domain.common

sealed class ResultOf<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T): ResultOf<T>(data)
    class Loading<T>(data: T? = null): ResultOf<T>(data)
    class Failure<T>(message: String? = null, data: T? = null): ResultOf<T>(data, message)

}