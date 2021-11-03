package com.jogayed.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher

/**
 * there will be 2 wrapper classes 1 for real code [MyCoroutineDispatchers]
 * and the other for unit testing
 * *
 */
interface ICoroutineDispatchers {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
}

/**
 * Wrapper class , wraps the [Dispatchers]
 * for the real usage inside the code
 */
class MyCoroutineDispatchers(
    override val io: CoroutineDispatcher = Dispatchers.IO,
    override val main: CoroutineDispatcher = Dispatchers.Main
) : ICoroutineDispatchers

/**
 * Wrapper class , wraps the [Dispatchers]
 * for the unit testing to replace the io , main with [TestCoroutineDispatcher]
 */
class MyTestCoroutineScopeDispatchers(
    override val io: CoroutineDispatcher,
    override val main: CoroutineDispatcher
) : ICoroutineDispatchers