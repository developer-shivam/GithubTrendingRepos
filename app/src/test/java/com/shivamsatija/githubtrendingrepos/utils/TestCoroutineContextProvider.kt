package com.shivamsatija.githubtrendingrepos.utils

import com.shivamsatija.githubtrendingrepos.util.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class TestCoroutineContextProvider : CoroutineContextProvider() {

    override val Main: CoroutineContext by lazy {
        Dispatchers.Main
    }

    override val IO: CoroutineContext by lazy {
        Dispatchers.Main
    }
}