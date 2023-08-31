package com.rizki.alfatest.app.common

import com.rizki.alfatest.app.domain.FailureData

sealed class Resource<out T> {
    data class Success<T>(val data: T? = null, val source: DataSource) : Resource<T>()
    data class Failure(val failureData: FailureData) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
    object None : Resource<Nothing>()
}

enum class DataSource {
    CACHE,
    REMOTE
}