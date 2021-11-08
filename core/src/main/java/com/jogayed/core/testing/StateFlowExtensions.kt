package com.jogayed.core.testing

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest

@ExperimentalCoroutinesApi
fun <T> StateFlow<T>.test(testFunction: (list: List<T>) -> Unit) = runBlockingTest {
    val list = mutableListOf<T>()
    val job = launch {
        toList(list)
    }
    testFunction(list)
    job.cancel()
}