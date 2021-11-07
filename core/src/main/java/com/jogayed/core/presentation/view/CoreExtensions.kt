package com.jogayed.core.presentation.view

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*


fun Context.hideSoftKeyboard() {
    val windowToken = when (this) {
        is Fragment -> {
            requireView().windowToken
        }
        is Activity -> {
            currentFocus?.windowToken
        }
        else -> {
            val baseContext = (this as ContextWrapper).baseContext
            (baseContext as? Activity)?.currentFocus?.windowToken
        }
    }

    val inputManager: InputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(
        windowToken,
        InputMethodManager.HIDE_NOT_ALWAYS
    )
}


/**
 * Add text watcher to the search view and emits all text changes in a flow
 */
private fun SearchView.getQueryTextChangeStateFlow(): Flow<String> {
    val query = MutableStateFlow("")

    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            context.hideSoftKeyboard()
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            query.value = newText
            return true
        }
    })

    return query
}


/**
 * Apply some operators to the method returned from the [getQueryTextChangeStateFlow] method
 * then returns a flow of filtered query inputs
 */
@ExperimentalCoroutinesApi
@FlowPreview
fun SearchView.getQueryFlow(): Flow<String> {
    return getQueryTextChangeStateFlow()
        .debounce(500)
        .filter { query ->
            return@filter query.isNotEmpty()
        }
        .distinctUntilChanged()
        .flatMapLatest { query ->
            flow {
                emit(query)
            }
        }

}

