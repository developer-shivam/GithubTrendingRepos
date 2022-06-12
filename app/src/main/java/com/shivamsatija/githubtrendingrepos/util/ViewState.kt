package com.shivamsatija.githubtrendingrepos.util

sealed class ViewState<T> {

    class Loading<T> : ViewState<T>()

    data class Success<T>(
        val data: T
    ) : ViewState<T>()

    data class Error<T>(
        val throwable: Throwable? = null
    ) : ViewState<T>()
}
