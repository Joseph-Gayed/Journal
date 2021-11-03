package com.jogayed.core.presentation.viewmodel


/*
sealed class DataState<out T> {
    object Initial : DataState<Nothing>()
    object Loading : DataState<Nothing>()
    data class Error(val throwable: Throwable) : DataState<Nothing>()
    data class Success<out T>(val data: T) : DataState<T>()
}

*/

sealed class DataState<out T>(
    val loading: Boolean,
    private val data: T? = null
) {

    fun data(): T? = data

    object Initial : DataState<Nothing>(false)

    class Loading<T>(isLoading: Boolean, val cachedData: T? = null) : DataState<T>(
        loading = isLoading,
        data = cachedData
    )

    class Error<T>(val throwable: Throwable) : DataState<T>(
        loading = false,
        data = null
    )

    data class Success<out T>(val data: T) : DataState<T>(
        loading = false,
        data = data
    )
}

fun <T> DataState<List<T>>.isEmptyList(): Boolean {
    return this is DataState.Success && this.data.isNullOrEmpty()
}


