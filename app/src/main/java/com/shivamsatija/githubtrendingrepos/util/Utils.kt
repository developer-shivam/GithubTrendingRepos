package com.shivamsatija.githubtrendingrepos.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> getResponse(
    call: suspend () -> T
) : Response<T> {
    return withContext(Dispatchers.IO) {
        try {
            Response.Success(data = call())
        } catch (exception: Exception) {
            Response.Error(exception)
        }
    }
}

fun <T> handleState(
    state: ViewState<T>,
    loading: () -> Unit,
    success: (T) -> Unit,
    error: (Throwable?) -> Unit
) {
    when (state) {
        is ViewState.Loading -> {
            loading()
        }
        is ViewState.Success -> {
            success(state.data)
        }
        is ViewState.Error -> {
            error(state.throwable)
        }
    }
}