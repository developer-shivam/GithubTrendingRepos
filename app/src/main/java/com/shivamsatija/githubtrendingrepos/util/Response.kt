package com.shivamsatija.githubtrendingrepos.util

sealed class Response<T> {

    data class Success<T>(
        val data: T
    ) : Response<T>()

    data class Error<T>(
        val throwable: Throwable? = null
    ) : Response<T>()
}
